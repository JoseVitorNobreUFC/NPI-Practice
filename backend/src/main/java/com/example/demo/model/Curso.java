package com.example.demo.model;

import javax.persistence.*;

import org.springframework.lang.NonNull;

@Entity
public class Curso {
  @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NonNull
	@Column(nullable = false)
	private String nome;

  @NonNull
  @Column(nullable = false)
  private String sigla;

  @NonNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Turno turno;

  public Curso() {}
  public Curso (Integer id, String nome, String sigla, Turno turno) {
    this.id = id;
    this.nome = nome;
    this.sigla = sigla;
    this.turno = turno;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSigla() {
    return sigla;
  }

  public void setSigla(String sigla) {
    this.sigla = sigla;
  }

  public Turno getTurno() {
    return turno;
  }

  public void setTurno(Turno turno) {
    this.turno = turno;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Curso other = (Curso) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
}
