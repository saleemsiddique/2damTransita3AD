package com.es.iesmz.transita3.Transita.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Punto")
public class Punto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "Descripción")
    private String descripcion;
    @Column(name = "Tipo")
    private TipoPunto tipoPunto;
    @Column(name = "Foto")
    private String foto;
    @Column(name = "Latitud")
    private double latitud;
    @Column(name = "Longitud")
    private double longitud;
    @Column(name = "Accesibilidad")
    private AccesibilidadPunto accesibilidadPunto;

    @OneToMany(mappedBy = "punto", cascade = CascadeType.ALL)
    private List<Incidencia> incidencias;

}
