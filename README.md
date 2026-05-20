# Bautista-post1-u11
Actividad Post-Contenido 1 / Unidad 11

# Pedido Service — Análisis SonarQube

## Estado inicial del análisis

| Métrica | Valor |
|---|---|
| Proyecto | refactoring-u11 |
| Version | 0.0.1-SNAPSHOT |
| Lineas de codigo | 195 |
| Code Smells | 5 |
| Coverage | 0.0% |
| Reliability Rating | C |
| Maintainability Rating | A |
| Security Rating | A |
| Duplications | 0.0% |

---

## Complejidad Ciclomática (CC)

El metodo `procesarPedido()` presenta una alta complejidad ciclomatica debido a:

- Validaciones de cliente
- Recorridos de listas
- Condicionales de descuentos
- Validaciones de productos
- Logica de notificacion
- Persistencia de datos

### CC estimada inicial:
- CC ≈ 10-12

---

## Technical Debt Ratio (TDR)

El proyecto presenta deuda tecnica inicial causada por:

- Long Method
- Large Class
- Primitive Obsession
- Mezcla de responsabilidades
- Falta de cobertura de pruebas

### TDR inicial:
- Maintainability Rating: A
- 5 Code Smells detectados

---

## Code Smells Identificados

### Bloater Smells

- Long Method:
  - `procesarPedido()`

- Large Class:
  - `PedidoService`

- Primitive Obsession:
  - Uso excesivo de tipos primitivos y Strings en parametros

---

## Resultado del Quality Gate

❌ FAILED

Razones:
- Coverage menor al 60%
- 5 Issues detectados

---

## Evidencia

### Analisis ejecutado correctamente

```bash
mvn verify sonar:sonar \
-Dsonar.host.url=http://localhost:9000 \
-Dsonar.token=TOKEN \
-Dsonar.projectKey=refactoring-u11
```

Resultado:
- BUILD SUCCESS
- ANALYSIS SUCCESSFUL

## Paso 2 — Introduccion de Value Objects

Se eliminaron code smells de tipo Primitive Obsession y Data Clump mediante la creacion de:

- Direccion
- DatosCliente

Beneficios obtenidos:

- Menor cantidad de parametros
- Mayor cohesion
- Validaciones encapsuladas
- Codigo mas mantenible
- Mejor modelado del dominio