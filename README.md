# Servicio de Reglas de Periodicidad (Spring Boot + JPA + H2)

Dependencias de Spring Initializr: Spring Web, Spring Data JPA, H2 Database, Lombok.

Estructura (paquete base com.poli.reglas):
  config/      TokenInterceptor, WebConfig
  controller/  ProductRuleController
  dto/         EvaluateRequest, EvaluateResponse
  entity/      ProductRule
  mapper/      ProductRuleMapper
  exception/   ApiException, ErrorResponse, RestExceptionHandler
  repository/  ProductRuleRepository
  service/     ProductRuleService  (interfaz)
  service/impl ProductRuleServiceImpl
  ReglasApplication (en la raíz del paquete base)
  src/test/java/com/poli/reglas/demo/OrquestadorDemo (cliente que simula al orquestador)

1. Abrir en IntelliJ como proyecto Maven (pom.xml) con JDK 21 y activar Annotation Processing (Lombok).
2. Ejecutar ReglasApplication. Escucha en http://localhost:8081 (tabla product_rule creada y llenada con data.sql).
3. Postman: importar los dos archivos de postman/ y ejecutar la colección con Runner (16 solicitudes).
4. Consola de la BD: http://localhost:8081/h2-console  (jdbc:h2:mem:reglasdb, usuario sa, sin contraseña).
