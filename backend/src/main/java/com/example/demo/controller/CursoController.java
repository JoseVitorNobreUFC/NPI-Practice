package com.example.demo.controller;

import com.example.demo.model.Curso;
import com.example.demo.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // Criar novo curso
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Curso curso) {
        try {
            if (curso.getNome() == null || curso.getSigla() == null || curso.getTurno() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Campos obrigatórios não preenchidos"));
            }

            int lines = cursoService.insert(curso.getNome(), curso.getSigla(), curso.getTurno());
            return ResponseEntity.ok(Map.of("message", lines + " linha(s) afetada(s)"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao cadastrar curso"));
        }
    }

    // Buscar curso por ID
    @GetMapping("{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        try {
            Optional<Curso> curso = cursoService.findById(id);
            if (curso.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Curso não encontrado"));
            }
            return ResponseEntity.ok(curso.get());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao buscar curso"));
        }
    }

    // Buscar todos os cursos
    @GetMapping
    public ResponseEntity<List<Curso>> getAll() {
        try {
            List<Curso> cursos = cursoService.findAll();
            return ResponseEntity.ok(cursos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // Atualizar curso
    @PutMapping("{id}")
    public ResponseEntity<Object> update(@RequestBody Curso curso, @PathVariable Integer id) {
        try {
            int lines = cursoService.update(id, curso.getNome(), curso.getSigla(), curso.getTurno());
            if (lines == 0) {
                return ResponseEntity.status(404).body(Map.of("error", "Curso não encontrado"));
            }
            return ResponseEntity.ok(Map.of("message", lines + " linha(s) afetada(s)"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao atualizar curso"));
        }
    }

    // Deletar curso
    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            int lines = cursoService.delete(id);
            if (lines == 0) {
                return ResponseEntity.status(404).body(Map.of("error", "Curso não encontrado"));
            }
            return ResponseEntity.ok(Map.of("message", lines + " linha(s) afetada(s)"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao deletar curso"));
        }
    }
}
