package com.example.demo.dto;

import com.example.demo.model.Turno;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CursoDto {
  public String nome;
  public String sigla;
  public Turno turno;
}
