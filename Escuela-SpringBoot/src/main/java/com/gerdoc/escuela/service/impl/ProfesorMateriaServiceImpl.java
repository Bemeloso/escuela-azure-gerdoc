package com.gerdoc.escuela.service.impl;
import com.gerdoc.escuela.dto.ProfesorMateriaDto;
import com.gerdoc.escuela.model.*;
import com.gerdoc.escuela.repository.*;
import com.gerdoc.escuela.service.ProfesorMateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@Service @RequiredArgsConstructor @Transactional
public class ProfesorMateriaServiceImpl implements ProfesorMateriaService {
    private final ProfesorMateriaRepository repository;
    private final ProfesorRepository profesores;
    private final MateriaRepository materias;
    private ProfesorMateriaDto dto(ProfesorMateria x) {return new ProfesorMateriaDto(x.getProfesor().getId(),x.getMateria().getId());}
    private ProfesorMateria entity(ProfesorMateriaId id) {return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Asignación no encontrada"));}
    private ProfesorMateria build(ProfesorMateriaDto value) {
        ProfesorMateria x=new ProfesorMateria();
        x.setProfesor(profesores.findById(value.profesorId()).orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"El profesor no existe")));
        x.setMateria(materias.findById(value.materiaId()).orElseThrow(()->new ResponseStatusException(HttpStatus.CONFLICT,"La materia no existe")));
        return x;
    }
    @Transactional(readOnly=true) public List<ProfesorMateriaDto> findAll() {return repository.findAll().stream().map(this::dto).toList();}
    @Transactional(readOnly=true) public ProfesorMateriaDto findById(ProfesorMateriaId id) {return dto(entity(id));}
    public ProfesorMateriaDto save(ProfesorMateriaDto value) {
        ProfesorMateriaId key=new ProfesorMateriaId(value.profesorId(),value.materiaId());
        if(repository.existsById(key)) throw new ResponseStatusException(HttpStatus.CONFLICT,"La asignación ya existe");
        return dto(repository.saveAndFlush(build(value)));
    }
    public ProfesorMateriaDto update(ProfesorMateriaId id,ProfesorMateriaDto value) {
        ProfesorMateria old=entity(id);
        ProfesorMateriaId next=new ProfesorMateriaId(value.profesorId(),value.materiaId());
        if(id.equals(next)) return dto(old);
        if(repository.existsById(next)) throw new ResponseStatusException(HttpStatus.CONFLICT,"La asignación ya existe");
        ProfesorMateria replacement=build(value);
        // La clave primaria es compuesta: reemplazar ambas partes dentro de la misma transacción.
        repository.delete(old);repository.flush();
        return dto(repository.saveAndFlush(replacement));
    }
    public void deleteById(ProfesorMateriaId id) {repository.delete(entity(id));repository.flush();}
}
