package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;  // Cambiado de LocalDateTime a LocalDate
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.format.DateTimeFormatter;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    // Hora de inicio de la cita
    @NotNull(message = "La hora de inicio es requerida")
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    // Hora de fin de la cita
    @NotNull(message = "La hora de fin es requerida")
    @Column(name = "hora_fin")
    private LocalTime horaFin;

    // Notas adicionales
    private String notas;

    // Estado de la cita (p.ej. “AGENDADA”, “CANCELADA”)
    @NotBlank(message = "El estado es requerido")
    private String estado;

    // === Constructor vacío requerido por Hibernate ===
    public Citas() {}

    // === Getters y Setters ===

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
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
    public String getHoraInicioFormatted() {
        return horaInicio != null ? horaInicio.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }

    public String getHoraFinFormatted() {
        return horaFin != null ? horaFin.format(DateTimeFormatter.ofPattern("HH:mm")) : "";
    }
}

