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
@Setter
@ToString
@Entity
@Table(name = "cliente",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nombreUsuario"),
                @UniqueConstraint(columnNames = "contrasenya")
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
    @Column(name = "apellidos")
    private String apellidos;

    @NotBlank
    @Size(max = 50)
    @Column(name = "nombre_usuario")
    @Email
    private String nombreUsuario;

    @NotBlank
    @Size(max = 120)
    private String contrasenya;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "roles_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Rol> rols = new HashSet<>();

    public Cliente(String nombre, String apellidos, String nombreUsuario, String contrasenya) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
    }
}