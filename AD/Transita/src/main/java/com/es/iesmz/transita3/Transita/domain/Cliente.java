package com.es.iesmz.transita3.Transita.domain;

import com.es.iesmz.transita3.Transita.domain.ECliente;
import com.es.iesmz.transita3.Transita.domain.Incidencia;
import com.es.iesmz.transita3.Transita.domain.Rol;
import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Schema(description = "ID del cliente", example = "1", required = true)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "nombre")
    @Schema(description = "Nombre del cliente", example = "Isaac", required = true)
    private String nombre;

    @NotBlank
    @Size(max = 20)
    @Column(name = "apellidos")
    @Schema(description = "Apellidos del cliente", example = "Barcos", required = true)
    private String apellidos;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "nombre_usuario")
    @Schema(description = "Nombre de usuario del cliente (correo electrónico)", example = "Isaac@example.com", required = true)
    private String nombreUsuario;

    @NotBlank
    @Size(max = 120)
    @Column(name = "contrasenya")
    @Schema(description = "Contraseña del cliente", example = "secretpassword", required = true)
    private String contrasenya;

    @Column(name = "estado")
    @Schema(description = "Estado de la cuenta del cliente", example = "ACTIVADO", required = true)
    private ECliente estadoCuenta;


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
