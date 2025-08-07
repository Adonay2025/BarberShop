package org.barberia.servicios.implementaciones;

import org.barberia.modelos.Usuarios;
import org.barberia.repositorios.IUsuariosRepository;
import org.barberia.servicios.interfaces.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService implements IUsuariosService {

    @Autowired
    private IUsuariosRepository usuariosRepository;

    @Override
    public Page<Usuarios> buscarTodosPaginados(Pageable pageable) {
        return usuariosRepository.findAll(pageable);
    }

    @Override
    public List<Usuarios> obtenerTodos() {
        return usuariosRepository.findAll();
    }

    @Override
    public Optional<Usuarios> buscarPorId(Integer id) {
        return usuariosRepository.findById(id);
    }

    @Override
    public Usuarios createOEditar(Usuarios usuarios) {
        return usuariosRepository.save(usuarios);
    }

    @Override
    public void eliminarPorId(Integer id) {
       usuariosRepository.deleteById(id);
    }
}

