package com.es.iesmz.transita3.Transita.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "usuario_municipio",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nombre_usuario"),
                @UniqueConstraint(columnNames = "contrasenya"),
                @UniqueConstraint(columnNames = "telefono")
        })
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioMunicipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    @Schema(description = "ID del Usuario Municipio", example = "1", required = true)
    private Long id;

    @NotBlank
    @Size(max = 20)
    @Column(name = "nombre")
    @Schema(description = "Nombre del Usuario Municipio", example = "Pepe", required = true)
    private String nombre;

    @NotBlank
    @Size(max = 20)
    @Column(name = "apellidos")
    @Schema(description = "Apellidos del Usuario Municipio", example = "Villuela", required = true)
    private String apellidos;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(name = "nombre_usuario_municipio")
    @Schema(description = "Nombre de usuario del Usuario Municipio(correo electrónico)", example = "email@email.com", required = true)
    private String nombreUsuario;

    @NotBlank
    @Size(max = 120)
    @Column(name = "contrasenya")
    @Schema(description = "Contraseña del usuario municipio", example = "secretpassword", required = true)
    private String contrasenya;

    @NotBlank
    @Size(max = 20)
    @Column(name = "telefono")
    @Schema(description = "Teléfono del usuario municipio", example = "123456789", required = true)
    private String telefono;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "roles_usuario_municipio",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private Set<Rol> rols = new HashSet<>();

    public UsuarioMunicipio(String nombre, String apellidos, String nombreUsuario, String contrasenya, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreUsuario = nombreUsuario;
        this.contrasenya = contrasenya;
        this.telefono = telefono;
    }

}
