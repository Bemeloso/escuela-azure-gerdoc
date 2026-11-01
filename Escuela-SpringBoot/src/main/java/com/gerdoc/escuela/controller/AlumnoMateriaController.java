package com.gerdoc.escuela.controller;
import com.gerdoc.escuela.dto.AlumnoMateriaDto;
import com.gerdoc.escuela.service.AlumnoMateriaService;
import com.gerdoc.escuela.model.ProfesorMateriaId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@RestController @RequestMapping("/api/alumno-materia") @RequiredArgsConstructor @Tag(name="AlumnoMateria")
public class AlumnoMateriaController {
    private final AlumnoMateriaService service;
    @GetMapping public List<AlumnoMateriaDto> findAll() {return service.findAll();}
    @GetMapping("/{id}") public AlumnoMateriaDto findById(@PathVariable Long id) {return service.findById(id);}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public AlumnoMateriaDto create(@Valid @RequestBody AlumnoMateriaDto value) {return service.save(value);}
    @PutMapping("/{id}") public AlumnoMateriaDto update(@PathVariable Long id,@Valid @RequestBody AlumnoMateriaDto value) {return service.update(id,value);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) {service.deleteById(id);}
}
