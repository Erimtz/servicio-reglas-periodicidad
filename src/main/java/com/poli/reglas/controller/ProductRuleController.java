package com.poli.reglas.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poli.reglas.dto.EvaluateRequest;
import com.poli.reglas.dto.EvaluateResponse;
import com.poli.reglas.service.ProductRuleService;

import lombok.RequiredArgsConstructor;

/**
 * Servicio de Reglas de Periodicidad (REST).
 *   POST /v1/frequency/evaluate -> evalúa la regla (requiere token interno)
 *   GET  /health                -> chequeo de salud
 */
@RestController
@RequiredArgsConstructor
public class ProductRuleController {

    private final ProductRuleService productRuleService;

    @PostMapping("/v1/frequency/evaluate")
    public EvaluateResponse evaluate(@RequestBody(required = false) EvaluateRequest request) {
        return productRuleService.evaluate(request == null ? new EvaluateRequest(null, null, null) : request);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
