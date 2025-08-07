//package org.barberia.repositorios;
//
//import org.barberia.modelos.Usuarios;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface IUsuariosRepository extends JpaRepository<Usuarios,Integer> {
//}

package org.barberia.repositorios;

import org.barberia.modelos.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IUsuariosRepository extends JpaRepository<Usuarios, Integer> {
    List<Usuarios> findByRole_Nombrerol(String nombrerol); // ✅ así sí funcionará
}

