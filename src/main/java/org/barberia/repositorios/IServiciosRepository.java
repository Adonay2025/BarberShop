package org.barberia.repositorios;

import org.barberia.modelos.Servicios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IServiciosRepository extends JpaRepository<Servicios, Integer> {
    List<Servicios> findByEstadoServicio(String estadoServicio);
}
