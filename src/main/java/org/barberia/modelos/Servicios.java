package org.barberia.modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "Servicios")

public class Servicios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotBlank (message = "El nombre es requerido")
    private  String nombre;
    @NotBlank (message = "El tiempo estimado es requerido")
    private Integer tiempoestimado;
    @NotBlank (message = "El precio es requerido")
    private BigDecimal precio;
    @NotBlank (message = "El estado es requerido")
    private  String estadoservicio;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTiempoestimado() {
        return tiempoestimado;
    }

    public void setTiempoestimado(Integer tiempoestimado) {
        this.tiempoestimado = tiempoestimado;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getEstadoservicio() {
        return estadoservicio;
    }

    public void setEstadoservicio(String estadoservicio) {
        this.estadoservicio = estadoservicio;
    }
}

