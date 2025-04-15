package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Usuario;

public interface UsuarioService {

  Usuario findByEmail(String email);

  Optional<Usuario> findById(Integer id);

  List<Usuario> findAll();

  int delete(Integer id);

  int update(Integer id, String nome, String email, String password, boolean habilitado);

  int insert(String nome, String email, String password, boolean habilitado);
}

