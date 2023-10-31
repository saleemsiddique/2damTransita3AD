package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.Zona;
import com.es.iesmz.transita3.Transita.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/zonas")
public class ZonaController {

    @Autowired
    private ZonaService zonaService;

    @GetMapping
    public ResponseEntity<Set<Zona>> getAllZonas() {
        Set<Zona> zonas = zonaService.findAll();
        return new ResponseEntity<>(zonas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zona> getZonaById(@PathVariable long id) {
        Zona zona = zonaService.findById(id).orElse(null);
        if (zona != null) {
            return new ResponseEntity<>(zona, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("nombre/{nombre}")
    public ResponseEntity<Set<Zona>> getZonaByNombre(@PathVariable String nombre) {
        Set<Zona> zonas = zonaService.findByNombreStartingWith(nombre);
        return new ResponseEntity<>(zonas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Zona> createZona(@RequestBody Zona zona) {
        Zona nuevaZona = zonaService.addZona(zona);
        return new ResponseEntity<>(nuevaZona, HttpStatus.CREATED);
    }

    @PutMapping("modificar/{id}")
    public ResponseEntity<Zona> updateZona(@PathVariable long id, @RequestBody Zona nuevaZona) {
        Zona zonaActualizada = zonaService.modifyZona(id, nuevaZona);
        if (zonaActualizada != null) {
            return new ResponseEntity<>(zonaActualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<Void> deleteZona(@PathVariable long id) {
        zonaService.deleteZona(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/puntos")
    public ResponseEntity<Set<Punto>> getPuntosByZona(@PathVariable long id) {
        Zona zona = zonaService.findById(id).orElse(null);
        if (zona != null) {
            Set<Punto> puntos = zonaService.findPuntosByZona(zona);
            return new ResponseEntity<>(puntos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}