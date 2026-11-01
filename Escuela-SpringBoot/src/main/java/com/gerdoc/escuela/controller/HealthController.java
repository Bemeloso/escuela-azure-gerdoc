package com.gerdoc.escuela.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.RequiredArgsConstructor;
import java.util.Map;
@RestController @RequiredArgsConstructor
public class HealthController {
    private final JdbcTemplate jdbc;
    @GetMapping("/api/health") public Map<String,Object> health() {
        return Map.of("status","UP","database","ESCUELA","conexion",jdbc.queryForObject("SELECT 1",Integer.class)==1);
    }
}
