package org.barberia.repositorios;

import org.barberia.modelos.Barberos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBarberosRepository extends JpaRepository<Barberos,Integer> {
}

