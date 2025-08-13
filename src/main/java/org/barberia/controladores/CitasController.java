package org.barberia.controladores;

import org.barberia.modelos.Citas;
import org.barberia.modelos.Usuarios;
import org.barberia.modelos.Servicios;
import org.barberia.modelos.Barberos;
import org.barberia.repositorios.ICitasRepository;
import org.barberia.repositorios.IUsuariosRepository;
import org.barberia.repositorios.IServiciosRepository;
import org.barberia.repositorios.IBarberosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/citas")
public class CitasController {

    @Autowired
    private ICitasRepository citasRepository;

    @Autowired
    private IUsuariosRepository usuariosRepository;

    @Autowired
    private IServiciosRepository serviciosRepository;

    @Autowired
    private IBarberosRepository barberosRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listaCitas", citasRepository.findAll());
        return "citas/index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cita", new Citas());
        model.addAttribute("clientes", usuariosRepository.findByRole_Nombrerol("cliente"));
        model.addAttribute("servicios", serviciosRepository.findByEstadoServicio("Activo"));
        model.addAttribute("barberos", barberosRepository.findAll());
        return "citas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("cita") Citas cita,
                          @RequestParam("usuario") Integer usuarioId,
                          @RequestParam("servicio") Integer servicioId,
                          @RequestParam("barbero") Integer barberoId) {

        Usuarios usuario = usuariosRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado: " + usuarioId));
        Servicios servicio = serviciosRepository.findById(servicioId)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + servicioId));
        Barberos barbero = barberosRepository.findById(barberoId)
                .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado: " + barberoId));

        cita.setUsuario(usuario);
        cita.setServicio(servicio);
        cita.setBarbero(barbero);

        citasRepository.save(cita);
        return "redirect:/citas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        Citas cita = citasRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("cita", cita);
        model.addAttribute("clientes", usuariosRepository.findByRole_Nombrerol("cliente"));
        model.addAttribute("servicios", serviciosRepository.findByEstadoServicio("Activo"));
        model.addAttribute("barberos", barberosRepository.findAll());
        return "citas/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        citasRepository.deleteById(id);
        return "redirect:/citas";
    }
}
