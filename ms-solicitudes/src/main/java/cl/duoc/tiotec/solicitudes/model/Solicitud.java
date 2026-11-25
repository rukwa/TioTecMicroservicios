package cl.duoc.tiotec.solicitudes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Entidad = tabla "solicitudes" en MySQL.
 * Las validaciones (@NotBlank, @Size, @Pattern) se revisan en el controlador con @Valid:
 * si algo no cumple, la API responde 400 con el mensaje de cada campo.
 */
@Entity
@Table(name = "solicitudes")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 60)
    private String categoria;

    @NotBlank(message = "El dispositivo es obligatorio")
    @Column(nullable = false, length = 80)
    private String dispositivo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 20, max = 500, message = "La descripción debe tener entre 20 y 500 caracteres")
    @Column(nullable = false, length = 500)
    private String descripcion;

    // ENVIADA → EN_REVISION → RESUELTA
    @Pattern(regexp = "ENVIADA|EN_REVISION|RESUELTA", message = "Estado no válido")
    @Column(nullable = false, length = 20)
    private String estado;

    // Formato de la fecha en el JSON: 2026-11-24T22:30:15
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime fecha;

    public Solicitud() {
    }

    public Solicitud(String categoria, String dispositivo, String descripcion, String estado) {
        this.categoria = categoria;
        this.dispositivo = dispositivo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    /** Se ejecuta justo antes del primer INSERT: completa estado y fecha si faltan. */
    @PrePersist
    void alCrear() {
        if (fecha == null) fecha = LocalDateTime.now();
        if (estado == null) estado = "ENVIADA";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDispositivo() { return dispositivo; }
    public void setDispositivo(String dispositivo) { this.dispositivo = dispositivo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
