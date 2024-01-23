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

    @Operation(summary = "Obtener una lista de todos los clientes por partes")
    @GetMapping("/cliente/filtrados")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> getClientesByPages(
            @RequestParam(name = "idInicial") int idInicial,
            @RequestParam(name = "idFinal") int idFinal,
            @RequestParam(name = "estado", required = false) Integer estado) {
        Set<Cliente> clientes =
        (estado == null) ?  clienteService.findAllByPages(idInicial, idFinal) :  clienteService.findAllByPagesFiltrado(idInicial, idFinal, estado);
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
    @Operation(summary = "Obtiene numero de cliente con filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de cliente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))
            )})
    @GetMapping("/cliente/count/filtros")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Integer> getClientesCountConFiltros(
            @RequestParam(name = "estado", required = false) Integer estado)
    {
        int cantidadPuntos =
                (estado == null) ? Math.toIntExact(clienteService.countCliente()) : Math.toIntExact(clienteService.getNumeroClientesFiltrados(estado));
        return new ResponseEntity<>(cantidadPuntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtiene numero de usuario municipio con filtros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de cliente",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Cliente.class)))
            )})
    @GetMapping("/cliente/count/admin/filtros")
    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    public ResponseEntity<Integer> getUsuarioMunicipioCountConFiltros(
            @RequestParam(name = "rol", required = false) Integer rol)
    {
        int cantidadPuntos =
                (rol == null) ? Math.toIntExact(clienteService.countUsuarioMunicipio()) : Math.toIntExact(clienteService.countUsuarioMunicipioFiltrado(rol));
        return new ResponseEntity<>(cantidadPuntos, HttpStatus.OK);
    }

    @Operation(summary = "Obtener una lista de todos los admin y moderadores por partes")
    @GetMapping("/cliente/admin/filtrados")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> getUsuariosMunicipioByPages(
            @RequestParam(name = "idInicial") int idInicial,
            @RequestParam(name = "idFinal") int idFinal,
            @RequestParam(name = "rol", required = false) Integer rol) {
        Set<Cliente> clientes =
                (rol == null) ?  clienteService.findUsuarioMunicipioWithRowNum(idInicial, idFinal) :  clienteService.findUsuarioMunicipioWithFilter(rol, idInicial, idFinal);
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

    @Operation(summary = "Buscar clientes por rol elegido")
    @GetMapping("/cliente/RolMunicipio/{rol}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> findByRole(@PathVariable int rol) {
        Set<Cliente> clientes;
        if(rol == 0){
            clientes = clienteService.findByRoleAdminOrModerator();
        }else {
            clientes = clienteService.findByRole(rol);
        }
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

    @Operation(summary = "Buscar clientes que contengan el valor pasado por parametro")
    @GetMapping("/cliente/buscar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Set<Cliente>> searchClientes(
            @RequestParam(name = "idInicial") int idInicial,
            @RequestParam(name = "idFinal") int idFinal,
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "apellidos", required = false) String apellidos,
            @RequestParam(name = "nombre_usuario", required = false) String nombreUsuario)
    {
        Set<Cliente> clientes = clienteService.searchUser(idInicial, idFinal, estado, nombre, apellidos, nombreUsuario);
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