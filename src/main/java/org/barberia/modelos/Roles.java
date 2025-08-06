package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El rol es requerido")
    private String nombrerol;

    // === Relación con Usuarios ===
    @OneToMany(
            mappedBy = "role",          // debe coincidir con el nombre del atributo en Usuarios
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Usuarios> usuarios = new ArrayList<>();

    public Roles() {}

    // ——— Getters y Setters ———

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(String nombrerol) {
        this.nombrerol = nombrerol;
    }

    public List<Usuarios> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
    }
}
