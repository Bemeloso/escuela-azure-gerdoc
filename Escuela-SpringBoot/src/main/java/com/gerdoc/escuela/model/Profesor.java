package com.gerdoc.escuela.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;
@Entity @Table(name="PROFESOR") @Getter @Setter @NoArgsConstructor
public class Profesor {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID")
    private Long id;
    @NotBlank @Size(max=100)
@Column(name="NOMBRE", nullable=false)
private String nombre;
@NotBlank @Size(max=100)
@Column(name="ESPECIALIDAD", nullable=false)
private String especialidad;
}
