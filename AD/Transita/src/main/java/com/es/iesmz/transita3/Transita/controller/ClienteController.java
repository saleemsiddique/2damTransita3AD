package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente")
    public ResponseEntity<Set<Cliente>> getClientes() {
        Set<Cliente> clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/cliente/dni/{dni}")
    public ResponseEntity<Cliente> getClienteByDni(@PathVariable String dni) {
        Cliente cliente = clienteService.findByDni(dni);
        if (cliente == null) {
            throw new ClienteNotFoundException(dni);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/cliente/nombre/{nombre}")
    public ResponseEntity<Set<Cliente>> searchClientesByNombreStartingWith(@PathVariable("nombre") String nombre) {
        Set<Cliente> clientes = clienteService.findByNombreStartingWith(nombre);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/apellido/{apellido}")
    public ResponseEntity<Set<Cliente>> searchClientesByApellidoStartingWith(@PathVariable("apellido") String apellido) {
        Set<Cliente> clientes = clienteService.findByApellidoStartingWith(apellido);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/nombreUsuario/{nombreUsuario}")
    public ResponseEntity<Set<Cliente>> searchClientesByNombreUsuarioStartingWith(@PathVariable("nombreUsuario") String nombreUsuario) {
        Set<Cliente> clientes = clienteService.findByNombreUsuarioStartingWith(nombreUsuario);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/telefono/{numeroTelefono}")
    public ResponseEntity<Cliente> getClienteByNumeroTelefono(@PathVariable String numeroTelefono) {
        Cliente cliente = clienteService.findByNumeroTelefono(numeroTelefono);
        if (cliente == null) {
            throw new ClienteNotFoundException(numeroTelefono);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/cliente/email/{email}")
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String email) {
        Cliente cliente = clienteService.findByEmail(email);
        if (cliente == null) {
            throw new ClienteNotFoundException(email);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/cliente")
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente addedCliente = clienteService.addCliente(cliente);
        return new ResponseEntity<>(addedCliente, HttpStatus.OK);
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<Cliente> modifyCliente(@PathVariable long id, @RequestBody Cliente newCliente) {
        Cliente cliente = clienteService.modifyCliente(id, newCliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("/cliente/{id}")
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
