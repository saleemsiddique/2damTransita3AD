package com.es.iesmz.transita3.Transita.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString

@Entity
@Table(name = "cliente",
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "nombreUsuario"),
      @UniqueConstraint(columnNames = "email") 
    })
public class Cliente {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  @Column(name = "nombre")
  private String nombre;

  @NotBlank
  @Size(max = 20)
  @Column(name = "apellido")
  private String apellido;

  @NotBlank
  @Size(max = 50)
  @Column(name = "email")
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String contrasenya;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "roles_usuario",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol"))
  private Set<Rol> rols = new HashSet<>();
}
