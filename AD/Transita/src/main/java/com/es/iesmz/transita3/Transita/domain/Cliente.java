package com.es.iesmz.transita3.Transita.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cliente",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nombre_usuario"),
                @UniqueConstraint(columnNames = "contrasenya")
        })
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
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
    @Email
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotBlank
    @Size(max = 120)
    @Column(name = "contrasenya")
    private String contrasenya;

    @Column(name = "estado")
    private ECliente estadoCuenta;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "roles_usuario",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> rols = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Incidencia> incidencias = new ArrayList<>();

    public Cliente(String nombre, String apellidos, String nombreUsuario, String contrasenya) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
        this.estadoCuenta = ECliente.ACTIVADO;
    } 
}