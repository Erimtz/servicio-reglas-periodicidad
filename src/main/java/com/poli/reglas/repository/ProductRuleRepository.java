package com.poli.reglas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poli.reglas.entity.ProductRule;

/** Acceso a la tabla product_rule. Spring Data genera las consultas a partir de los nombres de los métodos. */
@Repository
public interface ProductRuleRepository extends JpaRepository<ProductRule, Long> {

    Optional<ProductRule> findByClientIdAndProductType(String clientId, String productType);

    boolean existsByClientId(String clientId);
}
