package org.barberia.servicios.interfaces;

import org.barberia.modelos.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRolesService {

    Page<Roles> buscarTodosPaginados(Pageable pageable);

    List<Roles> obtenerTodos();

    Optional<Roles> buscarPorId(Integer id);

    Roles createOEditar(Roles roles);

    void eliminarPorId(Integer id);
}
