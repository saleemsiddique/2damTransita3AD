package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import com.es.iesmz.transita3.Transita.exception.AccesibilidadNotFoundException;
import com.es.iesmz.transita3.Transita.exception.PuntoNotFoundException;
import com.es.iesmz.transita3.Transita.exception.TipoNotFoundException;
import com.es.iesmz.transita3.Transita.service.PuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;
@RestController
public class PuntoController {
    @Autowired
    private PuntoService puntoService;

    @GetMapping("/puntos")
    @PreAuthorize("hasRole('ROL_USUARIO')")
    public ResponseEntity<Set<Punto>> getPunto(){
        Set<Punto> puntos = null;
        puntos = puntoService.findAll();

        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @GetMapping("/puntos/id/{id}")
    public ResponseEntity<Punto> getPuntoById(@PathVariable long id){
         Punto punto = puntoService.findById(id)
                 .orElseThrow(()-> new PuntoNotFoundException(id));
         return new ResponseEntity<>(punto, HttpStatus.OK);
    }

    @GetMapping("/puntos/tipo/{tipo}")
    public ResponseEntity<Set<Punto>> getPuntoByTipo(@PathVariable int tipo) {
        TipoPunto tipoPunto = null;

        if (tipo == 0) {
            tipoPunto = TipoPunto.ACCESO;
        }
        else if (tipo == 1) {
            tipoPunto = TipoPunto.LUGAR;
        }
        else {
            throw new TipoNotFoundException("Tipo de Punto no existente");
        }

        Set<Punto> puntos = puntoService.findByTipoPunto(tipoPunto);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @GetMapping("/puntos/latitud/{latitud}")
    public ResponseEntity<Set<Punto>> getPuntoByLatitud(@PathVariable double latitud){
        Set<Punto> puntos = puntoService.findByLatitud(latitud);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @GetMapping("/puntos/longitud/{longitud}")
    public ResponseEntity<Set<Punto>> getPuntoByLongitud(@PathVariable double longitud){
        Set<Punto> puntos = puntoService.findByLongitud(longitud);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @GetMapping("/puntos/accesibilidad/{accesibilidad}")
    public ResponseEntity<Set<Punto>> getPuntoByAccesibilidad(@PathVariable int accesibilidad) {
        AccesibilidadPunto accesibilidadPunto = null;

        if (accesibilidad == 0) {
            accesibilidadPunto = AccesibilidadPunto.ACCESIBLE;
        }
        else if (accesibilidad == 1) {
            accesibilidadPunto = AccesibilidadPunto.NO_ACCESIBLE;
        }
        else {
            throw new AccesibilidadNotFoundException("Tipo de Accesibilidad no existente");
        }

        Set<Punto> puntos = puntoService.findByAccesibilidadPunto(accesibilidadPunto);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @PostMapping("/puntos")
    public ResponseEntity<Punto> addPunto(@RequestBody Punto punto){
        Punto nuevoPunto = puntoService.addPunto(punto);
        return new ResponseEntity<>(nuevoPunto, HttpStatus.OK);
    }

    @PutMapping("/punto/modificar/{id}")
    public ResponseEntity<Punto> modifyPunto(@PathVariable long id,
                                             @RequestBody Punto nuevoPunto)
    {
        Punto punto = puntoService.findById(id)
                .orElseThrow(()-> new PuntoNotFoundException(id));
        punto = puntoService.modifyPunto(id, nuevoPunto);
        return new ResponseEntity<>(punto, HttpStatus.OK);
    }

    @DeleteMapping("punto/eliminar/{id}")
    public ResponseEntity<Punto> deletePunto(@PathVariable long id){
        Punto punto = puntoService.findById(id)
                .orElseThrow(()-> new PuntoNotFoundException(id));
        puntoService.deletePunto(id);
        return new ResponseEntity(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(PuntoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(PuntoNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND,
                pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



}
