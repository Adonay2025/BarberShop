package org.barberia.servicios.interfaces;

import org.barberia.modelos.Servicios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IServiciosService {

    Page<Servicios> buscarTodosPaginados(Pageable pageable);

    List<Servicios> obtenerTodos();

    Optional<Servicios> buscarPorId(Integer id);

    Servicios createOEditar(Servicios servicios);

    void eliminarPorId(Integer id);
}
