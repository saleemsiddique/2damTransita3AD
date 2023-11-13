package com.es.iesmz.transita3.Transita.payload.request;
import javax.validation.constraints.NotBlank;
public class ClienteModifyEstadoRequest {

        @NotBlank
        private String nuevoEstadoCuenta;

        public String getNuevoEstadoCuenta() {
            return nuevoEstadoCuenta;
        }

        public void setNuevoEstadoCuenta(String nuevoEstadoCuenta) {
            this.nuevoEstadoCuenta = nuevoEstadoCuenta;
        }
    }

