


package org.barberia.controladores;

import org.barberia.modelos.Servicios;
import org.barberia.repositorios.IServiciosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    @Autowired
    private IServiciosRepository serviciosRepository;

    // Tamaño máximo permitido para imágenes (5MB)
    private static final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 5 MB

    // Listar todos los servicios
    @GetMapping({"", "/"})
    public String listarServicios(Model model) {
        model.addAttribute("listaServicios", serviciosRepository.findAll());
        return "servicios/index";
    }

    // Mostrar formulario para crear nuevo servicio
    @GetMapping({"/nuevo", "/crear"})
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("servicio", new Servicios());
        model.addAttribute("modoEdicion", false);
        return "servicios/formulario";
    }

    // Guardar servicio (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarServicio(@Valid @ModelAttribute("servicio") Servicios servicio,
                                  BindingResult result,
                                  @RequestParam("imagen") MultipartFile imagenFile,
                                  Model model) {

        if (result.hasErrors()) {
            model.addAttribute("modoEdicion", servicio.getId() != null);
            return "servicios/formulario";
        }

        try {
            if (imagenFile != null && !imagenFile.isEmpty()) {
                // Validar tamaño de imagen
                if (imagenFile.getSize() > MAX_IMAGE_SIZE) {
                    model.addAttribute("errorImagen", "La imagen excede el tamaño máximo permitido (5 MB).");
                    model.addAttribute("modoEdicion", servicio.getId() != null);
                    return "servicios/formulario";
                }

                // Codificar imagen a Base64
                String base64Imagen = "data:" + imagenFile.getContentType() + ";base64," +
                        Base64.getEncoder().encodeToString(imagenFile.getBytes());
                servicio.setImagenUrl(base64Imagen);

            } else {
                if (servicio.getId() != null) {
                    // Si es edición, mantener imagen existente
                    Optional<Servicios> servicioExistente = serviciosRepository.findById(servicio.getId());
                    servicioExistente.ifPresent(s -> servicio.setImagenUrl(s.getImagenUrl()));
                } else {
                    // Si es nuevo y no suben imagen, poner una por defecto
                    servicio.setImagenUrl("https://cdn-icons-png.flaticon.com/512/1257/1257249.png");
                }
            }

            serviciosRepository.save(servicio);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorImagen", "Error al procesar la imagen.");
            return "servicios/formulario";
        }

        return "redirect:/servicios";
    }

    // Mostrar formulario para editar servicio
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Optional<Servicios> servicioOptional = serviciosRepository.findById(id);
        if (servicioOptional.isPresent()) {
            model.addAttribute("servicio", servicioOptional.get());
            model.addAttribute("modoEdicion", true);
            return "servicios/formulario";
        } else {
            return "redirect:/servicios";
        }
    }

    // Eliminar servicio por id
    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Integer id) {
        serviciosRepository.deleteById(id);
        return "redirect:/servicios";
    }
}

