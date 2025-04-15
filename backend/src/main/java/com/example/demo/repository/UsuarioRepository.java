package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findById(@Param("id") Integer id);

    @Query("SELECT u FROM Usuario u")
    List<Usuario> findAll();


    @Modifying
    @Transactional
    @Query("DELETE FROM Usuario u WHERE u.id = :id")
    int deleteUserById(Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.nome = :nome, u.email = :email, u.password = :password, u.habilitado = :habilitado WHERE u.id = :id")
    int updateUserById(Integer id, String nome, String email, String password, boolean habilitado);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Usuario (nome, email, password, habilitado) VALUES (:nome, :email, :password, :habilitado)", nativeQuery = true)
    int insertUser(String nome, String email, String password, boolean habilitado   );

}
