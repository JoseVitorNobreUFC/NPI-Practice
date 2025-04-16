package com.example.demo.service.impl;

import com.example.demo.model.Curso;
import com.example.demo.model.Turno;
import com.example.demo.repository.CursoRepository;
import com.example.demo.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Optional<Curso> findById(Integer id) {
        return cursoRepository.findById(id);
    }

    @Override
    public Curso findByNome(String nome) {
        return cursoRepository.findByNome(nome);
    }

    @Override
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    public int insert(String nome, String sigla, Turno turno) {
        return cursoRepository.insertCurso(nome, sigla, turno.name());
    }

    @Override
    public int update(Integer id, String nome, String sigla, Turno turno) {
        Optional<Curso> optCurso = cursoRepository.findById(id);
        if (optCurso.isEmpty())
            return 0;

        Curso curso = optCurso.get();

        if (nome != null)
            curso.setNome(nome);
        if (sigla != null)
            curso.setSigla(sigla);
        if (turno != null)
            curso.setTurno(turno);

        cursoRepository.save(curso);
        return 1;
    }

    @Override
    public int delete(Integer id) {
        return cursoRepository.deleteCursoById(id);
    }
}
