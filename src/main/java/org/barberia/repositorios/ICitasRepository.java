package org.barberia.repositorios;

import org.barberia.modelos.Citas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICitasRepository extends JpaRepository <Citas, Integer> {
}
