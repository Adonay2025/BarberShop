package org.barberia.servicios.implementaciones;

import org.barberia.modelos.Servicios;
import org.barberia.repositorios.IServiciosRepository;
import org.barberia.servicios.interfaces.IServiciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiciosService implements IServiciosService {

    @Autowired
    private IServiciosRepository serviciosRepository;

    @Override
    public Page<Servicios> buscarTodosPaginados(Pageable pageable) {
        return serviciosRepository.findAll(pageable);
    }

    @Override
    public List<Servicios> obtenerTodos() {
        return serviciosRepository.findAll();
    }

    @Override
    public Optional<Servicios> buscarPorId(Integer id) {
        return serviciosRepository.findById(id);
    }

    @Override
    public Servicios createOEditar(Servicios servicios) {
        return serviciosRepository.save(servicios);
    }

    @Override
    public void eliminarPorId(Integer id) {
        serviciosRepository.deleteById(id);
    }
}
