package com.poli.reglas.dto;

/** Respuesta exitosa de la evaluación. Las fechas se devuelven como texto yyyy-MM-dd. */
public record EvaluateResponse(
        String clientId,
        String productType,
        String frequency,
        boolean statementAvailable,
        String lastCutDate,
        String nextCutDate) {}
