package com.gerdoc.escuela.service;
import com.gerdoc.escuela.model.Alumno;
import java.util.List;
public interface AlumnoService {
    List<Alumno> findAll();
    Alumno findById(Long id);
    Alumno save(Alumno value);
    Alumno update(Long id,Alumno value);
    void deleteById(Long id);
}
