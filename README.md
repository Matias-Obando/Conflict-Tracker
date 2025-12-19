# Conflict Tracker API

API REST desarrollada con **Spring Boot** para la gestión de conflictos internacionales, países, facciones y eventos asociados.

El proyecto permite crear, consultar, actualizar y eliminar información relacionada con conflictos armados, así como las relaciones entre países, facciones y eventos históricos.


## Tecnologías utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Hibernate
- H2 Database (en memoria)
- Maven
- Postman (para pruebas)


## Estructura del proyecto

El proyecto sigue una arquitectura por capas:

- **controller** -> Endpoints REST
- **service** -> Lógica
- **repository** -> Acceso a datos (JPA)
- **model** -> Entidades JPA
- **dto** -> Objetos de transferencia de datos
- **resources** -> Configuración y scripts SQL


##  Base de datos

- Base de datos **H2 en memoria**
- Se inicializa automáticamente al arrancar la aplicación
- Consola disponible en:http://localhost:8080/h2-console
  
## Cómo ejecutar el proyecto
1-clonar el repositiorio -> git clone https://github.com/Matias-Obando/Conflict-Tracker.git
2-Abrir el proyecto en intellij IDEA y ejecutar ConflictTrackerApplication


## Pruebas de la API

Las pruebas se han realizado mediante:
Postman (GET,POST, PUT, DELETE)
Se han grabado vídeos demostrando el correcto funcionamiento de los endpoints.

## Funcionalidades implementadas

-CRUD completo de Conflicts, Countries, Factions y Events
-Relaciones ManyToMany y ManyToOne
-Uso de DTOs para entrada y salida
-Validaciones con Jakarta Validation
-Filtrado de conflictos por estado
-Consulta de conflictos por país
-Inicialización automática de datos

### Autor
Matías Obando
Practica Desarrollo de APIs REST con Spring Boot
