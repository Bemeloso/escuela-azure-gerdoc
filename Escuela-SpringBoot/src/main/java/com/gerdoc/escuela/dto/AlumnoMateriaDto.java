package com.gerdoc.escuela.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record AlumnoMateriaDto(Long id,@NotNull @Positive Long alumnoId,@NotNull @Positive Long materiaId,
    @DecimalMin("0.00") @DecimalMax("10.00") @Digits(integer=2,fraction=2) BigDecimal calificacion) { }
