package com.gerdoc.escuela.dto;
import jakarta.validation.constraints.*;
public record ProfesorMateriaDto(@NotNull @Positive Long profesorId,@NotNull @Positive Long materiaId) { }
