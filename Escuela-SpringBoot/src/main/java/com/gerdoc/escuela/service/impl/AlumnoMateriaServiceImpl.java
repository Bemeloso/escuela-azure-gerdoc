package com.gerdoc.escuela.service.impl;
import com.gerdoc.escuela.dto.AlumnoMateriaDto;
import com.gerdoc.escuela.model.AlumnoMateria;
import com.gerdoc.escuela.repository.*;
import com.gerdoc.escuela.service.AlumnoMateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional
public class AlumnoMateriaServiceImpl implements AlumnoMateriaService {
    private final AlumnoMateriaRepository repository;
    private final AlumnoRepository alumnos;
    private final MateriaRepository materias;
    private AlumnoMateriaDto dto(AlumnoMateria x) {return new AlumnoMateriaDto(x.getId(),x.getAlumno().getId(),x.getMateria().getId(),x.getCalificacion());}
    private AlumnoMateria entity(Long id) {return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Inscripción no encontrada"));}
    private void fill(AlumnoMateria x,AlumnoMateriaDto value) {
        x.setAlumno(alumnos.findById(value.alumnoId()).orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"El alumno no existe")));
        x.setMateria(materias.findById(value.materiaId()).orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"La materia no existe")));
        x.setCalificacion(value.calificacion());
    }
    @Transactional(readOnly=true) public List<AlumnoMateriaDto> findAll() {return repository.findAll(org.springframework.data.domain.Sort.by("id")).stream().map(this::dto).toList();}
    @Transactional(readOnly=true) public AlumnoMateriaDto findById(Long id) {return dto(entity(id));}
    public AlumnoMateriaDto save(AlumnoMateriaDto value) {AlumnoMateria x=new AlumnoMateria();fill(x,value);return dto(repository.saveAndFlush(x));}
    public AlumnoMateriaDto update(Long id,AlumnoMateriaDto value) {AlumnoMateria x=entity(id);fill(x,value);return dto(repository.saveAndFlush(x));}
    public void deleteById(Long id) {repository.delete(entity(id));repository.flush();}
}
