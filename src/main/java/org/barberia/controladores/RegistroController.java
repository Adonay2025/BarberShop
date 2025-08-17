//package org.barberia.controladores;
//
//import org.barberia.modelos.Usuarios;
//import org.barberia.modelos.Roles;
//import org.barberia.repositorios.IUsuariosRepository;
//import org.barberia.repositorios.IRolesRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.validation.Valid;
//
//@Controller
//public class RegistroController {
//
//    @Autowired
//    private IUsuariosRepository usuariosRepo;
//
//    @Autowired
//    private IRolesRepository rolesRepo;
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    @GetMapping("/registro")
//    public String mostrarFormularioRegistro(Model modelo) {
//        modelo.addAttribute("usuario", new Usuarios());
//        return "seguridad/registro";  // Ajustado con carpeta seguridad
//    }
//
//    @PostMapping("/registro")
//    public String guardarRegistro(@Valid @ModelAttribute("usuario") Usuarios usuario,
//                                  BindingResult result,
//                                  Model modelo) {
//        if (result.hasErrors()) {
//            return "seguridad/registro";  // Ajustado con carpeta seguridad
//        }
//
//        Roles rolCliente = rolesRepo.findByNombrerolIgnoreCase("cliente");
//
//        if (rolCliente == null) {
//            modelo.addAttribute("error", "Rol CLIENTE no encontrado en la base de datos");
//            return "seguridad/registro";  // Ajustado con carpeta seguridad
//        }
//
//        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
//        usuario.setRole(rolCliente);
//
//        usuariosRepo.save(usuario);
//
//        return "redirect:/login";  // Redirige a la página de login
//    }
//
//    // Método para mostrar la página de login
//    @GetMapping("/login")
//    public String mostrarLogin() {
//        return "seguridad/login";  // Ajustado con carpeta seguridad
//    }
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

@Controller
public class RegistroController {

    @Autowired
    private IUsuariosRepository usuariosRepo;

    @Autowired
    private IRolesRepository rolesRepo;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model modelo) {
        modelo.addAttribute("usuario", new Usuarios());
        return "seguridad/registro";  // Ajustado con carpeta seguridad
    }

    @PostMapping("/registro")
    public String guardarRegistro(@Valid @ModelAttribute("usuario") Usuarios usuario,
                                  BindingResult result,
                                  Model modelo) {
        if (result.hasErrors()) {
            return "seguridad/registro";  // Ajustado con carpeta seguridad
        }

        Roles rolCliente = rolesRepo.findByNombrerolIgnoreCase("cliente");

        if (rolCliente == null) {
            modelo.addAttribute("error", "Rol CLIENTE no encontrado en la base de datos");
            return "seguridad/registro";  // Ajustado con carpeta seguridad
        }

        // Guardar la contraseña tal cual (sin encriptar)
        usuario.setContrasena(usuario.getContrasena());
        usuario.setRole(rolCliente);

        usuariosRepo.save(usuario);

        return "redirect:/login";  // Redirige a la página de login
    }

    // Método para mostrar la página de login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "seguridad/login";  // Ajustado con carpeta seguridad
    }
}


