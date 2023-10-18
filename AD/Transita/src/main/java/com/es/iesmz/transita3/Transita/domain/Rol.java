package com.es.iesmz.transita3.Transita.domain;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Rol {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole nombre;

  public Rol() {

  }

  public Rol(ERole nombre) {
    this.nombre = nombre;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ERole getNombre() {
    return nombre;
  }

  public void setNombre(ERole nombre) {
    this.nombre = nombre;
  }
}