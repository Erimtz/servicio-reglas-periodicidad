package com.poli.reglas.service;

import com.poli.reglas.dto.EvaluateRequest;
import com.poli.reglas.dto.EvaluateResponse;

public interface ProductRuleService {

    /** Evalúa la regla de periodicidad de un producto de un cliente y calcula los cortes del estado de cuenta. */
    EvaluateResponse evaluate(EvaluateRequest request);
}
