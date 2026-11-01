package com.gerdoc.escuela.model;
import lombok.*;
import java.io.Serializable;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class ProfesorMateriaId implements Serializable {
    private Long profesor;
    private Long materia;
}
