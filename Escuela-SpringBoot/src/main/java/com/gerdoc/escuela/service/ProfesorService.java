package com.gerdoc.escuela.service;
import com.gerdoc.escuela.model.Profesor;
import java.util.List;
public interface ProfesorService {
    List<Profesor> findAll();
    Profesor findById(Long id);
    Profesor save(Profesor value);
    Profesor update(Long id,Profesor value);
    void deleteById(Long id);
}
