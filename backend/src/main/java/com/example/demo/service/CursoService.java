package com.example.demo.service;

import com.example.demo.model.Curso;
import com.example.demo.model.Turno;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    Curso findByNome(String nome);
    
    Optional<Curso> findById(Integer id);

    List<Curso> findAll();

    int insert(String nome, String sigla, Turno turno);

    int update(Integer id, String nome, String sigla, Turno turno);

    int delete(Integer id);
}
