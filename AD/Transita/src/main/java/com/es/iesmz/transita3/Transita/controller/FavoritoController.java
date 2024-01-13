package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Favorito;
import com.es.iesmz.transita3.Transita.domain.Favorito;
import com.es.iesmz.transita3.Transita.exception.FavoritoNotFoundException;
import com.es.iesmz.transita3.Transita.service.FavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
@RestController
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Operation(summary = "Obtener una lista de todos los favoritos de un cliente")
    @GetMapping("/favorito/{idCliente}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Set<Favorito>> getFavoritoByidCliente(@PathVariable long id) {
        Set<Favorito> favoritos = favoritoService.findByidCliente(id);
        return new ResponseEntity<>(favoritos, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se registra el favorito", content = @Content(schema = @Schema(implementation =
                    Favorito.class)))
    })
    @PostMapping("/favorito")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USUARIO') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Favorito> addFavorito(@RequestBody Favorito favorito) {
        PuntoController puntoController = new PuntoController();
        Favorito nuevaFavorito = favoritoService.createFavorito(favorito);
        return new ResponseEntity<>(nuevaFavorito, HttpStatus.OK);
    }


    @Operation(summary = "Modifica un favorito en el catálogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se modifica la favorito", content = @Content(schema = @Schema(implementation =
                    Favorito.class))),
            @ApiResponse(responseCode = "404", description = "la favorito no existe", content = @Content(schema = @Schema(implementation =
                    Response.class)))
    })
    @PutMapping("/favorito/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Favorito> modifyFavorito(@PathVariable long id, @RequestBody Favorito favorito) {
        Optional<Favorito> optionalFavorito = favoritoService.findById(id);
        Favorito oldfavorito = favoritoService.updateFavorito(id, favorito);
        return new ResponseEntity<>(favorito, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la favorito", content = @Content(schema = @Schema(implementation =
                    Response.class))),
            @ApiResponse(responseCode = "404", description = "La favorito no existe", content = @Content(schema = @Schema(implementation =
                    Response.class)))
    })
    @DeleteMapping("/favorito/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN')")
    public ResponseEntity<Favorito> deleteFavorito(@PathVariable long id) {
        Favorito favorito = favoritoService.findById(id)
                .orElseThrow(() -> new FavoritoNotFoundException(id));
        favoritoService.deleteFavorito(id);
        return new ResponseEntity(Response.noErrorResponse(), HttpStatus.OK);
    }

}
