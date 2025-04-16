package com.example.demo.dto;

import com.example.demo.model.Turno;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoDto {
  private String nome;
  private String sigla;
  private Turno turno;

  public String getNome() {
    return nome;
  }

  public String getSigla() {
    return sigla;
  }

  public Turno getTurno() {
    return turno;
  }
}
