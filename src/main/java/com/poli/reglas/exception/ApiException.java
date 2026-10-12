package com.poli.reglas.exception;

import lombok.Getter;

/** Error controlado del servicio: lleva el código HTTP que debe devolverse. */
@Getter
public class ApiException extends RuntimeException {

    private final int status;
    private final String code;

    public ApiException(int status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
