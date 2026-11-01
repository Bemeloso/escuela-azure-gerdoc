package com.gerdoc.escuela.controller;
import com.gerdoc.escuela.model.Alumno;
import com.gerdoc.escuela.service.AlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@RestController @RequestMapping("/api/alumnos") @RequiredArgsConstructor @Tag(name="Alumno")
public class AlumnoController {
    private final AlumnoService service;
    @GetMapping public List<Alumno> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Alumno findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Alumno create(@Valid @RequestBody Alumno value) { return service.save(value); }
    @PutMapping("/{id}") public Alumno update(@PathVariable Long id,@Valid @RequestBody Alumno value) { return service.update(id,value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
