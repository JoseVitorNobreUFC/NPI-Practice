package com.example.demo.controller;

import com.example.demo.dto.CursoDto;
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

            if (cursoService.findByNome(curso.getNome()) != null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Curso ja cadastrado"));
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
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody CursoDto cursoDto) {
        try {
            Optional<Curso> curso = cursoService.findById(id);
            if (curso.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Curso nao encontrado"));
            }

            if (cursoService.findByNome(cursoDto.getNome()) != null && !curso.get().getNome().equals(cursoDto.getNome())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Curso ja cadastrado"));
            }

            int linhasAfetadas = cursoService.update(id, cursoDto.getNome(), cursoDto.getSigla(), cursoDto.getTurno());
            return ResponseEntity.ok(Map.of("message", linhasAfetadas + " linha(s) atualizada(s"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao atualizar curso"));
        }
    }

    // Deletar curso
    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            Optional<Curso> curso = cursoService.findById(id);
            if (curso.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Curso nao encontrado"));
            }

            int lines = cursoService.delete(id);
            return ResponseEntity.ok(Map.of("message", lines + " linha(s) afetada(s)"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Não pode deletar curso com alunos matriculados"));
        }
    }
}
