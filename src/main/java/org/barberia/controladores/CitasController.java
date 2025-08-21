//
//package org.barberia.controladores;
//
//import org.barberia.modelos.Citas;
//import org.barberia.modelos.Usuarios;
//import org.barberia.modelos.Servicios;
//import org.barberia.modelos.Barberos;
//import org.barberia.repositorios.ICitasRepository;
//import org.barberia.repositorios.IUsuariosRepository;
//import org.barberia.repositorios.IServiciosRepository;
//import org.barberia.repositorios.IBarberosRepository;
//import org.barberia.servicios.EmailService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/citas")
//public class CitasController {
//
//    private static final Logger logger = LoggerFactory.getLogger(CitasController.class);
//
//    @Autowired
//    private ICitasRepository citasRepository;
//
//    @Autowired
//    private IUsuariosRepository usuariosRepository;
//
//    @Autowired
//    private IServiciosRepository serviciosRepository;
//
//    @Autowired
//    private IBarberosRepository barberosRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    // Para decidir a quién notificar en CANCELADA
//    private enum Actor { CLIENTE, BARBERO, ADMIN }
//
//    @GetMapping
//    public String index(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String correo = auth.getName();
//
//        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
//        boolean esBarbero = tieneRol(auth, "ROLE_barbero");
//
//        List<Citas> citas;
//
//        if (esAdmin) {
//            citas = citasRepository.findAll();
//        } else if (esBarbero) {
//            Barberos barbero = barberosRepository.findByUsuario_Correo(correo)
//                    .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado con correo: " + correo));
//            citas = citasRepository.findByBarberoId(barbero.getId());
//        } else {
//            citas = citasRepository.findByUsuario_Correo(correo);
//        }
//
//        model.addAttribute("listaCitas", citas);
//        return "citas/index";
//    }
//
//    @GetMapping("/nuevo")
//    public String mostrarFormulario(Model model) {
//        model.addAttribute("cita", new Citas());
//        model.addAttribute("esNuevo", true);
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String correo = auth.getName();
//
//        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
//
//        if (esAdmin) {
//            model.addAttribute("clientes", usuariosRepository.findByRole_Nombrerol("cliente"));
//        } else {
//            Usuarios usuario = usuariosRepository.findByCorreo(correo);
//            model.addAttribute("clientes", List.of(usuario));
//        }
//
//        model.addAttribute("servicios", serviciosRepository.findByEstadoServicio("Activo"));
//        model.addAttribute("barberos", barberosRepository.findAll());
//        return "citas/formulario";
//    }
//
//    @GetMapping("/editar/{id}")
//    public String editar(@PathVariable("id") Integer id, Model model) {
//        Citas cita = citasRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String correo = auth.getName();
//
//        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
//        boolean esBarbero = tieneRol(auth, "ROLE_barbero");
//
//        if (!esAdmin && !esBarbero && !cita.getUsuario().getCorreo().equals(correo)) {
//            return "redirect:/citas?error=forbidden";
//        }
//
//        model.addAttribute("cita", cita);
//        model.addAttribute("esNuevo", false);
//
//        if (esAdmin) {
//            model.addAttribute("clientes", usuariosRepository.findByRole_Nombrerol("cliente"));
//        } else {
//            Usuarios usuario = usuariosRepository.findByCorreo(correo);
//            model.addAttribute("clientes", List.of(usuario));
//        }
//
//        model.addAttribute("servicios", serviciosRepository.findByEstadoServicio("Activo"));
//        model.addAttribute("barberos", barberosRepository.findAll());
//
//        return "citas/formulario";
//    }
//
//    @PostMapping("/guardar")
//    public String guardar(@ModelAttribute("cita") Citas cita,
//                          @RequestParam(value = "usuario", required = false) Integer usuarioId,
//                          @RequestParam(value = "servicio", required = false) Integer servicioId,
//                          @RequestParam(value = "barbero", required = false) Integer barberoId) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String correo = auth.getName();
//        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
//        boolean esBarbero = tieneRol(auth, "ROLE_barbero");
//
//        // Determinar quién hizo la acción
//        Actor actor = esBarbero ? Actor.BARBERO : (esAdmin ? Actor.ADMIN : Actor.CLIENTE);
//
//        // Asignar usuario
//        if (!esAdmin && !esBarbero) {
//            Usuarios usuario = usuariosRepository.findByCorreo(correo);
//            cita.setUsuario(usuario);
//        } else if (usuarioId != null) {
//            Usuarios usuario = usuariosRepository.findById(usuarioId)
//                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + usuarioId));
//            cita.setUsuario(usuario);
//        }
//
//        // Asignar servicio
//        if (servicioId != null) {
//            Servicios servicio = serviciosRepository.findById(servicioId)
//                    .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + servicioId));
//            cita.setServicio(servicio);
//        }
//
//        // Asignar barbero
//        if (barberoId != null) {
//            Barberos barbero = barberosRepository.findById(barberoId)
//                    .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado: " + barberoId));
//            cita.setBarbero(barbero);
//        }
//
//        // Guardar estado anterior
//        String estadoAnterior = null;
//        if (cita.getId() != null) {
//            Citas citaOriginal = citasRepository.findById(cita.getId()).orElse(null);
//            if (citaOriginal != null) {
//                estadoAnterior = citaOriginal.getEstado();
//            }
//        }
//
//        // Guardar cambios
//        citasRepository.save(cita);
//
//        // Notificaciones
//        if (estadoAnterior == null) {
//            // Nueva cita → notificar barbero
//            Usuarios usuarioBarbero = (cita.getBarbero() != null) ? cita.getBarbero().getUsuario() : null;
//            if (usuarioBarbero != null && tieneCorreo(usuarioBarbero.getCorreo())) {
//                emailService.enviarCorreo(
//                        usuarioBarbero.getCorreo(),
//                        "Nueva cita asignada",
//                        "Hola " + usuarioBarbero.getNombre() +
//                                ",\n\nTienes una nueva cita con el cliente " +
//                                (cita.getUsuario() != null ? cita.getUsuario().getNombre() : "cliente") +
//                                " el día " + cita.getFecha() + "."
//                );
//            } else {
//                logger.warn("⚠️ No se pudo notificar nueva cita: barbero o correo nulo");
//            }
//        } else if (!estadoAnterior.equalsIgnoreCase(cita.getEstado())) {
//            // Recargar usuario completo antes de notificar (por si viene parcial)
//            if (cita.getUsuario() != null && cita.getUsuario().getId() != null) {
//                Usuarios clienteCompleto = usuariosRepository.findById(cita.getUsuario().getId()).orElse(null);
//                cita.setUsuario(clienteCompleto);
//            }
//            notificarPorEstado(cita, cita.getEstado(), actor);
//        }
//
//        return "redirect:/citas";
//    }
//
//    private void notificarPorEstado(Citas cita, String nuevoEstado, Actor actor) {
//        Usuarios cliente = cita.getUsuario();
//        Usuarios usuarioBarbero = (cita.getBarbero() != null) ? cita.getBarbero().getUsuario() : null;
//
//        switch (nuevoEstado.toUpperCase()) {
//            case "CANCELADA":
//                // Si el CLIENTE cancela → avisar al BARBERO
//                if (actor == Actor.CLIENTE || actor == Actor.ADMIN) {
//                    if (usuarioBarbero != null && tieneCorreo(usuarioBarbero.getCorreo())) {
//                        String nombreCliente = (cliente != null ? cliente.getNombre() : "cliente");
//                        emailService.enviarCorreo(
//                                usuarioBarbero.getCorreo(),
//                                "Cita cancelada",
//                                "El cliente " + nombreCliente +
//                                        " ha cancelado la cita programada para el " + cita.getFecha() + "."
//                        );
//                    } else {
//                        logger.warn("⚠️ No se pudo notificar cancelación al barbero: usuario o correo nulo");
//                    }
//                }
//
//                // Si el BARBERO cancela → avisar al CLIENTE
//                if (actor == Actor.BARBERO || actor == Actor.ADMIN) {
//                    if (cliente != null && tieneCorreo(cliente.getCorreo())) {
//                        String nombreBarbero = (usuarioBarbero != null ? usuarioBarbero.getNombre() : "el barbero");
//                        emailService.enviarCorreo(
//                                cliente.getCorreo(),
//                                "Cita cancelada",
//                                "Tu cita con " + nombreBarbero +
//                                        " programada para el " + cita.getFecha() +
//                                        " ha sido cancelada."
//                        );
//                    } else {
//                        logger.warn("⚠️ No se pudo notificar cancelación al cliente: usuario o correo nulo");
//                    }
//                }
//                break;
//
//            case "CONFIRMADA":
//                // Cuando confirman, avisamos al cliente
//                if (cliente != null && tieneCorreo(cliente.getCorreo())) {
//                    emailService.enviarCorreo(
//                            cliente.getCorreo(),
//                            "Cita confirmada",
//                            "Tu cita con el barbero " +
//                                    (usuarioBarbero != null ? usuarioBarbero.getNombre() : "asignado") +
//                                    " ha sido confirmada para el " + cita.getFecha() + "."
//                    );
//                } else {
//                    logger.warn("⚠️ No se pudo notificar confirmación: cliente o correo nulo");
//                }
//                break;
//
//            case "RECHAZADA":
//                // Rechazo (barbero/admin) → avisar al cliente
//                if (cliente != null && tieneCorreo(cliente.getCorreo())) {
//                    emailService.enviarCorreo(
//                            cliente.getCorreo(),
//                            "Cita rechazada",
//                            "Tu cita con el barbero " +
//                                    (usuarioBarbero != null ? usuarioBarbero.getNombre() : "asignado") +
//                                    " ha sido rechazada."
//                    );
//                } else {
//                    logger.warn("⚠️ No se pudo notificar rechazo: cliente o correo nulo");
//                }
//                break;
//
//            case "COMPLETADA":
//                // Completada → opcional: avisar al cliente
//                if (cliente != null && tieneCorreo(cliente.getCorreo())) {
//                    emailService.enviarCorreo(
//                            cliente.getCorreo(),
//                            "Cita completada",
//                            "Tu cita con el barbero " +
//                                    (usuarioBarbero != null ? usuarioBarbero.getNombre() : "asignado") +
//                                    " se ha completado exitosamente."
//                    );
//                } else {
//                    logger.warn("⚠️ No se pudo notificar completada: cliente o correo nulo");
//                }
//                break;
//        }
//    }
//
//    @GetMapping("/eliminar/{id}")
//    public String eliminar(@PathVariable("id") Integer id) {
//        Citas cita = citasRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String correo = auth.getName();
//
//        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
//        boolean esBarbero = tieneRol(auth, "ROLE_barbero");
//
//        if (!esAdmin && !esBarbero && !cita.getUsuario().getCorreo().equals(correo)) {
//            return "redirect:/citas?error=forbidden";
//        }
//
//        citasRepository.deleteById(id);
//        return "redirect:/citas";
//    }
//
//    // ---- Helpers ----
//    private boolean tieneRol(Authentication auth, String rol) {
//        return auth.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .anyMatch(r -> r.equals(rol));
//    }
//
//    private boolean tieneCorreo(String correo) {
//        return correo != null && !correo.isBlank();
//    }
//}
//
//
//
//
//
//
//


package org.barberia.controladores;

import org.barberia.modelos.Citas;
import org.barberia.modelos.Usuarios;
import org.barberia.modelos.Servicios;
import org.barberia.modelos.Barberos;
import org.barberia.repositorios.ICitasRepository;
import org.barberia.repositorios.IUsuariosRepository;
import org.barberia.repositorios.IServiciosRepository;
import org.barberia.repositorios.IBarberosRepository;
import org.barberia.servicios.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/citas")
public class CitasController {

    private static final Logger logger = LoggerFactory.getLogger(CitasController.class);

    @Autowired
    private ICitasRepository citasRepository;

    @Autowired
    private IUsuariosRepository usuariosRepository;

    @Autowired
    private IServiciosRepository serviciosRepository;

    @Autowired
    private IBarberosRepository barberosRepository;

    @Autowired
    private EmailService emailService;

    private enum Actor { CLIENTE, BARBERO, ADMIN }

    @GetMapping
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
        boolean esBarbero = tieneRol(auth, "ROLE_barbero");

        List<Citas> citas;

        if (esAdmin) {
            citas = citasRepository.findAll();
        } else if (esBarbero) {
            Barberos barbero = barberosRepository.findByUsuario_Correo(correo)
                    .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado con correo: " + correo));
            citas = citasRepository.findByBarberoId(barbero.getId());
        } else {
            citas = citasRepository.findByUsuario_Correo(correo);
        }

        model.addAttribute("listaCitas", citas);
        return "citas/index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cita", new Citas());
        model.addAttribute("esNuevo", true);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        boolean esAdmin = tieneRol(auth, "ROLE_administrador");

        if (esAdmin) {
            model.addAttribute("clientes", usuariosRepository.findByRole_Nombrerol("cliente"));
        } else {
            Usuarios usuario = usuariosRepository.findByCorreo(correo);
            model.addAttribute("clientes", List.of(usuario));
        }

        model.addAttribute("servicios", serviciosRepository.findByEstadoServicio("Activo"));
        model.addAttribute("barberos", barberosRepository.findAll());
        return "citas/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        Citas cita = citasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
        boolean esBarbero = tieneRol(auth, "ROLE_barbero");

        if (!esAdmin && !esBarbero && !cita.getUsuario().getCorreo().equals(correo)) {
            return "redirect:/citas?error=forbidden";
        }

        model.addAttribute("cita", cita);
        model.addAttribute("esNuevo", false);

        if (esAdmin) {
            model.addAttribute("clientes", usuariosRepository.findByRole_Nombrerol("cliente"));
        } else {
            Usuarios usuario = usuariosRepository.findByCorreo(correo);
            model.addAttribute("clientes", List.of(usuario));
        }

        model.addAttribute("servicios", serviciosRepository.findByEstadoServicio("Activo"));
        model.addAttribute("barberos", barberosRepository.findAll());

        return "citas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("cita") Citas cita,
                          @RequestParam(value = "usuario", required = false) Integer usuarioId,
                          @RequestParam(value = "servicio", required = false) Integer servicioId,
                          @RequestParam(value = "barbero", required = false) Integer barberoId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
        boolean esBarbero = tieneRol(auth, "ROLE_barbero");

        Actor actor = esBarbero ? Actor.BARBERO : (esAdmin ? Actor.ADMIN : Actor.CLIENTE);

        if (!esAdmin && !esBarbero) {
            Usuarios usuario = usuariosRepository.findByCorreo(correo);
            cita.setUsuario(usuario);
        } else if (usuarioId != null) {
            Usuarios usuario = usuariosRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + usuarioId));
            cita.setUsuario(usuario);
        }

        if (servicioId != null) {
            Servicios servicio = serviciosRepository.findById(servicioId)
                    .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + servicioId));
            cita.setServicio(servicio);
        }

        if (barberoId != null) {
            Barberos barbero = barberosRepository.findById(barberoId)
                    .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado: " + barberoId));
            cita.setBarbero(barbero);
        }

        String estadoAnterior = null;
        if (cita.getId() != null) {
            Citas citaOriginal = citasRepository.findById(cita.getId()).orElse(null);
            if (citaOriginal != null) {
                estadoAnterior = citaOriginal.getEstado();
            }
        }

        citasRepository.save(cita);

        if (estadoAnterior == null) {
            // Nueva cita → notificar barbero con HTML limpio
            Usuarios usuarioBarbero = (cita.getBarbero() != null) ? cita.getBarbero().getUsuario() : null;
            if (usuarioBarbero != null && tieneCorreo(usuarioBarbero.getCorreo())) {
                String htmlCuerpo = generarHtmlCita("Nueva cita asignada",
                        usuarioBarbero.getNombre(),
                        cita.getUsuario() != null ? cita.getUsuario().getNombre() : "cliente",
                        cita.getFecha().toString());
                emailService.enviarCorreo(usuarioBarbero.getCorreo(), "Nueva cita asignada", htmlCuerpo, true);
            } else {
                logger.warn("⚠️ No se pudo notificar nueva cita: barbero o correo nulo");
            }
        } else if (!estadoAnterior.equalsIgnoreCase(cita.getEstado())) {
            if (cita.getUsuario() != null && cita.getUsuario().getId() != null) {
                Usuarios clienteCompleto = usuariosRepository.findById(cita.getUsuario().getId()).orElse(null);
                cita.setUsuario(clienteCompleto);
            }
            notificarPorEstado(cita, cita.getEstado(), actor);
        }

        return "redirect:/citas";
    }

    private void notificarPorEstado(Citas cita, String nuevoEstado, Actor actor) {
        Usuarios cliente = cita.getUsuario();
        Usuarios usuarioBarbero = (cita.getBarbero() != null) ? cita.getBarbero().getUsuario() : null;

        switch (nuevoEstado.toUpperCase()) {
            case "CANCELADA":
                if (actor == Actor.CLIENTE || actor == Actor.ADMIN) {
                    if (usuarioBarbero != null && tieneCorreo(usuarioBarbero.getCorreo())) {
                        String html = generarHtmlCita("Cita cancelada",
                                usuarioBarbero.getNombre(),
                                cliente != null ? cliente.getNombre() : "cliente",
                                cita.getFecha().toString());
                        emailService.enviarCorreo(usuarioBarbero.getCorreo(), "Cita cancelada", html, true);
                    }
                }
                if (actor == Actor.BARBERO || actor == Actor.ADMIN) {
                    if (cliente != null && tieneCorreo(cliente.getCorreo())) {
                        String html = generarHtmlCita("Cita cancelada",
                                cliente.getNombre(),
                                usuarioBarbero != null ? usuarioBarbero.getNombre() : "el barbero",
                                cita.getFecha().toString());
                        emailService.enviarCorreo(cliente.getCorreo(), "Cita cancelada", html, true);
                    }
                }
                break;

            case "CONFIRMADA":
                if (cliente != null && tieneCorreo(cliente.getCorreo())) {
                    String html = generarHtmlCita("Cita confirmada",
                            cliente.getNombre(),
                            usuarioBarbero != null ? usuarioBarbero.getNombre() : "asignado",
                            cita.getFecha().toString());
                    emailService.enviarCorreo(cliente.getCorreo(), "Cita confirmada", html, true);
                }
                break;

            case "RECHAZADA":
                if (cliente != null && tieneCorreo(cliente.getCorreo())) {
                    String html = generarHtmlCita("Cita rechazada",
                            cliente.getNombre(),
                            usuarioBarbero != null ? usuarioBarbero.getNombre() : "asignado",
                            cita.getFecha().toString());
                    emailService.enviarCorreo(cliente.getCorreo(), "Cita rechazada", html, true);
                }
                break;

            case "COMPLETADA":
                if (cliente != null && tieneCorreo(cliente.getCorreo())) {
                    String html = generarHtmlCita("Cita completada",
                            cliente.getNombre(),
                            usuarioBarbero != null ? usuarioBarbero.getNombre() : "asignado",
                            cita.getFecha().toString());
                    emailService.enviarCorreo(cliente.getCorreo(), "Cita completada", html, true);
                }
                break;
        }
    }

    private String generarHtmlCita(String titulo, String destinatario, String otroUsuario, String fecha) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "body { font-family: Arial, sans-serif; background-color: #f7f7f7; margin: 0; padding: 20px; }\n" +
                ".container { background-color: #ffffff; max-width: 500px; margin: auto; padding: 20px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); }\n" +
                "h2 { color: #333; text-align: center; }\n" +
                ".details { font-size: 16px; color: #555; margin-bottom: 20px; }\n" +
                ".highlight { font-weight: bold; color: #2a9d8f; }\n" +
                ".footer { font-size: 14px; color: #999; text-align: center; margin-top: 20px; }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "<h2>" + titulo + "</h2>\n" +
                "<p class=\"details\">Hola <span class=\"highlight\">" + destinatario + "</span>,</p>\n" +
                "<p class=\"details\">Tu cita con <span class=\"highlight\">" + otroUsuario + "</span> está programada para el <span class=\"highlight\">" + fecha + "</span>.</p>\n" +
                "<p class=\"footer\">Gracias por usar nuestro servicio.<br>Barbershop Team</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        Citas cita = citasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();

        boolean esAdmin = tieneRol(auth, "ROLE_administrador");
        boolean esBarbero = tieneRol(auth, "ROLE_barbero");

        if (!esAdmin && !esBarbero && !cita.getUsuario().getCorreo().equals(correo)) {
            return "redirect:/citas?error=forbidden";
        }

        citasRepository.deleteById(id);
        return "redirect:/citas";
    }

    private boolean tieneRol(Authentication auth, String rol) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals(rol));
    }

    private boolean tieneCorreo(String correo) {
        return correo != null && !correo.isBlank();
    }
}
