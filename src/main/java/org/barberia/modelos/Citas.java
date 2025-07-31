package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Citas")

public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank (message = "Nombre del cliente")
    private  Integer ClienteId;
    @NotBlank (message = "Nombre del servicio")
    private Integer ServicioId;
    @NotBlank (message = "Nombre del barbero")
    private  Integer  BarberoId;
    @NotBlank (message =  "La fecha es requerida")
    private LocalDateTime Fecha;
    @NotBlank (message =  "Una nota es requerida")
    private  String Notas;
    @NotBlank (message =  "El estado es requerido")
    private  String Estado;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getClienteId() {
        return ClienteId;
    }

    public void setClienteId(Integer clienteId) {
        ClienteId = clienteId;
    }

    public Integer getServicioId() {
        return ServicioId;
    }

    public void setServicioId(Integer servicioId) {
        ServicioId = servicioId;
    }

    public Integer getBarberoId() {
        return BarberoId;
    }

    public void setBarberoId(Integer barberoId) {
        BarberoId = barberoId;
    }

    public LocalDateTime getFecha() {
        return Fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        Fecha = fecha;
    }

    public String getNotas() {
        return Notas;
    }

    public void setNotas(String notas) {
        Notas = notas;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
