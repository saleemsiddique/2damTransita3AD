package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.AccesibilidadPunto;
import com.es.iesmz.transita3.Transita.domain.EVisibilidad;
import com.es.iesmz.transita3.Transita.domain.Punto;
import com.es.iesmz.transita3.Transita.domain.TipoPunto;
import com.es.iesmz.transita3.Transita.exception.AccesibilidadNotFoundException;
import com.es.iesmz.transita3.Transita.exception.PuntoNotFoundException;
import com.es.iesmz.transita3.Transita.exception.TipoNotFoundException;
import com.es.iesmz.transita3.Transita.service.PuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;

@RestController
public class PuntoController {
    @Autowired
    private PuntoService puntoService;


    @Operation(summary = "Obtiene el primer punto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/inicial")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Punto> getPrimerPunto() {
        Punto punto = puntoService.getPrimerPunto();

        return new ResponseEntity<>(punto, HttpStatus.OK);
    }
    @Operation(summary = "Obtiene el listado de puntos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Punto>> getPunto() {
        System.out.println("Método getPunto ha sido llamado.");
        Set<Punto> puntos = null;
        puntos = puntoService.findAll();

        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de puntos entre id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/{idInicial}-{idFinal}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Punto>> getPuntoPorPaginas(@PathVariable int idInicial, @PathVariable int idFinal) {
        Set<Punto> puntos = null;
        puntos = puntoService.findAllByPages(idInicial, idFinal);

        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un punto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Punto con ese ID",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Punto> getPuntoById(@PathVariable long id) {
        System.out.println("Método getPunto ha sido llamado.");
        Punto punto = puntoService.findById(id)
                .orElseThrow(() -> new PuntoNotFoundException(id));
        return new ResponseEntity<>(punto, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene un punto por su latitud y longitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Punto con esas coordenadas",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/punto/coordenadas/{latitud}/{longitud}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Optional<Punto>> getPuntoByCoordenadas(@PathVariable Double latitud, @PathVariable Double longitud) {
        Optional<Punto> punto = puntoService.findByLatitudLongitud(latitud, longitud);
        return new ResponseEntity<Optional<Punto>>(punto, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de puntos por tipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/tipo/{tipo}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Punto>> getPuntoByTipo(@PathVariable int tipo) {
        TipoPunto tipoPunto = null;

        if (tipo == 0) {
            tipoPunto = TipoPunto.ACCESO;
        } else if (tipo == 1) {
            tipoPunto = TipoPunto.LUGAR;
        } else {
            throw new TipoNotFoundException("Tipo de Punto no existente");
        }

        Set<Punto> puntos = puntoService.findByTipoPunto(tipoPunto);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene la visibilidad del punto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/visibilidad/{tipo}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Punto>> getPuntoByVisibilidad(@PathVariable int tipo) {
        EVisibilidad tipoPunto = null;

        if (tipo == 0) {
            tipoPunto = EVisibilidad.GLOBAL;
        } else if (tipo == 1) {
            tipoPunto = EVisibilidad.FAVORITO;
        } else if (tipo == 2) {
            tipoPunto = EVisibilidad.INCIDENCIA;
        } else if (tipo == 3) {
            tipoPunto = EVisibilidad.OCULTO;
        } else {
            throw new TipoNotFoundException("Tipo de Punto no existente");
        }

        Set<Punto> puntos = puntoService.findByVisibilidadPunto(tipoPunto);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de puntos por latitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/latitud/{latitud}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Punto>> getPuntoByLatitud(@PathVariable double latitud) {
        Set<Punto> puntos = puntoService.findByLatitud(latitud);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de puntos por longitud")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/longitud/{longitud}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Punto>> getPuntoByLongitud(@PathVariable double longitud) {
        Set<Punto> puntos = puntoService.findByLongitud(longitud);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de puntos por accesibilidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/accesibilidad/{accesibilidad}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Punto>> getPuntoByAccesibilidad(@PathVariable int accesibilidad) {
        AccesibilidadPunto accesibilidadPunto = null;

        if (accesibilidad == 0) {
            accesibilidadPunto = AccesibilidadPunto.ACCESIBLE;
        } else if (accesibilidad == 1) {
            accesibilidadPunto = AccesibilidadPunto.NO_ACCESIBLE;
        } else if (accesibilidad == 2) {
            accesibilidadPunto = AccesibilidadPunto.PARCIALMENTE_ACCESIBLE;
        } else {
            throw new AccesibilidadNotFoundException("Tipo de Accesibilidad no existente");
        }

        Set<Punto> puntos = puntoService.findByAccesibilidadPunto(accesibilidadPunto);
        return new ResponseEntity<>(puntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de puntos por tipo, accesibilidad y visibilidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/tipo/{tipo}/accesibilidad/{accesibilidad}/visibilidad/{visibilidad}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Punto>> getPuntoByTipoAccesibilidadVisibilidad(@PathVariable String tipo, @PathVariable String accesibilidad, @PathVariable String visibilidad) {


        Set<Punto> puntos = puntoService.findByTipoAccesibilidadVisibilidad(tipo, accesibilidad, visibilidad);
        return new ResponseEntity<>(puntos, HttpStatus.OK);

    }

    @Operation(summary = "Obtiene numero de puntos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/count")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Integer> getPuntosCount() {
        int cantidadPuntos = Math.toIntExact(puntoService.countPunto()) ;
        return new ResponseEntity<>(cantidadPuntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene numero de puntos con filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de puntos",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Punto.class)))
            )})
    @GetMapping("/puntos/count/filtros")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Integer> getPuntosCountConFiltros(
            @RequestParam(name = "tipoPunto", required = false) String tipoPunto,
            @RequestParam(name = "accesibilidadPunto", required = false) String accesibilidadPunto,
            @RequestParam(name = "visibilidadPunto", required = false) String visibilidadPunto) {

        int cantidadPuntos = Math.toIntExact(puntoService.countPuntoConFiltros(tipoPunto, accesibilidadPunto, visibilidadPunto));
        return new ResponseEntity<>(cantidadPuntos, HttpStatus.OK);
    }


    @Operation(summary = "Añade un nuevo punto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Punto agregado",
                    content = @Content(schema = @Schema(implementation = Punto.class))
            )})
    @PostMapping("/puntos")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Punto> addPunto(@RequestBody Punto punto) {
        Punto nuevoPunto = puntoService.addPunto(punto);
        return new ResponseEntity<>(nuevoPunto, HttpStatus.OK);
    }

    @GetMapping("puntos/filtrados")
    public ResponseEntity<List<Punto>> buscarPuntosConFiltros(
            @RequestParam(name = "tipoPunto", required = false) String tipoPunto,
            @RequestParam(name = "accesibilidadPunto", required = false) String accesibilidadPunto,
            @RequestParam(name = "visibilidadPunto", required = false) String visibilidadPunto,
            @RequestParam(name = "idInicial", required = true) int idInicial,
            @RequestParam(name = "idFinal", required = true) int idFinal) {

        List<Punto> puntosFiltrados = puntoService.buscarPuntosConFiltros(tipoPunto, accesibilidadPunto, visibilidadPunto, idInicial, idFinal);
        return new ResponseEntity<>(puntosFiltrados, HttpStatus.OK);
    }

    @Operation(summary = "Modifica un punto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Punto modificado",
                    content = @Content(schema = @Schema(implementation = Punto.class))
            )})
    @PutMapping("/punto/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Punto> modifyPunto(@PathVariable long id,
                                             @RequestBody Punto nuevoPunto) {
        Punto punto = puntoService.findById(id)
                .orElseThrow(() -> new PuntoNotFoundException(id));
        punto = puntoService.modifyPunto(id, nuevoPunto);
        return new ResponseEntity<>(punto, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un punto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el punto", content = @Content(schema = @Schema(implementation =
                    Response.class))),
            @ApiResponse(responseCode = "404", description = "El punto no existe", content = @Content(schema = @Schema(implementation =
                    Response.class)))
    })
    @DeleteMapping("punto/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Punto> deletePunto(@PathVariable long id) {
        Punto punto = puntoService.findById(id)
                .orElseThrow(() -> new PuntoNotFoundException(id));
        puntoService.deletePunto(id);
        return new ResponseEntity(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(PuntoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response>
    handleException(PuntoNotFoundException pnfe) {
        Response response = Response.errorResonse(NOT_FOUND, pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
