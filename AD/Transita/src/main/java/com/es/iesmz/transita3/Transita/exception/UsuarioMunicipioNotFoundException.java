package com.es.iesmz.transita3.Transita.exception;

public class UsuarioMunicipioNotFoundException extends RuntimeException{
    public UsuarioMunicipioNotFoundException() {
        super();
    }

    public UsuarioMunicipioNotFoundException(String message) {
        super(message);
    }

    public UsuarioMunicipioNotFoundException(long id) {
        super("Usuario municipio not found: " + id);
    }
}
