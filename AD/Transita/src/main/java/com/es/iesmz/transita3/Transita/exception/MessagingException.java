package com.es.iesmz.transita3.Transita.exception;

public class MessagingException extends RuntimeException {
    public MessagingException() {
        super();
    }

    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(long id) {
        super("Error sending password");
    }
}
