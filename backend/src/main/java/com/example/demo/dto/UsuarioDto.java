package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {
  public String nome;
  public String email;
  public String password;
  public boolean habilitado;
  public Integer cursoId;
}

