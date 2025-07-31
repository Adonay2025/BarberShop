package org.barberia.servicios.interfaces;

import org.barberia.modelos.Barberos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBarberosService {

    Page<Barberos> buscarTodosPaginados(Pageable pageable);

    List<Barberos> obtenerTodos();

    Optional<Barberos> buscarPorId(Integer id);

    Barberos createOEditar(Barberos barberos);

    void eliminarPorId(Integer id);
}