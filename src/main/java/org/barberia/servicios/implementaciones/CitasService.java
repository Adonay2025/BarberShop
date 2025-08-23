package org.barberia.servicios.implementaciones;

import org.barberia.modelos.Citas;
import org.barberia.repositorios.ICitasRepository;
import org.barberia.servicios.interfaces.ICitasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitasService implements ICitasService {
    @Autowired
    private ICitasRepository citasRepository;

    @Override
    public Page<Citas> buscarTodosPaginados(Pageable pageable) {
        return citasRepository.findAll(pageable);
    }
    @Override
    public List<Citas> obtenerTodos() {
        return citasRepository.findAll();
    }

    @Override
    public Optional<Citas> buscarPorId(Integer id) {
        return citasRepository.findById(id);
    }


    @Override
    public Citas createOEditar(Citas cita) {
        return validarYGuardarCita(cita);
    }


    // ✅ NUEVO MÉTODO para evitar traslapes
    public Citas validarYGuardarCita(Citas nuevaCita) {
        List<Citas> citasExistentes = citasRepository.findByBarbero_IdAndFechaBetween(
                nuevaCita.getBarbero().getId(),
                nuevaCita.getFecha(),
                nuevaCita.getFecha()
        );

        for (Citas citaExistente : citasExistentes) {
            // Ignora la misma cita si estamos en modo edición
            if (nuevaCita.getId() != null && nuevaCita.getId().equals(citaExistente.getId())) {
                continue;
            }

            boolean seSolapa = !(nuevaCita.getHoraFin().compareTo(citaExistente.getHoraInicio()) <= 0 ||
                    nuevaCita.getHoraInicio().compareTo(citaExistente.getHoraFin()) >= 0);

            if (seSolapa) {
                throw new IllegalArgumentException("El barbero ya tiene una cita en ese horario.");
            }
        }

        return citasRepository.save(nuevaCita);
    }


    @Override
    public void eliminarPorId(Integer id) {
        citasRepository.deleteById(id);
    }
}

