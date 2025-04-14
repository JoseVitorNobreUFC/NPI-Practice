package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Usuario;

public interface UsuarioService {

  Usuario findByEmail(String email);

  List<Usuario> findAll();
}

