package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.Admin;
import com.es.iesmz.transita3.Transita.domain.ERole;
import com.es.iesmz.transita3.Transita.domain.Rol;
import com.es.iesmz.transita3.Transita.payload.request.AdminSignupRequest;
import com.es.iesmz.transita3.Transita.payload.request.ClienteLoginRequest;
import com.es.iesmz.transita3.Transita.payload.response.JwtResponse;
import com.es.iesmz.transita3.Transita.payload.response.MessageResponse;
import com.es.iesmz.transita3.Transita.repository.AdminRepository;
import com.es.iesmz.transita3.Transita.repository.RoleRepository;
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
public class AdminAuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/signin/admin")
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
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerUser(@Valid @RequestBody AdminSignupRequest signUpRequestAdmin) {
        if (adminRepository.existsByNombreUsuario(signUpRequestAdmin.getNombreUsuario())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Nombre de usuario ya existente"));
        }

        if (adminRepository.existsByEmail(signUpRequestAdmin.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email está en uso"));
        }

        // Create new user's account
        Admin admin = new Admin(signUpRequestAdmin.getNombreUsuario(),
                signUpRequestAdmin.getEmail(),
                encoder.encode(signUpRequestAdmin.getContrasenya()),
                signUpRequestAdmin.getTelefono());

        Set<String> strRoles = signUpRequestAdmin.getRol();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol userRol = roleRepository.findByNombre(ERole.ROL_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRol);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Rol adminRol = roleRepository.findByNombre(ERole.ROL_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(adminRol);
                        break;
                    default:
                        Rol userRol = roleRepository.findByNombre(ERole.ROL_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(userRol);
                }
            });
        }

        admin.setRoles(roles);
        adminRepository.save(admin);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
