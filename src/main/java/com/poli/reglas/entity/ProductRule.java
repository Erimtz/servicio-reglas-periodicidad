package com.poli.reglas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Regla de periodicidad de un producto de un cliente: frecuencia de corte y si el estado de cuenta
 * está disponible. Una fila de la tabla product_rule.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)   // requerido por JPA
@Entity
@Table(name = "product_rule")
public class ProductRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "product_type", nullable = false)
    private String productType;

    /** MONTHLY, BIWEEKLY o FORTNIGHTLY. */
    @Column(name = "frequency", nullable = false)
    private String frequency;

    @Column(name = "active", nullable = false)
    private boolean active;

    public ProductRule(String clientId, String productType, String frequency, boolean active) {
        this.clientId = clientId;
        this.productType = productType;
        this.frequency = frequency;
        this.active = active;
    }
}
