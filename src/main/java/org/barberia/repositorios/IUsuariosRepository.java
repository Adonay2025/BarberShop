package org.barberia.repositorios;

import org.barberia.modelos.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuariosRepository extends JpaRepository<Usuarios,Integer> {
}
