package com.gerdoc.escuela.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Entity @Table(name="ALUMNO_MATERIA",uniqueConstraints=@UniqueConstraint(columnNames={"ALUMNO_ID","MATERIA_ID"}))
@Getter @Setter @NoArgsConstructor
public class AlumnoMateria {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="ID") private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="ALUMNO_ID",nullable=false) private Alumno alumno;
    @ManyToOne(optional=false) @JoinColumn(name="MATERIA_ID",nullable=false) private Materia materia;
    @Column(name="CALIFICACION",precision=4,scale=2) private BigDecimal calificacion;
}
