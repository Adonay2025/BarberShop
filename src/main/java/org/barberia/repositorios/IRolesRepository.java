//package org.barberia.repositorios;
//
//import org.barberia.modelos.Roles;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface IRolesRepository extends JpaRepository<Roles,Integer> {
//}
package org.barberia.repositorios;

import org.barberia.modelos.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolesRepository extends JpaRepository<Roles, Integer> {
    Roles findByNombrerolIgnoreCase(String nombrerol);


}
