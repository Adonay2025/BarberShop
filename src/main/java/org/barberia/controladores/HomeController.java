//package org.barberia.controladores;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class HomeController {
//
//    @GetMapping("/")
//    public String home() {
//        return "home/index"; // No pongas .html
//    }
//
//
//}
package org.barberia.controladores;

import org.barberia.modelos.Usuarios;
import org.barberia.servicios.interfaces.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private IUsuariosService usuariosService;

    @GetMapping({"/", "/home"})
    public String home(Model model, Principal principal) {
        if (principal != null) {
            String correo = principal.getName(); // Obtiene el correo del usuario autenticado
            Usuarios usuario = usuariosService.buscarPorCorreo(correo);
            if (usuario != null) {
                model.addAttribute("nombreUsuario", usuario.getNombre());
            }
        }

        return "home/index"; // Redirige a la plantilla home/index.html
    }
}




