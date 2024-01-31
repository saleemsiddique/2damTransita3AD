package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.ECliente;
import com.es.iesmz.transita3.Transita.domain.ERole;
import com.es.iesmz.transita3.Transita.domain.Rol;
import com.es.iesmz.transita3.Transita.payload.request.*;
import com.es.iesmz.transita3.Transita.payload.response.JwtResponse;
import com.es.iesmz.transita3.Transita.payload.response.MessageResponse;
import com.es.iesmz.transita3.Transita.repository.ClienteRepository;
import com.es.iesmz.transita3.Transita.repository.RolRepository;
import com.es.iesmz.transita3.Transita.security.jwt.JwtUtils;
import com.es.iesmz.transita3.Transita.security.services.UsuarioDetailsImpl;
import com.es.iesmz.transita3.Transita.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//https://github.com/bezkoder/spring-boot-spring-security-jwt-authentication
//http://localhost:8080/api/auth/signup?username=Pepe&email=pepe@gmail.com&password=1234&role=admin
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class ClienteAuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ClienteRepository clientRepository;

    @Autowired
    RolRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ClienteService clienteService;

    @PostMapping("/signin/cliente")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody ClienteLoginRequest clienteLoginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(clienteLoginRequest.getNombreUsuario(), clienteLoginRequest.getContrasenya()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getNombre(),
                userDetails.getApellidos(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getEstado(),
                roles));
    }

    @PostMapping("/signup/cliente")
    public ResponseEntity<?> registerUser(@Valid @RequestBody ClienteSignupRequest signUpRequestCliente) {
        if (clientRepository.existsByNombreUsuario(signUpRequestCliente.getNombreUsuario())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Nombre de usuario ya está en uso"));
        }

        // Create new user's account
        Cliente cliente = new Cliente(signUpRequestCliente.getNombre(), signUpRequestCliente.getApellidos(), signUpRequestCliente.getNombreUsuario(),
                encoder.encode(signUpRequestCliente.getContrasenya()));

        // Set the estadoCuenta to ACTIVADO (or your desired default state)
        cliente.setEstadoCuenta(ECliente.ACTIVADO);

        Set<String> strRoles = signUpRequestCliente.getRol();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol userRol = roleRepository.findByNombre(ERole.ROLE_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRol);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Rol adminRol = roleRepository.findByNombre(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(adminRol);
                        break;
                    case "ROLE_MODERADOR":
                        Rol moderatorRol = roleRepository.findByNombre(ERole.ROLE_MODERADOR)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(moderatorRol);
                        break;
                    case "ROLE_USUARIO":
                        Rol userRol = roleRepository.findByNombre(ERole.ROLE_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(userRol);
                }
            });
        }

        cliente.setRols(roles);
        clientRepository.save(cliente);

        // Convertir el objeto Cliente a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String clienteJson;
        try {
            clienteJson = objectMapper.writeValueAsString(cliente);
        } catch (JsonProcessingException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error al convertir el objeto Cliente a JSON"));
        }

        // Devolver el JSON del cliente en el cuerpo de la respuesta
        return ResponseEntity.ok(clienteJson);
    }



    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    @PutMapping("/cliente/modificar/{id}")
    public ResponseEntity<?> modifyCliente(@PathVariable Long id, @Valid @RequestBody ClienteModificarDatosRequest modifyDatosCliente) {
        // Verifica si el cliente existe en la base de datos
        Optional<Cliente> optionalCliente = clientRepository.findById(id);

        if (!optionalCliente.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error: Cliente no encontrado"));
        }

        // Obtiene el cliente existente
        Cliente cliente = optionalCliente.get();

        // Actualiza los campos del cliente con los valores proporcionados en la solicitud
        cliente.setNombre(modifyDatosCliente.getNombre());
        cliente.setApellidos(modifyDatosCliente.getApellidos());
        cliente.setNombreUsuario(modifyDatosCliente.getNombreUsuario());


        // Actualiza los roles del cliente
        Set<String> strRoles = modifyDatosCliente.getRol();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol userRol = roleRepository.findByNombre(ERole.ROLE_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRol);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Rol adminRol = roleRepository.findByNombre(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(adminRol);
                        break;
                    case "ROLE_MODERADOR":
                        Rol moderatorRol = roleRepository.findByNombre(ERole.ROLE_MODERADOR)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(moderatorRol);
                        break;
                    case "ROLE_USUARIO":
                        Rol userRol = roleRepository.findByNombre(ERole.ROLE_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(userRol);
                }
            });
        }

        cliente.setRols(roles);
        clientRepository.save(cliente);

        return ResponseEntity.ok(new MessageResponse("Cliente modificado exitosamente"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/cliente/modificarEstado/{id}")
    public ResponseEntity<?> modifyClienteEstado(@PathVariable Long id, @Valid @RequestBody ClienteModifyEstadoRequest clienteModifyEstadoRequest) {
        // Verifica si el cliente existe en la base de datos
        Optional<Cliente> optionalCliente = clientRepository.findById(id);

        if (!optionalCliente.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error: Cliente no encontrado"));
        }

        // Obtiene el cliente existente
        Cliente cliente = optionalCliente.get();

        // Actualiza los campos del cliente con los valores proporcionados en la solicitud
        String nuevoEstadoCuenta = clienteModifyEstadoRequest.getNuevoEstadoCuenta();

        if (nuevoEstadoCuenta != null) {
            try {
                // Convierte el String a ECliente
                ECliente estadoCliente = ECliente.valueOf(nuevoEstadoCuenta);

                // Actualiza el estado de cuenta del cliente con el nuevo valor
                cliente.setEstadoCuenta(estadoCliente);
                clientRepository.save(cliente);

                return ResponseEntity.ok(new MessageResponse("Cliente modificado exitosamente"));
            } catch (IllegalArgumentException e) {
                // Manejar la excepción si el valor no coincide con ningún estado de ECliente
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Estado de cuenta no válido"));
            }
        } else {
            // No se proporcionó un nuevo estado de cuenta
            return ResponseEntity.ok(new MessageResponse("No se realizaron cambios en el estado de cuenta del cliente"));
        }
    }

    @PreAuthorize("hasRole('ROLE_USUARIO') || hasRole('ROLE_ADMIN') || hasRole('ROLE_MODERADOR')")
    @PutMapping("/cliente/modificarContrasenya/{id}")
    public ResponseEntity<?> modifyContrasenyaCliente(@PathVariable Long id, @Valid @RequestBody ClienteModificarContrasenyaRequest clienteModificarContrasenyaRequest){
        Optional<Cliente> optionalCliente = clientRepository.findById(id);

        if (!optionalCliente.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Error: Cliente no encontrado"));
        }

        Cliente cliente = optionalCliente.get();

        cliente.setContrasenya(encoder.encode(clienteModificarContrasenyaRequest.getContrasenya()));
        clientRepository.save(cliente);

        return ResponseEntity.ok(new MessageResponse("Cliente modificado exitosamente"));

    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email){
        String responseMessage = clienteService.setPassword(email);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}