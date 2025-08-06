package org.barberia;

import org.barberia.modelos.Usuarios;
import org.barberia.repositorios.IUsuariosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BarberiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberiaApplication.class, args);
	}


//	// Bloque de prueba para insertar un usuario
//	@Bean
//	public CommandLineRunner init(IUsuariosRepository repo) {
//		return args -> {
//			Usuarios u = new Usuarios();
//			u.setNombre("Admin");
//			u.setCorreo("admin@barberia.com");
//			u.setContrasena("1234");
//			u.setTelefono("5551234");
//
//			repo.save(u);
//			System.out.println("Usuario guardado correctamente.");
//		};
//	}
}

