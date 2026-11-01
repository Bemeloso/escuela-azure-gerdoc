package com.gerdoc.escuela.service;
import com.gerdoc.escuela.dto.ProfesorMateriaDto;
import com.gerdoc.escuela.model.ProfesorMateriaId;
import java.util.List;
public interface ProfesorMateriaService {
    List<ProfesorMateriaDto> findAll();
    ProfesorMateriaDto findById(ProfesorMateriaId id);
    ProfesorMateriaDto save(ProfesorMateriaDto value);
    ProfesorMateriaDto update(ProfesorMateriaId id,ProfesorMateriaDto value);
    void deleteById(ProfesorMateriaId id);
}
