package com.gerdoc.escuela.controller;
import com.gerdoc.escuela.model.Materia;
import com.gerdoc.escuela.service.MateriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@RestController @RequestMapping("/api/materias") @RequiredArgsConstructor @Tag(name="Materia")
public class MateriaController {
    private final MateriaService service;
    @GetMapping public List<Materia> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Materia findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Materia create(@Valid @RequestBody Materia value) { return service.save(value); }
    @PutMapping("/{id}") public Materia update(@PathVariable Long id,@Valid @RequestBody Materia value) { return service.update(id,value); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
