package com.poli.reglas.exception;

/** Formato único de error: un código estable y un mensaje legible. */
public record ErrorResponse(String error, String message) {}
