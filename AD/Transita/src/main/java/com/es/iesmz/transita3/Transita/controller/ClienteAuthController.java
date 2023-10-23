package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Cliente;
import com.es.iesmz.transita3.Transita.domain.ECliente;
import com.es.iesmz.transita3.Transita.domain.ERole;
import com.es.iesmz.transita3.Transita.domain.Rol;
import com.es.iesmz.transita3.Transita.payload.request.ClienteLoginRequest;
import com.es.iesmz.transita3.Transita.payload.request.ClienteSignupRequest;
import com.es.iesmz.transita3.Transita.payload.response.JwtResponse;
import com.es.iesmz.transita3.Transita.payload.response.MessageResponse;
import com.es.iesmz.transita3.Transita.repository.ClienteRepository;
import com.es.iesmz.transita3.Transita.repository.RolRepository;
import com.es.iesmz.transita3.Transita.security.jwt.JwtUtils;
import com.es.iesmz.transita3.Transita.security.services.UsuarioDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import java.util.Set;
import java.util.stream.Collectors;


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

    @GetMapping("/signin/cliente")
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
                    .body(new MessageResponse("Error: Email está en uso"));
        }

        // Create new user's account
        Cliente cliente = new Cliente(signUpRequestCliente.getNombre(), signUpRequestCliente.getApellidos(), signUpRequestCliente.getNombreUsuario(),
                encoder.encode(signUpRequestCliente.getContrasenya()));

        // Set the estadoCuenta to ACTIVADO (or your desired default state)
        cliente.setEstadoCuenta(ECliente.ACTIVADO);

        Set<String> strRoles = signUpRequestCliente.getRol();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol userRol = roleRepository.findByNombre(ERole.ROL_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRol);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROL_ADMIN":
                        Rol adminRol = roleRepository.findByNombre(ERole.ROL_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(adminRol);
                        break;
                    case "ROL_USUARIO":
                        Rol userRol = roleRepository.findByNombre(ERole.ROL_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(userRol);
                }
            });
        }

        cliente.setRols(roles);
        clientRepository.save(cliente);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}