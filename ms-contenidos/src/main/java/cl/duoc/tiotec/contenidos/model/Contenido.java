package cl.duoc.tiotec.contenidos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Entidad = tabla "contenidos" en MySQL.
 * Cada fila es un consejo de la sección "Aprende", asociado a una de las 4 áreas del caso.
 */
@Entity
@Table(name = "contenidos")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mismos nombres que el enum Area de la app
    @NotBlank(message = "El área es obligatoria")
    @Pattern(regexp = "RESPALDOS|ACTUALIZACIONES|AUTENTICACION|RECUPERACION", message = "Área no válida")
    @Column(nullable = false, length = 20)
    private String area;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 80, message = "El título debe tener entre 3 y 80 caracteres")
    @Column(nullable = false, length = 80)
    private String titulo;

    @NotBlank(message = "El detalle es obligatorio")
    @Size(min = 10, max = 300, message = "El detalle debe tener entre 10 y 300 caracteres")
    @Column(nullable = false, length = 300)
    private String detalle;

    public Contenido() {
    }

    public Contenido(String area, String titulo, String detalle) {
        this.area = area;
        this.titulo = titulo;
        this.detalle = detalle;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}
