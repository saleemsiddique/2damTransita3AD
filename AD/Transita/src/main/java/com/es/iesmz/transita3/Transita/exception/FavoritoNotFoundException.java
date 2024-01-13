package com.es.iesmz.transita3.Transita.exception;

public class FavoritoNotFoundException extends RuntimeException {

    public FavoritoNotFoundException() {
        super();
    }

    public FavoritoNotFoundException(String message) {
        super(message);
    }

    public FavoritoNotFoundException(long id) {
        super("Favorito not found: " + id);
    }
}
