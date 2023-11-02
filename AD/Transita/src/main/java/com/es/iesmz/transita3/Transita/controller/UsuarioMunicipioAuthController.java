package com.es.iesmz.transita3.Transita.controller;

import com.es.iesmz.transita3.Transita.domain.UsuarioMunicipio;
import com.es.iesmz.transita3.Transita.domain.ERole;
import com.es.iesmz.transita3.Transita.domain.Rol;
import com.es.iesmz.transita3.Transita.payload.request.UsuarioMunicipioLoginRequest;
import com.es.iesmz.transita3.Transita.payload.request.UsuarioMunicipioSignupRequest;
import com.es.iesmz.transita3.Transita.payload.response.JwtResponse;
import com.es.iesmz.transita3.Transita.payload.response.MessageResponse;
import com.es.iesmz.transita3.Transita.repository.UsuarioMunicipioRepository;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UsuarioMunicipioAuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioMunicipioRepository usuarioMunicipioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/signin/usuarioMunicipio")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UsuarioMunicipioLoginRequest usuarioMunicipioLoginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioMunicipioLoginRequest.getNombreUsuario(), usuarioMunicipioLoginRequest.getContrasenya()));

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

    @PostMapping("/signup/usuarioMunicipio")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioMunicipioSignupRequest signUpRequestUsuarioMunicipio) {
        if (usuarioMunicipioRepository.existsByNombreUsuario(signUpRequestUsuarioMunicipio.getNombreUsuario())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email está en uso"));
        }

        // Create new user's account
        UsuarioMunicipio usuarioMunicipio = new UsuarioMunicipio(signUpRequestUsuarioMunicipio.getNombre(), signUpRequestUsuarioMunicipio.getApellidos(), signUpRequestUsuarioMunicipio.getNombreUsuario(),
                encoder.encode(signUpRequestUsuarioMunicipio.getContrasenya()), signUpRequestUsuarioMunicipio.getTelefono());



        Set<String> strRoles = signUpRequestUsuarioMunicipio.getRol();
        Set<Rol> roles = new HashSet<>();

        if (strRoles == null) {
            Rol userRol = rolRepository.findByNombre(ERole.ROLE_USUARIO)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(userRol);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Rol adminRol = rolRepository.findByNombre(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(adminRol);
                        break;
                    case "ROLE_USUARIO":
                        Rol userRol = rolRepository.findByNombre(ERole.ROLE_USUARIO)
                                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                        roles.add(userRol);
                }
            });
        }

        usuarioMunicipio.setRols(roles);
        usuarioMunicipioRepository.save(usuarioMunicipio);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
