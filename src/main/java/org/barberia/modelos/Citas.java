package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Citas")
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // === Relación con Usuarios (cliente) ===
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuarios usuario;

    // === Relación con Servicios ===
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicios servicio;

    // === Relación con Barberos ===
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "barbero_id", nullable = false)
    private Barberos barbero;

    // Fecha de la cita
    @NotNull(message = "La fecha es requerida")
    private LocalDateTime fecha;

    // Notas adicionales
    @NotBlank(message = "Una nota es requerida")
    private String notas;

    // Estado de la cita (p.ej. “AGENDADA”, “CANCELADA”)
    @NotBlank(message = "El estado es requerido")
    private String estado;

    // === Constructor vacío requerido por Hibernate ===
    public Citas() {}

    // ——— Getters y Setters ———

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Servicios getServicio() {
        return servicio;
    }

    public void setServicio(Servicios servicio) {
        this.servicio = servicio;
    }

    public Barberos getBarbero() {
        return barbero;
    }

    public void setBarbero(Barberos barbero) {
        this.barbero = barbero;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
