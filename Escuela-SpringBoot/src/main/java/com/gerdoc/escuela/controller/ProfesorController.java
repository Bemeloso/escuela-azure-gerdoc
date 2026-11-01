package com.gerdoc.escuela.controller;
import com.gerdoc.escuela.model.Profesor;
import com.gerdoc.escuela.service.ProfesorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@RestController @RequestMapping("/api/profesores") @RequiredArgsConstructor @Tag(name="Profesor")
public class ProfesorController {
    private final ProfesorService service;
    @GetMapping public List<Profesor> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Profesor findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Profesor create(@Valid @RequestBody Profesor value) { return service.save(value); }
    @PutMapping("/{id}") public Profesor update(@PathVariable Long id,@Valid @RequestBody Profesor value) { return service.update(id,value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
