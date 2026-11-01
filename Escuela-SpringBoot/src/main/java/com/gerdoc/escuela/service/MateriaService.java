package com.gerdoc.escuela.service;
import com.gerdoc.escuela.model.Materia;
import java.util.List;
public interface MateriaService {
    List<Materia> findAll();
    Materia findById(Long id);
    Materia save(Materia value);
    Materia update(Long id,Materia value);
    void deleteById(Long id);
}
