package com.gerdoc.escuela.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;
@Entity @Table(name="ALUMNO") @Getter @Setter @NoArgsConstructor
public class Alumno {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID")
    private Long id;
    @NotBlank @Size(max=100)
@Column(name="NOMBRE", nullable=false)
private String nombre;
@NotNull @Min(1) @Max(120)
@Column(name="EDAD", nullable=false)
private Integer edad;
@NotNull @DecimalMin("0.00") @DecimalMax("10.00") @Digits(integer=2,fraction=2)
@Column(name="PROMEDIO", nullable=false, precision=4, scale=2)
private BigDecimal promedio;
}
