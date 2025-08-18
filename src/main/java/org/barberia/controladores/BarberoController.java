//
//
//package org.barberia.controladores;
//
//import org.barberia.modelos.Barberos;
//import org.barberia.modelos.Usuarios;
//import org.barberia.repositorios.IBarberosRepository;
//import org.barberia.repositorios.IUsuariosRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//import java.util.List;
//
//@Controller
//@RequestMapping("/barberos")
//public class BarberoController {
//
//    @Autowired
//    private IBarberosRepository barberoRepo;
//
//    @Autowired
//    private IUsuariosRepository usuariosRepo;
//
//    // ✅ Listar barberos
//    @GetMapping
//    public String listarBarberos(Model modelo) {
//        List<Barberos> barberos = barberoRepo.findAll();
//        modelo.addAttribute("barberos", barberos);
//        return "barberos/index";
//    }
//
//    // ✅ Formulario nuevo barbero
//    @GetMapping("/nuevo")
//    public String mostrarFormulario(Model modelo) {
//        modelo.addAttribute("barbero", new Barberos());
//        List<Usuarios> usuariosConRolBarbero = usuariosRepo.findByRole_Nombrerol("Barbero");
//        modelo.addAttribute("usuarios", usuariosConRolBarbero);
//        return "barberos/formulario";
//    }
//
//    // ✅ Guardar o actualizar barbero
//    @PostMapping("/guardar")
//    public String guardarBarbero(@Valid @ModelAttribute("barbero") Barberos barbero, BindingResult result, Model modelo) {
//        if (result.hasErrors()) {
//            List<Usuarios> usuariosConRolBarbero = usuariosRepo.findByRole_Nombrerol("Barbero");
//            modelo.addAttribute("usuarios", usuariosConRolBarbero);
//            return "barberos/formulario";
//        }
//        barberoRepo.save(barbero);
//        return "redirect:/barberos";
//    }
//
//    // ✅ Obtener datos de usuario por ID (para autocompletar)
//    @GetMapping("/datos-usuario/{id}")
//    @ResponseBody
//    public Usuarios obtenerDatosUsuario(@PathVariable("id") Integer id) {
//        return usuariosRepo.findById(id).orElse(null);
//    }
//
//    // ✅ Editar barbero
//    @GetMapping("/editar/{id}")
//    public String editarBarbero(@PathVariable("id") Integer id, Model modelo) {
//        Barberos barbero = barberoRepo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("ID de barbero inválido: " + id));
//        modelo.addAttribute("barbero", barbero);
//
//        List<Usuarios> usuariosConRolBarbero = usuariosRepo.findByRole_Nombrerol("Barbero");
//        modelo.addAttribute("usuarios", usuariosConRolBarbero);
//
//        return "barberos/formulario";
//    }
//
//    // ✅ Eliminar barbero
//    @GetMapping("/eliminar/{id}")
//    public String eliminarBarbero(@PathVariable("id") Integer id) {
//        barberoRepo.deleteById(id);
//        return "redirect:/barberos";
//    }
//}
//

package org.barberia.controladores;

import org.barberia.modelos.Barberos;
import org.barberia.modelos.Citas;
import org.barberia.modelos.Usuarios;
import org.barberia.repositorios.IBarberosRepository;
import org.barberia.repositorios.ICitasRepository;
import org.barberia.repositorios.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/barberos")
public class BarberoController {

    @Autowired
    private IBarberosRepository barberoRepo;

    @Autowired
    private IUsuariosRepository usuariosRepo;

    @Autowired
    private ICitasRepository citasRepo;

    // ✅ Listar barberos (admin)
    @GetMapping
    public String listarBarberos(Model modelo) {
        List<Barberos> barberos = barberoRepo.findAll();
        modelo.addAttribute("barberos", barberos);
        return "barberos/index";
    }

    // ✅ Formulario nuevo barbero (admin)
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model modelo) {
        modelo.addAttribute("barbero", new Barberos());
        List<Usuarios> usuariosConRolBarbero = usuariosRepo.findByRole_Nombrerol("Barbero");
        modelo.addAttribute("usuarios", usuariosConRolBarbero);
        return "barberos/formulario";
    }

    // ✅ Guardar o actualizar barbero (admin)
    @PostMapping("/guardar")
    public String guardarBarbero(@Valid @ModelAttribute("barbero") Barberos barbero, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            List<Usuarios> usuariosConRolBarbero = usuariosRepo.findByRole_Nombrerol("Barbero");
            modelo.addAttribute("usuarios", usuariosConRolBarbero);
            return "barberos/formulario";
        }
        barberoRepo.save(barbero);
        return "redirect:/barberos";
    }

    // ✅ Obtener datos de usuario por ID (admin)
    @GetMapping("/datos-usuario/{id}")
    @ResponseBody
    public Usuarios obtenerDatosUsuario(@PathVariable("id") Integer id) {
        return usuariosRepo.findById(id).orElse(null);
    }

    // ✅ Editar barbero (admin)
    @GetMapping("/editar/{id}")
    public String editarBarbero(@PathVariable("id") Integer id, Model modelo) {
        Barberos barbero = barberoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de barbero inválido: " + id));
        modelo.addAttribute("barbero", barbero);

        List<Usuarios> usuariosConRolBarbero = usuariosRepo.findByRole_Nombrerol("Barbero");
        modelo.addAttribute("usuarios", usuariosConRolBarbero);

        return "barberos/formulario";
    }

    // ✅ Eliminar barbero (admin)
    @GetMapping("/eliminar/{id}")
    public String eliminarBarbero(@PathVariable("id") Integer id) {
        barberoRepo.deleteById(id);
        return "redirect:/barberos";
    }

    // ✅ Ver citas del barbero logueado
    @GetMapping("/mis-citas")
    public String verCitasDelBarbero(Authentication authentication, Model model) {
        // 1. Obtener correo del usuario logueado
        String correo = authentication.getName();

        // 2. Buscar el barbero asociado a ese usuario
        Barberos barbero = barberoRepo.findByUsuario_Correo(correo)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado para el correo: " + correo));

        // 3. Obtener citas de ese barbero
        List<Citas> citas = citasRepo.findByBarberoId(barbero.getId());

        // 4. Pasar citas a la vista
        model.addAttribute("citas", citas);

        return "barberos/mis-citas"; // Thymeleaf → templates/barberos/mis-citas.html
    }
}
