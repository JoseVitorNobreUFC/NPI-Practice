package com.example.demo.service.impl;


import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @PostConstruct
    public void init() {
        if (usuarioRepository.findAll().isEmpty()) {
            Usuario usuarioTeste = new Usuario();
            usuarioTeste.setNome("HomemSegredo");
            usuarioTeste.setEmail("homem@segredo.com");
            usuarioTeste.setPassword("$2a$12$NMgLwu8bqUNT5sZjQPYfXehBWCKRJp4soaRtcZ6cFmnYNZD/o1fiu");
            usuarioTeste.setHabilitado(true);
            
            // Salva o usuário de teste no banco de dados
            usuarioRepository.save(usuarioTeste);
            System.out.println("Usuário de teste criado com sucesso!");
        }
    }

}

