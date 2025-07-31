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
    public Citas createOEditar(Citas citas) {
        return citasRepository.save(citas);
    }

    @Override
    public void eliminarPorId(Integer id) {
citasRepository.deleteById(id);
    }
}
