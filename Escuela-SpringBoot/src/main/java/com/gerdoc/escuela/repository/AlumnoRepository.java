package com.gerdoc.escuela.repository;
import com.gerdoc.escuela.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AlumnoRepository extends JpaRepository<Alumno,Long> { }
