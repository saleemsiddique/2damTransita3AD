package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.*;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.exception.TipoNotFoundException;
import com.es.iesmz.transita3.Transita.repository.RolRepository;
import com.es.iesmz.transita3.Transita.service.ClienteService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    RolRepository roleRepository;

    @Operation(summary = "Obtener una lista de todos los clientes")
    @GetMapping("/cliente")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> getClientes(@RequestParam(value = "nombre", defaultValue = "") String nombre) {
        Set<Cliente> clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Obtener un cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente obtenido exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/cliente/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> getCliente(@PathVariable long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Buscar clientes por nombre que comienza con")
    @GetMapping("/cliente/nombre/{nombre}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> searchClientesByNombreStartingWith(@PathVariable("nombre") String nombre) {
        Set<Cliente> clientes = clienteService.findByNombreStartingWith(nombre);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @Operation(summary = "Buscar clientes por rol (ROLE_ADMIN o ROLE_MODERADOR)")
    @GetMapping("/cliente/RolMunicipio")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> findByRoleAdminOrModerator(@RequestParam(value = "nombre", defaultValue = "") String roles_usuario) {
        Set<Cliente> clientes = clienteService.findByRoleAdminOrModerator();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @Operation(summary = "Buscar clientes por rol (ROLE_USUARIO)")
    @GetMapping("/cliente/RolUsuario")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> findByRoleUsuario(@RequestParam(value = "nombre", defaultValue = "") String roles_usuario) {
        Set<Cliente> clientes = clienteService.findByRoleUsuario();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @Operation(summary = "Buscar clientes por rol (ROLE_USUARIO) y estado desactivado")
    @GetMapping("/cliente/RolUsuario/estado/0")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> findByRoleUsuarioAndEstadoDesactivado(@RequestParam(value = "nombre", defaultValue = "") String roles_usuario) {
        Set<Cliente> clientes = clienteService.findByRoleUsuarioAndEstadoDesactivado();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @Operation(summary = "Buscar clientes por rol (ROLE_USUARIO) y estado activado")
    @GetMapping("/cliente/RolUsuario/estado/1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> findByRoleUsuarioAndEstadoActivado(@RequestParam(value = "nombre", defaultValue = "") String roles_usuario) {
        Set<Cliente> clientes = clienteService.findByRoleUsuarioAndEstadoActivado();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @Operation(summary = "Obtiene el listado de clientes por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de clientes por estado",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))),
    })
    @GetMapping("/cliente/estado/{estado}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> findByEstado(@PathVariable int estado) {
        ECliente estadoCliente = null;

        if (estado == 0) {
            estadoCliente = ECliente.ACTIVADO;
        } else if (estado == 1) {
            estadoCliente = ECliente.DESACTIVADO;
        } else {
            throw new TipoNotFoundException();
        }

        Set<Cliente> clientes = clienteService.findByEstadoCuenta(estadoCliente);

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Buscar clientes por apellido que comienza con")
    @GetMapping("/cliente/apellidos/{apellidos}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> searchClientesByApellidoStartingWith(@PathVariable("apellidos") String apellidos) {
        Set<Cliente> clientes = clienteService.findByApellidoStartingWith(apellidos);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Obtener cliente por correo electrónico")
    @GetMapping("/cliente/nombreusuario/{nombreusuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> getClienteByEmail(@PathVariable String nombreusuario) {
        Set<Cliente> clientes = clienteService.findByNombreUsuarioStartingWith(nombreusuario);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar un cliente por ID")
    @DeleteMapping("/cliente/eliminar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Response> deleteCliente(@PathVariable long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(ClienteNotFoundException cnfe) {
        Response response = Response.errorResonse(NOT_FOUND, cnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}