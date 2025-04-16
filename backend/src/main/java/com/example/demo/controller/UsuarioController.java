package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> find(@PathVariable Integer id) {
        try {
            Optional<Usuario> usuario = usuarioService.findById(id);
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Usuário não encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao buscar usuário"));
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> create(@RequestBody UsuarioDto usuarioDto) {
        try {
            if (usuarioDto.getEmail() == null || usuarioDto.getNome() == null || usuarioDto.getPassword() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Campos obrigatórios não preenchidos"));
            }

            if (usuarioService.findByEmail(usuarioDto.getEmail()) != null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email já cadastrado"));
            }

            int lines = usuarioService.insert(
                usuarioDto.getNome(),
                usuarioDto.getEmail(),
                usuarioDto.getPassword(),
                usuarioDto.isHabilitado(),
                usuarioDto.getCursoId()
            );
            return ResponseEntity.ok(Map.of("message", lines + " linhas afetadas"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao cadastrar usuário"));
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            Optional<Usuario> usuario = usuarioService.findById(id);
            if (usuario.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Usuário com ID informado não encontrado"));
            }

            if (usuario.get().isHabilitado()) {
                return ResponseEntity.status(400).body(Map.of("error", "Usuário habilitado, impossível deletar"));
            }
            

            int lines = usuarioService.delete(id);
            return ResponseEntity.ok(Map.of("message", lines + " linhas afetadas"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao deletar usuário"));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@RequestBody UsuarioDto usuarioDto, @PathVariable Integer id) {
        try {
            Optional<Usuario> existente = usuarioService.findById(id);
            if (existente.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("error", "Usuário com ID informado não encontrado"));
            }

            if (usuarioService.findByEmail(usuarioDto.getEmail()) != null && !existente.get().getEmail().equals(usuarioDto.getEmail())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Email já cadastrado"));
            }

            int lines = usuarioService.update(
                id,
                usuarioDto.getNome(),
                usuarioDto.getEmail(),
                usuarioDto.getPassword(),
                usuarioDto.isHabilitado(),
                usuarioDto.getCursoId()
            );
            return ResponseEntity.ok(Map.of("message", lines + " linhas afetadas"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Erro ao atualizar usuário"));
        }
    }

}

