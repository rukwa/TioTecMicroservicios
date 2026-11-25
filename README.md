# TíoTec Preventivo · Microservicios

Backend de la app [TíoTec Preventivo](https://github.com/rukwa/TioTecPreventivo) (DSY1105, Evaluación Parcial 4).
Dos microservicios Spring Boot independientes, cada uno con su propia base de datos MySQL.

| Microservicio | Responsable | Puerto | Base de datos | Qué maneja |
|---|---|---|---|---|
| `ms-contenidos` | Jorge Zárate | 8081 | `tiotec_contenidos` | Consejos educativos de la sección "Aprende" |
| `ms-solicitudes` | Matías Echeverría | 8082 | `tiotec_solicitudes` | Solicitudes de asistencia técnica y su estado |

## Tecnologías

Java 17 · Spring Boot 3.5 · Spring Web · Spring Data JPA · Bean Validation · MySQL · JUnit 5 + Mockito

## Estructura de cada microservicio

```
cl.duoc.tiotec.<contenidos|solicitudes>
├── model        Entidad JPA (= tabla) con sus validaciones
├── repository   Interfaz JpaRepository (consultas)
├── service      Lógica de negocio
├── controller   Endpoints REST (@RestController)
├── exception    Errores 404 y 400 en JSON
└── config       Datos iniciales de ejemplo
```

Flujo de una petición: **Controller** (recibe JSON y valida con `@Valid`) → **Service** (reglas) → **Repository** (JPA) → **MySQL**.

## Endpoints

### ms-contenidos · http://localhost:8081

| Método | Endpoint | Respuesta |
|---|---|---|
| GET | `/api/contenidos` | 200 · lista (opcional `?area=RESPALDOS`) |
| GET | `/api/contenidos/{id}` | 200 · un contenido, o 404 |
| POST | `/api/contenidos` | 201 · contenido creado, o 400 si falla una validación |
| PUT | `/api/contenidos/{id}` | 200 · contenido actualizado, 400 o 404 |
| DELETE | `/api/contenidos/{id}` | 204 · sin cuerpo, o 404 |

Ejemplo de cuerpo:

```json
{ "area": "RESPALDOS", "titulo": "Copia automática", "detalle": "Activa la copia automática en la nube." }
```

`area` acepta: `RESPALDOS`, `ACTUALIZACIONES`, `AUTENTICACION`, `RECUPERACION`.

### ms-solicitudes · http://localhost:8082

| Método | Endpoint | Respuesta |
|---|---|---|
| GET | `/api/solicitudes` | 200 · lista, la más nueva primero (opcional `?estado=ENVIADA`) |
| GET | `/api/solicitudes/{id}` | 200 · una solicitud, o 404 |
| POST | `/api/solicitudes` | 201 · creada siempre en estado `ENVIADA` |
| PUT | `/api/solicitudes/{id}` | 200 · datos y estado actualizados |
| DELETE | `/api/solicitudes/{id}` | 204 · sin cuerpo, o 404 |

Ejemplo de cuerpo:

```json
{ "categoria": "Respaldos", "dispositivo": "Tablet", "descripcion": "Mi tablet no respalda las fotos hace un mes.", "estado": "EN_REVISION" }
```

`estado` acepta: `ENVIADA`, `EN_REVISION`, `RESUELTA`. La descripción debe tener entre 20 y 500 caracteres.

## Cómo ejecutar

1. Iniciar MySQL en Laragon o XAMPP (usuario `root`, sin clave). Las bases se crean solas.
2. Abrir la carpeta `ms-contenidos` en IntelliJ IDEA o Spring Tool Suite como proyecto Maven y ejecutar `MsContenidosApplication`.
3. Repetir con `ms-solicitudes` y `MsSolicitudesApplication`.
4. Probar en el navegador: http://localhost:8081/api/contenidos y http://localhost:8082/api/solicitudes.

Si su MySQL tiene clave, cámbienla en `src/main/resources/application.properties` (`spring.datasource.password`).

## Pruebas

`ContenidoServiceTest` y `SolicitudServiceTest` prueban la lógica del servicio con el repositorio simulado (Mockito), sin necesidad de MySQL.
Se ejecutan con clic derecho en `src/test/java` → **Run Tests**.
