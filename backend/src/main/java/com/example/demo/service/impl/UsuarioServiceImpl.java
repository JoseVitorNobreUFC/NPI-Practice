package com.example.demo.service.impl;


import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Curso;
import com.example.demo.model.Turno;
import com.example.demo.model.Usuario;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
   
    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public int delete(Integer id) {
        return usuarioRepository.deleteUserById(id);
    }

    @Override
    public int update(Integer id, String nome, String email, String password, Boolean habilitado, Integer cursoId) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);
        if (optUsuario.isEmpty()) return 0;

        Usuario usuario = optUsuario.get();

        if (nome != null) usuario.setNome(nome);
        if (email != null) usuario.setEmail(email);
        if (password != null && !password.isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(password));
        }
        if (habilitado != null) usuario.setHabilitado(habilitado);
        if (cursoId != null) {
            Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + cursoId));
            usuario.setCurso(curso);
        }

        usuarioRepository.save(usuario);
        return 1;
    }

    @Override
    public int insert(String nome, String email, String password, boolean habilitado, Integer cursoId) {
        String encryptedPassword = passwordEncoder.encode(password);
        return usuarioRepository.insertUser(nome, email, encryptedPassword, habilitado, cursoId);
    }


    @PostConstruct
    public void init() {
        if (cursoRepository.findAll().isEmpty()) {
            Curso cursoTeste = new Curso();
            cursoTeste.setNome("Engenharia de Software");
            cursoTeste.setSigla("ES");
            cursoTeste.setTurno(Turno.MANHA);
            cursoRepository.save(cursoTeste);
            System.out.println("Curso de teste criado com sucesso!");
        }

        if (usuarioRepository.findAll().isEmpty()) {
            Usuario usuarioTeste = new Usuario();
            usuarioTeste.setNome("HomemSegredo");
            usuarioTeste.setEmail("homem@segredo.com");
            usuarioTeste.setPassword("$2a$12$NMgLwu8bqUNT5sZjQPYfXehBWCKRJp4soaRtcZ6cFmnYNZD/o1fiu");
            usuarioTeste.setHabilitado(true);
            usuarioTeste.setCurso(cursoRepository.findById(1).orElseThrow(() -> new RuntimeException("Curso nao encontrado")));
            // Salva o usuário de teste no banco de dados
            usuarioRepository.save(usuarioTeste);
            System.out.println("Usuário de teste criado com sucesso!");
        }
    }

}

