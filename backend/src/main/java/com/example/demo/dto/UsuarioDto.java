package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {
  private String nome;
  private String email;
  private String password;
  private boolean habilitado;
  private Integer cursoId;

  public String getNome() {
    return nome;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public boolean isHabilitado() {
    return habilitado;
  }

  public Integer getCursoId() {
    return cursoId;
  }
}

