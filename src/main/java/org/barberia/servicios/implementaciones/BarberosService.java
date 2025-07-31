package org.barberia.servicios.implementaciones;

import org.barberia.modelos.Barberos;
import org.barberia.repositorios.IBarberosRepository;
import org.barberia.servicios.interfaces.IBarberosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BarberosService implements IBarberosService {

    @Autowired
    private IBarberosRepository barberosRepository;

    @Override
    public Page<Barberos> buscarTodosPaginados(Pageable pageable) {
        return barberosRepository .findAll(pageable);
    }

    @Override
    public List<Barberos> obtenerTodos() {
        return barberosRepository.findAll();
    }

    @Override
    public Optional<Barberos> buscarPorId(Integer id) {
        return barberosRepository.findById(id);
    }

    @Override
    public Barberos createOEditar(Barberos barberos) {
        return barberosRepository.save(barberos);
    }

    @Override
    public void eliminarPorId(Integer id) {
        barberosRepository.deleteById(id);

    }
}
