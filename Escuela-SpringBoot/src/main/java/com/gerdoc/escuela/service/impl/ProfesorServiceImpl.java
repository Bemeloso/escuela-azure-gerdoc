package com.gerdoc.escuela.service.impl;
    import com.gerdoc.escuela.model.Profesor;
    import com.gerdoc.escuela.repository.ProfesorRepository;
    import com.gerdoc.escuela.service.ProfesorService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.server.ResponseStatusException;
    import org.springframework.data.domain.Sort;
    import java.util.List;
    @Service @RequiredArgsConstructor @Transactional
    public class ProfesorServiceImpl implements ProfesorService {
        private final ProfesorRepository repository;
        @Transactional(readOnly=true)
        public List<Profesor> findAll() { return repository.findAll(Sort.by("id")); }
        @Transactional(readOnly=true)
        public Profesor findById(Long id) { return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Registro no encontrado")); }
        public Profesor save(Profesor value) { value.setId(null); return repository.saveAndFlush(value); }
        public Profesor update(Long id,Profesor value) {
            Profesor existing=findById(id);
            existing.setNombre(value.getNombre());
existing.setEspecialidad(value.getEspecialidad());
            return repository.saveAndFlush(existing);
        }
        public void deleteById(Long id) { repository.delete(findById(id)); repository.flush(); }
    }
