package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Optional<Usuario>> find(@PathVariable Integer id) {
        // Busca usuário pelo id e retornar usuário...
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<String> create(@RequestBody Usuario usuario) {
        // Cadastrar usuário e retornar usuário cadastrado...
        int lines = usuarioService.insert(usuario.getNome(), usuario.getEmail(), usuario.getPassword(), usuario.isHabilitado());
        return ResponseEntity.ok(lines + " linhas afetadas");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        // Deletar usuário pelo id...
        int lines = usuarioService.delete(id);
        return ResponseEntity.ok(lines + " linhas afetadas");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> update(@RequestBody Usuario usuario, @PathVariable Integer id) {
        // Atualizar usuário pelo id...
        int lines = usuarioService.update(id, usuario.getNome(), usuario.getEmail(), usuario.getPassword(), usuario.isHabilitado());
        return ResponseEntity.ok(lines + " linhas afetadas");
    }



}

