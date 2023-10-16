package com.es.iesmz.transita3.Transita.exception;

public class IncidenciaNotFoundException  extends RuntimeException{
    public IncidenciaNotFoundException(){super();}
    public IncidenciaNotFoundException(String mensaje){super(mensaje);}
    public IncidenciaNotFoundException(long id){super("Incidencia no encontrada: " + id);}
}
