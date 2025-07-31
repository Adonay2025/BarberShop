package org.barberia.servicios.implementaciones;

import org.barberia.modelos.Roles;
import org.barberia.repositorios.IRolesRepository;
import org.barberia.servicios.interfaces.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService implements IRolesService {

    @Autowired
    private IRolesRepository rolesRepository;

    @Override
    public Page<Roles> buscarTodosPaginados(Pageable pageable) {
        return rolesRepository.findAll(pageable);
    }

    @Override
    public List<Roles> obtenerTodos() {
        return rolesRepository.findAll();
    }

    @Override
    public Optional<Roles> buscarPorId(Integer id) {
        return rolesRepository.findById(id);
    }

    @Override
    public Roles createOEditar(Roles roles) {
        return rolesRepository.save(roles);
    }

    @Override
    public void eliminarPorId(Integer id) {
        rolesRepository.deleteById(id);
    }
}