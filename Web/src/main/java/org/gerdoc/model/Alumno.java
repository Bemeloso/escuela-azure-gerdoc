package org.gerdoc.model;
import javax.persistence.*;
import java.math.BigDecimal;
import lombok.*;
@Entity @Table(name="ALUMNO") @Getter @Setter @NoArgsConstructor
public class Alumno {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID")
    private Long id;
@Column(name="NOMBRE", nullable=false)
private String nombre;
@Column(name="EDAD", nullable=false)
private Integer edad;
@Column(name="PROMEDIO", nullable=false, precision=4, scale=2)
private BigDecimal promedio;
}
