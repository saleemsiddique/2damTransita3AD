package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.EstadoIncidencia;
import com.es.iesmz.transita3.Transita.domain.Incidencia;

import com.es.iesmz.transita3.Transita.exception.EstadoIncidenciaNotFoundException;
import com.es.iesmz.transita3.Transita.exception.IncidenciaNotFoundException;

import com.es.iesmz.transita3.Transita.service.IncidenciaService;

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

import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;
@RestController
public class IncidenciaController {
    @Autowired
    private IncidenciaService incidenciaService;

    @Operation(summary = "Obtiene el listado de incidencias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencias")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Incidencia>> getIncidencia(){
        Set<Incidencia> incidencias = null;
        incidencias = incidenciaService.findAll();

        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene una incidencia determinada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidencia con ese ID",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencia/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Incidencia> getIncidenciaById(@PathVariable long id){
        Incidencia incidencia = incidenciaService.findById(id)
                .orElseThrow(()-> new IncidenciaNotFoundException(id));
        return new ResponseEntity<>(incidencia, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene las incidencias de un cliente especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incidencias con ese Cliente_ID",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencias/clienteid/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERADOR', 'ROLE_USUARIO')")
    public ResponseEntity<Set<Incidencia>> findByIncidenciaByClienteId(@PathVariable long id){
        Set<Incidencia> incidencias = incidenciaService.findByIncidenciaByClienteId(id);
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de incidencias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencia/estado/{estado}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Incidencia>> findByEstado(@PathVariable int estado) {
        EstadoIncidencia estadoIncidencia = null;

        if (estado == 0) {
            estadoIncidencia = EstadoIncidencia.ENVIADO;
        }
        else if (estado == 1) {
            estadoIncidencia = EstadoIncidencia.ACEPTADO;
        }else if (estado == 2) {
            estadoIncidencia = EstadoIncidencia.ENPROCESO;
        } else if (estado == 3) {
            estadoIncidencia = EstadoIncidencia.FINALIZADO;
        } else {
            throw new EstadoIncidenciaNotFoundException("Estado de incidencia no existente");
        }

        Set<Incidencia> incidencias = incidenciaService.findByEstado(estadoIncidencia);
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene el listado de incidencias por duracion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de incidencias",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Incidencia.class)))),
    })
    @GetMapping("/incidencia/duracion/{duracion}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Incidencia>> getIncideciaByDuracion(@PathVariable String duracion){
        Set<Incidencia> incidencias = incidenciaService.findByDuracion(duracion);
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo incidencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el incidencia", content = @Content(schema = @Schema(implementation =
                    Incidencia.class)))
    })
    @PostMapping("/incidencia")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Incidencia> addIncidencia(@RequestBody Incidencia incidencia){
        Incidencia nuevaIncidencia = incidenciaService.addIncidencia(incidencia);
        return new ResponseEntity<>(nuevaIncidencia, HttpStatus.OK);
    }


    @Operation(summary = "Modifica una incidencia en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la incidencia", content = @Content(schema = @Schema(implementation =
                    Incidencia.class))),
    @ApiResponse(responseCode = "404", description = "la incidencia no existe", content = @Content(schema = @Schema(implementation =
            Response.class)))
})
@PutMapping("/incidencia/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Incidencia> modifyIncidencia(@PathVariable long id,
                                             @RequestBody Incidencia nuevaIncidencia)
    {
        Incidencia incidencia = incidenciaService.modifyIncidencia(id, nuevaIncidencia);
        return new ResponseEntity<>(incidencia, HttpStatus.OK);
    }


    @Operation(summary = "Elimina una incidencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la incidencia", content = @Content(schema = @Schema(implementation =
                    Response.class))),
    @ApiResponse(responseCode = "404", description = "La incidencia no existe", content = @Content(schema = @Schema(implementation =
            Response.class)))
})
    @DeleteMapping("/incidencia/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
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