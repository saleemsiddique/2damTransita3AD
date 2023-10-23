package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.EstadoIncidencia;
import com.es.iesmz.transita3.Transita.domain.Incidencia;

import com.es.iesmz.transita3.Transita.exception.EstadoIncidenciaNotFoundException;
import com.es.iesmz.transita3.Transita.exception.IncidenciaNotFoundException;

import com.es.iesmz.transita3.Transita.service.IncidenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;
@RestController
public class IncidenciaController {
    @Autowired
    private IncidenciaService incidenciaService;

    @GetMapping("/incidencias")
    public ResponseEntity<Set<Incidencia>> getIncidencia(){
        Set<Incidencia> incidencias = null;
        incidencias = incidenciaService.findAll();

        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @GetMapping("/incidencia/id/{id}")
    public ResponseEntity<Incidencia> getIncidenciaById(@PathVariable long id){
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(()-> new IncidenciaNotFoundException(id));
        return new ResponseEntity<>(incidencia, HttpStatus.OK);
    }

    @GetMapping("/incidencia/estado/{estado}")
    public ResponseEntity<Set<Incidencia>> findByEstado(@PathVariable int estado) {
        EstadoIncidencia estadoIncidencia = null;

        if (estado == 0) {
            estadoIncidencia = EstadoIncidencia.Abierta;
        }
        else if (estado == 1) {
            estadoIncidencia = EstadoIncidencia.Revision;
        }else if (estado == 2) {
            estadoIncidencia = EstadoIncidencia.Cerrada;
        }
        else {
            throw new EstadoIncidenciaNotFoundException("Estado de incidencia no existente");
        }

        Set<Incidencia> incidencias = incidenciaService.findByEstado(estadoIncidencia);
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @GetMapping("/incidencia/duracion/{duracion}")
    public ResponseEntity<Set<Incidencia>> getIncideciaByDuracion(@PathVariable int duracion){
        Set<Incidencia> incidencias = incidenciaService.findByDuracion(duracion);
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @PostMapping("/incidencia")
    public ResponseEntity<Incidencia> addIncidencia(@RequestBody Incidencia incidencia){
        Incidencia nuevaIncidencia = incidenciaService.addIncidencia(incidencia);
        return new ResponseEntity<>(nuevaIncidencia, HttpStatus.OK);
    }

    @PutMapping("/incidencia/modificar/{id}")
    public ResponseEntity<Incidencia> modifyIncidencia(@PathVariable long id,
                                             @RequestBody Incidencia nuevaIncidencia)
    {
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(()-> new IncidenciaNotFoundException(id));
        incidencia = incidenciaService.modifyIncidencia(id, nuevaIncidencia);
        return new ResponseEntity<>(incidencia, HttpStatus.OK);
    }

    @DeleteMapping("/incidencia/eliminar/{id}")
    public ResponseEntity<Incidencia> deleteIncidencia(@PathVariable long id){
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(()-> new IncidenciaNotFoundException(id));
        incidenciaService.deleteIncidencia(id);
        return new ResponseEntity(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(IncidenciaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(IncidenciaNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND,
                pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



}