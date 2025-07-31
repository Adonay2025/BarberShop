package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Roles")

public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank (message = "El rol es requerido")
    private  String nombrerol;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(String nombrerol) {
        this.nombrerol = nombrerol;
    }
}

