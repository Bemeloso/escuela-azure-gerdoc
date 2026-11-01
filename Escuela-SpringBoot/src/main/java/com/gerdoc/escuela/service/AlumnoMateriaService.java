package com.gerdoc.escuela.service;
import com.gerdoc.escuela.dto.AlumnoMateriaDto;
import java.util.List;
public interface AlumnoMateriaService {
    List<AlumnoMateriaDto> findAll();
    AlumnoMateriaDto findById(Long id);
    AlumnoMateriaDto save(AlumnoMateriaDto value);
    AlumnoMateriaDto update(Long id,AlumnoMateriaDto value);
    void deleteById(Long id);
}
