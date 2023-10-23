package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.exception.ClienteNotFoundException;
import com.es.iesmz.transita3.Transita.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.es.iesmz.transita3.Transita.controller.Response.NOT_FOUND;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cliente")
    @PreAuthorize("hasAnyRole('ROL_USUARIO', 'ROL_ADMIN')")
    public ResponseEntity<Set<Cliente>> getClientes(@RequestParam(value = "nombre", defaultValue = "") String nombre) {
        Set<Cliente> clientes = clienteService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }


    @GetMapping("/cliente/{id}")
    @PreAuthorize("hasRole('ROL_USUARIO') or hasRole('ROL_ADMIN')")
    public ResponseEntity<Cliente> getCliente(@PathVariable long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/cliente/nombre/{nombre}")
    public ResponseEntity<Set<Cliente>> searchClientesByNombreStartingWith(@PathVariable("nombre") String nombre) {
        Set<Cliente> clientes = clienteService.findByNombreStartingWith(nombre);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/apellidos/{apellido}")
    public ResponseEntity<Set<Cliente>> searchClientesByApellidoStartingWith(@PathVariable("apellido") String apellido) {
        Set<Cliente> clientes = clienteService.findByApellidoStartingWith(apellido);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/cliente/nombreusuario/{nombreusuario}")
    public ResponseEntity<Cliente> getClienteByEmail(@PathVariable String nombreusuario) {
        Cliente cliente = clienteService.findByNombreUsuario(nombreusuario);
        if (cliente == null) {
            throw new ClienteNotFoundException(nombreusuario);
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
