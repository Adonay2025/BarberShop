package org.barberia.repositorios;

import org.barberia.modelos.Citas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.time.LocalDate;
import org.barberia.modelos.Usuarios;
import org.barberia.modelos.Barberos;


public interface ICitasRepository extends JpaRepository<Citas, Integer> {

    List<Citas> findByUsuario_Correo(String correo);

    List<Citas> findByBarberoId(Integer barberoId);

    List<Citas> findByBarbero_IdAndFechaBetween(Integer barberoId, LocalDate fechaInicio, LocalDate fechaFin);


    // Obtener todas las citas de un cliente
    List<Citas> findByUsuario(Usuarios usuario);

    // Obtener todas las citas de un barbero
    List<Citas> findByBarbero(Barberos barbero);

    boolean existsByBarberoIdAndFechaAndHoraInicio(Integer barberoId, LocalDate fecha, LocalTime horaInicio);



}


