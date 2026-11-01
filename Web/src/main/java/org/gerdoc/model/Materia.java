package org.gerdoc.model;
import javax.persistence.*;
import java.math.BigDecimal;
import lombok.*;
@Entity @Table(name="MATERIA") @Getter @Setter @NoArgsConstructor
public class Materia {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID")
    private Long id;
@Column(name="NOMBRE", nullable=false)
private String nombre;
@Column(name="CREDITOS", nullable=false)
private Integer creditos;
}
