# Sistema de Biblioteca - Microservicios

Proyecto desarrollado con una arquitectura de microservicios para la gestión completa de una biblioteca, utilizando un patrón **BFF (Backend for Frontend)** para la orquestación y composición de datos.

---

## Estructura del Sistema (Microservicios)

El sistema está compuesto por los siguientes componentes principales:

### 1. Backend for Frontend (BFF)
Actúa como el único punto de entrada (Gateway) y orquestador del sistema.
* **Funciones principales**:
  * Orquestar y componer las llamadas a los microservicios de libros y autores.
  * Autenticar peticiones mediante la validación de tokens JWT.
  * Ofrecer tolerancia a fallos (resiliencia) cuando fallan servicios secundarios (como retornar `author: null` si un autor no existe).

### 2. Servicio de Autenticación (`ms-auth`)
Encargado de la gestión de usuarios, seguridad y generación de credenciales.
* **Funciones principales**:
  * Registro de usuarios.
  * Inicio de sesión (Login) de usuarios.
  * Generación y firma de tokens JWT (RSA).
  * Exposición de claves públicas (JWKS) para la validación de tokens.

### 3. Servicio de Libros (`ms-book`)
Encargado de la gestión del catálogo y persistencia de libros.
* **Funciones principales**:
  * Crear libros.
  * Listar libros.
  * Buscar libros por ID.
  * Actualizar libros.
  * Eliminar libros.

### 4. Servicio de Autores (`ms-author`)
Encargado de la gestión del registro de autores literarios.
* **Funciones principales**:
  * Crear autores.
  * Listar autores.
  * Buscar autores por ID.
  * Actualizar autores.
  * Eliminar autores.

---

## Tecnologías Utilizadas

* **Lenguaje**: Java
* **Framework**: Spring Boot (Spring Web, Spring Security, Spring Data JPA)
* **Seguridad**: JSON Web Tokens (JWT) / JWKS
* **Base de Datos**: PostgreSQL
* **Migraciones de Base de Datos**: Flyway
* **Contenedores y Orquestación**: Docker / Docker Compose
* **Gestor de Construcción**: Gradle
