package com.es.iesmz.transita3.Transita.service;


import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.Zona;

import java.util.Optional;
import java.util.Set;

public interface ZonaService {
    Set<Zona> findAll();

    Optional<Zona> findById(long id);
    Zona addZona(Zona zona);
    Set<Zona> findByNombreStartingWith(String nombre);


    Zona findByNombre(String nombre);

    Zona modifyZona(long id, Zona newZona);
    void deleteZona(long id);

    Set<Punto> findPuntosByZona(Zona zona); // Método para obtener puntos de una zona específica
}
