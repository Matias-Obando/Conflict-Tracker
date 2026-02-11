# Conflict Tracker API

API REST desarrollada con Spring Boot para la gestión de conflictos internacionales, países, facciones y eventos asociados.

El proyecto permite crear, consultar, actualizar y eliminar información relacionada con conflictos armados, así como las relaciones entre países, facciones y eventos históricos.

Además, incluye una pequeña interfaz web desarrollada con Thymeleaf para la gestión de conflictos.


## Tecnologías utilizadas

-Java 21
-Spring Boot 3
-Spring Data JPA
-Hibernate
-PostgreSQL
-H2 Database (perfil dev)
-Thymeleaf (frontend)
-Maven
-Postman (para pruebas)

## Estructura del proyecto

El proyecto sigue una arquitectura por capas:

- **controller** -> Endpoints REST
- **web** -> Controladores MVC (Thymeleaf)
- **service** -> Lógica
- **repository** -> Acceso a datos (JPA)
- **model** -> Entidades JPA
- **dto** -> Objetos de transferencia de datos
- **resources** -> Configuración y scripts SQL


##  Base de datos

El proyecto utiliza perfiles:
**dev** → H2 en memoria
**prod** → PostgreSQL
La base de datos PostgreSQL se inicializa automáticamente mediante data.sql al arrancar la aplicación.

## Frontend (Thymeleaf)

Se ha implementado una pequeña interfaz web clásica:
Ruta base: http://localhost:8080/web/conflicts

## Funcionalidades:
-Listado de conflictos
-Creación de conflictos (formulario con validaciones)
-Eliminación desde la interfaz
-Mensajes de confirmación tras creación
-El frontend reutiliza la misma lógica de negocio (Services) que la API REST.

## Cómo ejecutar el proyecto

Clonar el repositorio
git clone https://github.com/Matias-Obando/Conflict-Tracker.git
Configurar PostgreSQL (si se usa perfil prod)
Ejecutar ConflictTrackerApplication desde IntelliJ IDEA

## Pruebas de la API

Las pruebas se han realizado mediante:
Postman (GET, POST, PUT, DELETE)
Se han grabado vídeos demostrando el correcto funcionamiento de los endpoints

## Funcionalidades implementadas

-CRUD completo de Conflicts, Countries, Factions y Events
-Relaciones ManyToMany y ManyToOne
-Uso de DTOs para entrada y salida
-Validaciones con Jakarta Validation
-Filtrado de conflictos por estado
-Consulta de conflictos por país
-Inicialización automática de datos
-Interfaz web con Thymeleaf (listado y gestión básica)

## Autor
Matías Obando
Práctica Desarrollo de APIs REST con Spring Boot
