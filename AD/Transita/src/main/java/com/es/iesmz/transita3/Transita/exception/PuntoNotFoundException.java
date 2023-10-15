package com.es.iesmz.transita3.Transita.exception;

import com.es.iesmz.transita3.Transita.domain.TipoPunto;

public class PuntoNotFoundException extends RuntimeException{
    public PuntoNotFoundException(){super();}
    public PuntoNotFoundException(String mensaje){super(mensaje);}
    public PuntoNotFoundException(long id){super("Punto no encontrado: " + id);}
}
