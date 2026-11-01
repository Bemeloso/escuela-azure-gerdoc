package com.gerdoc.escuela.service.impl;
    import com.gerdoc.escuela.model.Alumno;
    import com.gerdoc.escuela.repository.AlumnoRepository;
    import com.gerdoc.escuela.service.AlumnoService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.server.ResponseStatusException;
    import org.springframework.data.domain.Sort;
    import java.util.List;
    @Service @RequiredArgsConstructor @Transactional
    public class AlumnoServiceImpl implements AlumnoService {
        private final AlumnoRepository repository;
        @Transactional(readOnly=true)
        public List<Alumno> findAll() { return repository.findAll(Sort.by("id")); }
        @Transactional(readOnly=true)
        public Alumno findById(Long id) { return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Registro no encontrado")); }
        public Alumno save(Alumno value) { value.setId(null); return repository.saveAndFlush(value); }
        public Alumno update(Long id,Alumno value) {
            Alumno existing=findById(id);
            existing.setNombre(value.getNombre());
existing.setEdad(value.getEdad());
existing.setPromedio(value.getPromedio());
            return repository.saveAndFlush(existing);
        }
        public void deleteById(Long id) { repository.delete(findById(id)); repository.flush(); }
    }
