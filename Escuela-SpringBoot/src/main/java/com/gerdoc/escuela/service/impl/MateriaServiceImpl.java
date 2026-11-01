package com.gerdoc.escuela.service.impl;
    import com.gerdoc.escuela.model.Materia;
    import com.gerdoc.escuela.repository.MateriaRepository;
    import com.gerdoc.escuela.service.MateriaService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.http.HttpStatus;
    import org.springframework.web.server.ResponseStatusException;
    import org.springframework.data.domain.Sort;
    import java.util.List;
    @Service @RequiredArgsConstructor @Transactional
    public class MateriaServiceImpl implements MateriaService {
        private final MateriaRepository repository;
        @Transactional(readOnly=true)
        public List<Materia> findAll() { return repository.findAll(Sort.by("id")); }
        @Transactional(readOnly=true)
        public Materia findById(Long id) { return repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Registro no encontrado")); }
        public Materia save(Materia value) { value.setId(null); return repository.saveAndFlush(value); }
        public Materia update(Long id,Materia value) {
            Materia existing=findById(id);
            existing.setNombre(value.getNombre());
existing.setCreditos(value.getCreditos());
            return repository.saveAndFlush(existing);
        }
        public void deleteById(Long id) { repository.delete(findById(id)); repository.flush(); }
    }
