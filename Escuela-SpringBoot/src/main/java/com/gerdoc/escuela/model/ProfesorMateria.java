package com.gerdoc.escuela.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="PROFESOR_MATERIA") @IdClass(ProfesorMateriaId.class)
@Getter @Setter @NoArgsConstructor
public class ProfesorMateria {
    @Id @ManyToOne(optional=false) @JoinColumn(name="PROFESOR_ID",nullable=false) private Profesor profesor;
    @Id @ManyToOne(optional=false) @JoinColumn(name="MATERIA_ID",nullable=false) private Materia materia;
}
