package org.barberia.servicios.interfaces;

import org.barberia.modelos.Citas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICitasService {

    Page<Citas> buscarTodosPaginados(Pageable pageable);

    List<Citas> obtenerTodos();

    Optional<Citas> buscarPorId(Integer id);

    Citas createOEditar(Citas citas);

    void eliminarPorId(Integer id);

    // ✅ Agrega este método
    Citas validarYGuardarCita(Citas cita);
}
