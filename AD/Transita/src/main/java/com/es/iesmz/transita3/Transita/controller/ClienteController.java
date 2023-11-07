package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.service.ClienteService;
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
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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


    /*
    Creo que no hace falta, porque el cliente se crea cuando haces el signup (preguntar)
    @PostMapping("/cliente")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente addedCliente = clienteService.addCliente(cliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.OK);
    }*/



    @Operation(summary = "Modificar un cliente por ID")
    @PutMapping("/cliente/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> modifyCliente(@PathVariable long id, @RequestBody Cliente newCliente) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);

        if (optionalCliente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Cliente cliente = optionalCliente.get();

        // Actualiza los campos del cliente con los valores proporcionados en la solicitud
        cliente.setNombre(newCliente.getNombre());
        cliente.setApellidos(newCliente.getApellidos());
        cliente.setNombreUsuario(newCliente.getNombreUsuario());

        // Cifra la nueva contraseña antes de guardarla si se proporciona
        if (newCliente.getContrasenya() != null) {
            String contraseñaCifrada = passwordEncoder.encode(newCliente.getContrasenya());
            cliente.setContrasenya(contraseñaCifrada);
        }

        // Guarda el cliente modificado en la base de datos
        cliente = clienteService.modifyCliente(cliente.getId(), cliente);

        return new ResponseEntity<>(cliente, HttpStatus.OK);
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