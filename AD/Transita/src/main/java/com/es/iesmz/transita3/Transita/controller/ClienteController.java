package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.service.ClienteService;
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


    @GetMapping("/cliente")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> getClientes(@RequestParam(value = "nombre", defaultValue = "") String nombre) {
        Set<Cliente> clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }


    @GetMapping("/cliente/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> getCliente(@PathVariable long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/cliente/nombre/{nombre}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> searchClientesByNombreStartingWith(@PathVariable("nombre") String nombre) {
        Set<Cliente> clientes = clienteService.findByNombreStartingWith(nombre);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/apellidos/{apellido}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> searchClientesByApellidoStartingWith(@PathVariable("apellido") String apellido) {
        Set<Cliente> clientes = clienteService.findByApellidoStartingWith(apellido);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/nombreusuario/{nombreusuario}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String nombreusuario) {
        Cliente cliente = clienteService.findByNombreUsuario(nombreusuario);
        if (cliente == null) {
            throw new ClienteNotFoundException(nombreusuario);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    /*
    Creo que no hace falta, porque el cliente se crea cuando haces el signup (preguntar)
    @PostMapping("/cliente")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente addedCliente = clienteService.addCliente(cliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.OK);
    }*/



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
