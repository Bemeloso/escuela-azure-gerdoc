package org.gerdoc.model;
import javax.persistence.*;
import java.math.BigDecimal;
import lombok.*;
@Entity @Table(name="PROFESOR") @Getter @Setter @NoArgsConstructor
public class Profesor {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID")
    private Long id;
@Column(name="NOMBRE", nullable=false)
private String nombre;
@Column(name="ESPECIALIDAD", nullable=false)
private String especialidad;
}
