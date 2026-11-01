package com.gerdoc.escuela.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;
@Entity @Table(name="MATERIA") @Getter @Setter @NoArgsConstructor
public class Materia {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID")
    private Long id;
    @NotBlank @Size(max=100)
@Column(name="NOMBRE", nullable=false)
private String nombre;
@NotNull @Min(1) @Max(100)
@Column(name="CREDITOS", nullable=false)
private Integer creditos;
}
