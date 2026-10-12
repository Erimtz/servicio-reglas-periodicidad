package com.poli.reglas.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.poli.reglas.dto.EvaluateRequest;
import com.poli.reglas.dto.EvaluateResponse;
import com.poli.reglas.entity.ProductRule;
import com.poli.reglas.exception.ApiException;
import com.poli.reglas.mapper.ProductRuleMapper;
import com.poli.reglas.repository.ProductRuleRepository;
import com.poli.reglas.service.ProductRuleService;

import lombok.RequiredArgsConstructor;

/**
 * Regla de negocio centralizada (patrón Rules Centralization): define la frecuencia de corte
 * del estado de cuenta y su disponibilidad por cliente y producto.
 * Las reglas se leen de la base de datos (tabla product_rule); el cálculo de los cortes vive aquí.
 */
@Service
@RequiredArgsConstructor
public class ProductRuleServiceImpl implements ProductRuleService {

    private static final Set<String> PRODUCT_TYPES =
            Set.of("DEBIT_CARD", "PERSONAL_CREDIT", "PAYROLL_CREDIT", "INVESTMENTS", "MORTGAGE_CREDIT");

    /** Fecha ancla para la frecuencia catorcenal (cada 14 días). */
    private static final LocalDate FORTNIGHT_ANCHOR = LocalDate.of(2026, 1, 2);

    private final ProductRuleRepository repository;
    private final ProductRuleMapper mapper;

    @Override
    public EvaluateResponse evaluate(EvaluateRequest request) {
        String clientId = request.clientId();
        String productType = request.productType();
        String refRaw = request.referenceDate();

        if (clientId == null || productType == null) {
            throw new ApiException(400, "BAD_REQUEST", "Los campos clientId y productType son obligatorios");
        }
        if (!PRODUCT_TYPES.contains(productType)) {
            throw new ApiException(422, "UNKNOWN_PRODUCT_TYPE", "productType no reconocido: " + productType);
        }

        LocalDate ref;
        try {
            ref = refRaw == null ? LocalDate.now() : LocalDate.parse(refRaw);
        } catch (DateTimeParseException e) {
            throw new ApiException(422, "INVALID_DATE", "referenceDate debe tener formato yyyy-MM-dd");
        }
        if (ref.isBefore(FORTNIGHT_ANCHOR)) {
            throw new ApiException(422, "INVALID_DATE",
                    "referenceDate no puede ser anterior a " + FORTNIGHT_ANCHOR);
        }

        if (!repository.existsByClientId(clientId)) {
            throw new ApiException(404, "CLIENT_NOT_FOUND", "Cliente no encontrado: " + clientId);
        }
        ProductRule rule = repository.findByClientIdAndProductType(clientId, productType)
                .orElseThrow(() -> new ApiException(404, "PRODUCT_NOT_ASSOCIATED",
                        "El cliente no tiene el producto " + productType));

        LocalDate[] cuts = cuts(rule.getFrequency(), ref);   // [último corte, próximo corte]
        return mapper.toResponse(rule, cuts[0], cuts[1]);
    }

    // ------------------------------------------------------------- regla de negocio

    /** Devuelve {último corte <= ref, próximo corte > ref} según la frecuencia. */
    private static LocalDate[] cuts(String frequency, LocalDate ref) {
        LocalDate endOfMonth = ref.withDayOfMonth(ref.lengthOfMonth());
        switch (frequency) {
            case "MONTHLY": {
                LocalDate last = ref.equals(endOfMonth) ? ref : endOfPrevMonth(ref);
                LocalDate next = ref.isBefore(endOfMonth)
                        ? endOfMonth
                        : endOfMonth.plusMonths(1).withDayOfMonth(endOfMonth.plusMonths(1).lengthOfMonth());
                return new LocalDate[]{last, next};
            }
            case "BIWEEKLY": {            // quincenal: días 15 y último del mes
                if (ref.equals(endOfMonth)) return new LocalDate[]{ref, ref.plusMonths(1).withDayOfMonth(15)};
                if (ref.getDayOfMonth() >= 15) return new LocalDate[]{ref.withDayOfMonth(15), endOfMonth};
                return new LocalDate[]{endOfPrevMonth(ref), ref.withDayOfMonth(15)};
            }
            case "FORTNIGHTLY": {         // catorcenal: cada 14 días desde una fecha ancla
                long days = ChronoUnit.DAYS.between(FORTNIGHT_ANCHOR, ref);
                LocalDate last = FORTNIGHT_ANCHOR.plusDays(Math.floorDiv(days, 14) * 14);
                return new LocalDate[]{last, last.plusDays(14)};
            }
            default:
                throw new IllegalStateException("Frecuencia desconocida: " + frequency);
        }
    }

    private static LocalDate endOfPrevMonth(LocalDate d) {
        return d.withDayOfMonth(1).minusDays(1);
    }
}
