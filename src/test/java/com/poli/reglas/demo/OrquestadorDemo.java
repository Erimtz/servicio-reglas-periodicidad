package com.poli.reglas.demo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Cliente de ejemplo: simula al Orquestador consumiendo el Servicio de Reglas.
 * Paso 1: consulta la regla. Paso 2: decide si invoca al microservicio de producto.
 * (La llamada a GetStatementsList se representa con un mensaje; no se implementa aquí.)
 */
public class OrquestadorDemo {
    public static void main(String[] args) throws Exception {
        HttpClient http = HttpClient.newHttpClient();
        String[][] casos = {
                {"C-1001", "PAYROLL_CREDIT"},
                {"C-1002", "PAYROLL_CREDIT"},
                {"C-1003", "MORTGAGE_CREDIT"},
                {"C-1005", "MORTGAGE_CREDIT"},
        };
        for (String[] c : casos) {
            String body = "{\"clientId\":\"" + c[0] + "\",\"productType\":\"" + c[1] + "\",\"referenceDate\":\"2026-10-15\"}";
            HttpRequest req = HttpRequest.newBuilder(URI.create("http://localhost:8081/v1/frequency/evaluate"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer internal-demo-token")
                    .POST(HttpRequest.BodyPublishers.ofString(body)).build();
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());
            System.out.println("[Orquestador] " + c[0] + " / " + c[1] + " -> HTTP " + res.statusCode());
            System.out.println("              regla: " + res.body());
            boolean disponible = res.statusCode() == 200 && res.body().contains("\"statementAvailable\":true");
            System.out.println(disponible
                    ? "              decisión: invocar GetStatementsList para este producto\n"
                    : "              decisión: NO invocar GetStatementsList (estado de cuenta no disponible)\n");
        }
    }
}
