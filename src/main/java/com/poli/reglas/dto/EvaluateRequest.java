package com.poli.reglas.dto;

/** Cuerpo de la solicitud de POST /v1/frequency/evaluate. referenceDate es opcional (yyyy-MM-dd). */
public record EvaluateRequest(String clientId, String productType, String referenceDate) {}
