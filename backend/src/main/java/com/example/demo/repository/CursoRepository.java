package com.example.demo.repository;

import com.example.demo.model.Curso;
import com.example.demo.model.Turno;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    // Buscar curso por ID
    @Query("SELECT c FROM Curso c WHERE c.id = :id")
    Optional<Curso> findById(@Param("id") Integer id);

    // Buscar todos os cursos
    @Query("SELECT c FROM Curso c")
    List<Curso> findAll();

    // Inserir novo curso (usando nativeQuery)
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO curso (nome, sigla, turno) VALUES (:nome, :sigla, :turno)", nativeQuery = true)
    int insertCurso(@Param("nome") String nome, @Param("sigla") String sigla, @Param("turno") String turno);

    // Atualizar curso
    @Modifying
    @Transactional
    @Query("UPDATE Curso c SET c.nome = :nome, c.sigla = :sigla, c.turno = :turno WHERE c.id = :id")
    int updateCursoById(@Param("id") Integer id, @Param("nome") String nome, @Param("sigla") String sigla, @Param("turno") Turno turno);

    // Deletar curso
    @Modifying
    @Transactional
    @Query("DELETE FROM Curso c WHERE c.id = :id")
    int deleteCursoById(@Param("id") Integer id);
}
