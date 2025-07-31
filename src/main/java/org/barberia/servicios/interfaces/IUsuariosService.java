package org.barberia.servicios.interfaces;

import org.barberia.modelos.Usuarios;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUsuariosService {

    Page<Usuarios> buscarTodosPaginados(Pageable pageable);

    List<Usuarios> obtenerTodos();

    Optional<Usuarios> buscarPorId(Integer id);

    Usuarios createOEditar(Usuarios usuarios);

    void eliminarPorId(Integer id);
}


