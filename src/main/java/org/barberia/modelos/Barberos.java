

package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Barberos")
public class Barberos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank(message = "La especialidad es requerida")
    private String especialidad;

    @NotBlank(message = "La disponibilidad es requerida")
    private String disponibilidad;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // Nombre de la columna en la tabla Barberos
    private Usuarios usuario;

    public Barberos() {}

    // Getters y Setters
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}


