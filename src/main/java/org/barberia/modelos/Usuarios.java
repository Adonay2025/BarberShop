package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    @NotBlank(message = "El correo es requerido")
    private String correo;
    @NotBlank(message = "La contraseña es requerida")
    private String contrasena;
    @NotBlank(message = "El teléfono es requerido")
    private String telefono;

    // === Relación con Roles ===
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id")
    private Roles role;

    // === Relación con Citas ===
    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Citas> citas = new ArrayList<>();

    public Usuarios() {}

    // ——— Getters y Setters ———

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Roles getRole() { return role; }
    public void setRole(Roles role) { this.role = role; }

    public List<Citas> getCitas() { return citas; }
    public void setCitas(List<Citas> citas) { this.citas = citas; }
}
