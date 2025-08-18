//package org.barberia.repositorios;
//
//import org.barberia.modelos.Barberos;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface IBarberosRepository extends JpaRepository<Barberos,Integer> {
//}

package org.barberia.repositorios;

import org.barberia.modelos.Barberos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IBarberosRepository extends JpaRepository<Barberos, Integer> {

    // Buscar un barbero a partir del correo del usuario
    Optional<Barberos> findByUsuario_Correo(String correo);
}



