package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.UsuarioMunicipio;
import com.es.iesmz.transita3.Transita.exception.UsuarioMunicipioNotFoundException;
import com.es.iesmz.transita3.Transita.service.UsuarioMunicipioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;

@RestController
public class UsuarioMunicipioController {

    @Autowired
    private UsuarioMunicipioService usuarioMunicipioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Obtener una lista de todos los usuarios de municipio")
    @GetMapping("/usuario_municipio")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<UsuarioMunicipio>> getUsuariosMunicipio(@RequestParam(value = "nombre", defaultValue = "") String nombre) {
        Set<UsuarioMunicipio> usuariosMunicipio = usuarioMunicipioService.findAll();
        return new ResponseEntity<>(usuariosMunicipio, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un usuario de municipio por teléfono")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario de municipio obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario de municipio no encontrado")
    })
    @GetMapping("/usuario_municipio/telefono/{telefono}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioMunicipio> getUsuarioMunicipio(@PathVariable String telefono) {
        UsuarioMunicipio usuarioMunicipio = usuarioMunicipioService.findByTelefono(telefono);
        return new ResponseEntity<>(usuarioMunicipio, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un usuario de municipio por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario de municipio obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario de municipio no encontrado")
    })
    @GetMapping("/usuario_municipio/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioMunicipio> getUsuarioMunicipio(@PathVariable long id) {
        UsuarioMunicipio usuarioMunicipio = usuarioMunicipioService.findById(id)
                .orElseThrow(() -> new UsuarioMunicipioNotFoundException(id));

        return new ResponseEntity<>(usuarioMunicipio, HttpStatus.OK);
    }

    @Operation(summary = "Buscar usuarios de municipio por nombre que comienza con")
    @GetMapping("/usuario_municipio/nombre/{nombre}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<UsuarioMunicipio>> searchUsuariosMunicipioByNombreStartingWith(@PathVariable("nombre") String nombre) {
        Set<UsuarioMunicipio> usuariosMunicipio = usuarioMunicipioService.findByNombreStartingWith(nombre);
        return new ResponseEntity<>(usuariosMunicipio, HttpStatus.OK);
    }

    @Operation(summary = "Buscar usuarios de municipio por apellido que comienza con")
    @GetMapping("/usuario_municipio/apellidos/{apellidos}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<UsuarioMunicipio>> searchUsuariosMunicipioByApellidoStartingWith(@PathVariable("apellidos") String apellidos) {
        Set<UsuarioMunicipio> usuariosMunicipio = usuarioMunicipioService.findByApellidoStartingWith(apellidos);
        return new ResponseEntity<>(usuariosMunicipio, HttpStatus.OK);
    }

    @Operation(summary = "Obtener usuario de municipio por correo electrónico")
    @GetMapping("/usuario_municipio/nombreusuario/{nombreusuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<UsuarioMunicipio>> getUsuarioMunicipioByNombreUsuario(@PathVariable String nombreusuario) {
        Set<UsuarioMunicipio> usuariosMunicipio = usuarioMunicipioService.findByNombreUsuarioStartingWith(nombreusuario);
        return new ResponseEntity<>(usuariosMunicipio, HttpStatus.OK);
    }

    @Operation(summary = "Modificar un usuario de municipio por ID")
    @PutMapping("/usuario_municipio/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioMunicipio> modifyUsuarioMunicipio(@PathVariable long id, @RequestBody UsuarioMunicipio newUsuarioMunicipio) {
        Optional<UsuarioMunicipio> optionalUsuarioMunicipio = usuarioMunicipioService.findById(id);

        if (optionalUsuarioMunicipio.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UsuarioMunicipio usuarioMunicipio = optionalUsuarioMunicipio.get();

        // Actualiza los campos del usuario de municipio con los valores proporcionados en la solicitud
        usuarioMunicipio.setNombre(newUsuarioMunicipio.getNombre());
        usuarioMunicipio.setApellidos(newUsuarioMunicipio.getApellidos());
        usuarioMunicipio.setNombreUsuario(newUsuarioMunicipio.getNombreUsuario());

        // Cifra la nueva contraseña antes de guardarla si se proporciona
        if (newUsuarioMunicipio.getContrasenya() != null) {
            String contraseñaCifrada = passwordEncoder.encode(newUsuarioMunicipio.getContrasenya());
            usuarioMunicipio.setContrasenya(contraseñaCifrada);
        }

        // Guarda el usuario de municipio modificado en la base de datos
        usuarioMunicipio = usuarioMunicipioService.modifyUsuarioMunicipio(usuarioMunicipio.getId(), usuarioMunicipio);

        return new ResponseEntity<>(usuarioMunicipio, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un usuario de municipio por ID")
    @DeleteMapping("/usuario_municipio/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response> deleteUsuarioMunicipio(@PathVariable long id) {
        usuarioMunicipioService.deleteUsuarioMunicipio(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(UsuarioMunicipioNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UsuarioMunicipioNotFoundException cnfe) {
        Response response = Response.errorResonse(NOT_FOUND, cnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
