package com.poli.reglas.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poli.reglas.exception.ApiException;

/**
 * Valida el token interno antes de ejecutar el controlador. En producción este control lo haría Apigee
 * (OAuth2/JWT + RBAC); aquí se simula con un token fijo configurable.
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final String token;

    public TokenInterceptor(@Value("${reglas.internal-token:internal-demo-token}") String token) {
        this.token = token;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.equals("Bearer " + token)) {
            throw new ApiException(401, "UNAUTHORIZED", "Token interno ausente o inválido");
        }
        return true;
    }
}
