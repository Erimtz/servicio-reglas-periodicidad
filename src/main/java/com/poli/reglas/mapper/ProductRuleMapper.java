package com.poli.reglas.mapper;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.poli.reglas.dto.EvaluateResponse;
import com.poli.reglas.entity.ProductRule;

/** Convierte la entidad ProductRule (más los cortes calculados) en el DTO de respuesta. La entidad no sale del servicio. */
@Component
public class ProductRuleMapper {

    public EvaluateResponse toResponse(ProductRule rule, LocalDate lastCut, LocalDate nextCut) {
        return new EvaluateResponse(
                rule.getClientId(),
                rule.getProductType(),
                rule.getFrequency(),
                rule.isActive(),
                lastCut.toString(),
                nextCut.toString());
    }
}
