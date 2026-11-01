package com.gerdoc.escuela.controller;
import com.gerdoc.escuela.dto.ProfesorMateriaDto;
import com.gerdoc.escuela.service.ProfesorMateriaService;
import com.gerdoc.escuela.model.ProfesorMateriaId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@RestController @RequestMapping("/api/profesor-materia") @RequiredArgsConstructor @Tag(name="ProfesorMateria")
public class ProfesorMateriaController {
    private final ProfesorMateriaService service;
    @GetMapping public List<ProfesorMateriaDto> findAll() {return service.findAll();}
    @GetMapping("/{profesorId}-{materiaId}") public ProfesorMateriaDto findById(@PathVariable Long profesorId,@PathVariable Long materiaId) {return service.findById(new ProfesorMateriaId(profesorId,materiaId));}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public ProfesorMateriaDto create(@Valid @RequestBody ProfesorMateriaDto value) {return service.save(value);}
    @PutMapping("/{profesorId}-{materiaId}") public ProfesorMateriaDto update(@PathVariable Long profesorId,@PathVariable Long materiaId,@Valid @RequestBody ProfesorMateriaDto value) {return service.update(new ProfesorMateriaId(profesorId,materiaId),value);}
    @DeleteMapping("/{profesorId}-{materiaId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long profesorId,@PathVariable Long materiaId) {service.deleteById(new ProfesorMateriaId(profesorId,materiaId));}
}
