//package org.barberia.controladores;
//
//import org.barberia.modelos.Usuarios;
//import org.barberia.modelos.Roles;
//import org.barberia.repositorios.IUsuariosRepository;
//import org.barberia.repositorios.IRolesRepository;
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
//@RequestMapping("/usuarios")
//public class UsuarioController {
//
//
//    @Autowired
//    private IUsuariosRepository usuariosRepo;
//
//    @Autowired
//    private IRolesRepository rolesRepo;
//
//    @GetMapping
//    public String listarUsuarios(Model modelo) {
//        List<Usuarios> usuarios = usuariosRepo.findAll();
//        modelo.addAttribute("usuarios", usuarios);
//        return "usuarios/index";
//    }
//
//    @GetMapping("/nuevo")
//    public String mostrarFormulario(Model modelo) {
//        modelo.addAttribute("usuario", new Usuarios());
//        modelo.addAttribute("roles", rolesRepo.findAll());
//        return "usuarios/formulario";
//    }
//
//    @PostMapping("/guardar")
//    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuarios usuario, BindingResult result, Model modelo) {
//        if (result.hasErrors()) {
//            modelo.addAttribute("roles", rolesRepo.findAll());
//            return "usuarios/formulario";
//        }
//
//        Integer rolId = usuario.getRole().getId();
//        Roles rolSeleccionado = rolesRepo.findById(rolId).orElse(null);
//        usuario.setRole(rolSeleccionado);
//
//        usuariosRepo.save(usuario);
//        return "redirect:/usuarios";
//    }
//
//    @GetMapping("/{id}")
//    public String verDetalle(@PathVariable("id") Integer id, Model modelo) {
//        Usuarios usuario = usuariosRepo.findById(id).orElse(null);
//        if (usuario == null) {
//            return "redirect:/usuarios";
//        }
//        modelo.addAttribute("usuario", usuario);
//        return "usuarios/detalle";
//    }
//
//    @GetMapping("/editar/{id}")
//    public String editarUsuario(@PathVariable("id") Integer id, Model modelo) {
//        Usuarios usuario = usuariosRepo.findById(id).orElse(null);
//        if (usuario == null) {
//            return "redirect:/usuarios";
//        }
//        modelo.addAttribute("usuario", usuario);
//        modelo.addAttribute("roles", rolesRepo.findAll());
//        modelo.addAttribute("modoEdicion", true);  // Agregado
//        return "usuarios/formulario";
//    }
//
//@PostMapping("/eliminar/{id}")
//public String eliminarUsuario(@PathVariable("id") Integer id) {
//    usuariosRepo.deleteById(id);
//    return "redirect:/usuarios";
//}
//
//
//}

package org.barberia.controladores;

import org.barberia.modelos.Usuarios;
import org.barberia.modelos.Roles;
import org.barberia.repositorios.IUsuariosRepository;
import org.barberia.repositorios.IRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuariosRepository usuariosRepo;

    @Autowired
    private IRolesRepository rolesRepo;

    @GetMapping
    public String listarUsuarios(@RequestParam(name = "rol", required = false) String nombrerol, Model modelo) {
        List<Usuarios> usuarios;

        if (nombrerol != null && !nombrerol.isEmpty()) {
            usuarios = usuariosRepo.findByRole_Nombrerol(nombrerol);
        } else {
            usuarios = usuariosRepo.findAll();
        }

        List<Roles> roles = rolesRepo.findAll();

        modelo.addAttribute("usuarios", usuarios);
        modelo.addAttribute("roles", roles);
        modelo.addAttribute("rolSeleccionado", nombrerol);

        return "usuarios/index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model modelo) {
        modelo.addAttribute("usuario", new Usuarios());
        modelo.addAttribute("roles", rolesRepo.findAll());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute("usuario") Usuarios usuario, BindingResult result, Model modelo) {
        if (result.hasErrors()) {
            modelo.addAttribute("roles", rolesRepo.findAll());
            return "usuarios/formulario";
        }

        Integer rolId = usuario.getRole().getId();
        Roles rolSeleccionado = rolesRepo.findById(rolId).orElse(null);
        usuario.setRole(rolSeleccionado);

        usuariosRepo.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable("id") Integer id, Model modelo) {
        Usuarios usuario = usuariosRepo.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        modelo.addAttribute("usuario", usuario);
        return "usuarios/detalle";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Integer id, Model modelo) {
        Usuarios usuario = usuariosRepo.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuarios";
        }
        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("roles", rolesRepo.findAll());
        modelo.addAttribute("modoEdicion", true);
        return "usuarios/formulario";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Integer id) {
        usuariosRepo.deleteById(id);
        return "redirect:/usuarios";
    }
}

