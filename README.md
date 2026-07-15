# Sistema de Biblioteca - Microservicios

Proyecto desarrollado con una arquitectura de microservicios para la gestión completa de una biblioteca digital, utilizando un patrón **BFF (Backend for Frontend)** para la orquestación, composición de datos y control centralizado de la autenticación.

---

## Estructura del Sistema (Microservicios)

El sistema está compuesto por los siguientes componentes principales:

### 1. Backend for Frontend (BFF)
Actúa como el único punto de entrada (Gateway) y orquestador del sistema.
* **Funciones principales**:
  * Orquestar y componer las llamadas a todos los microservicios del sistema.
  * Encapsular el flujo de autenticación (`/api/auth/register` y `/api/auth/login`) redirigiéndolo internamente.
  * Validar tokens JWT asimétricos de manera centralizada.
  * Ofrecer tolerancia a fallos (resiliencia) cuando fallan servicios secundarios (como retornar `author: null` si un autor no existe).
  * Calcular dinámicamente el stock de copias físicas de un libro llamando a `ms-inventory`.

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
  * Operaciones CRUD completas para libros del catálogo.

### 4. Servicio de Autores (`ms-author`)
Encargado de la gestión del registro de autores literarios.
* **Funciones principales**:
  * Operaciones CRUD completas para autores.

### 5. Servicio de Inventario (`ms-inventory`)
Administra las copias físicas individuales asociadas a cada libro en catálogo.
* **Funciones principales**:
  * Registrar copias físicas de libros con un código de barra (`barcode`) único y estado físico.
  * Controlar la disponibilidad de copias (`isAvailable`).

### 6. Servicio de Préstamos (`ms-loan`)
Controla el ciclo de vida de los préstamos de copias físicas a los usuarios.
* **Funciones principales**:
  * Registrar préstamos activos con una fecha límite (`dueDate`) automática de 14 días.
  * Registrar devoluciones reales de libros mediante un endpoint dedicado (`/return`), estableciendo la fecha exacta en `returnDate` y marcando el estado como `"RETURNED"`.

### 7. Servicio de Multas (`ms-penalty` - Stateless)
Calcula e implementa sanciones monetarias a usuarios por devoluciones tardías.
* **Funciones principales**:
  * Generar y listar multas pendientes de pago asociadas a un préstamo.
  * Registrar el pago de multas mediante un endpoint dedicado (`/pay`), cambiando el estado de la multa a `"PAID"`.
  * Restringir la eliminación de multas (eliminación bloqueada por lógica de negocio).

### 8. Servicio de Reservas (`ms-reservation`)
Administra las listas de espera para libros que actualmente no tienen stock disponible.
* **Funciones principales**:
  * Crear y gestionar solicitudes de reserva en estado `"PENDING"`.
  * Impedir reservas duplicadas activas de un mismo libro por parte de un mismo usuario.

### 9. Servicio de Notificaciones (`ms-notification` - Stateless)
Envía alertas sobre el estado físico de los libros y avisos de vencimiento.
* **Funciones principales**:
  * Simular el envío de correos electrónicos y mensajes SMS mediante logs de consola.
  * Consultar el historial en memoria de alertas emitidas.

### 10. Servicio de Reseñas (`ms-review`)
Permite a los usuarios publicar opiniones y calificar libros de la biblioteca.
* **Funciones principales**:
  * Registrar opiniones asociadas a libros y puntuación en estrellas (de 1 a 5).

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje**: Java 25
* **Framework**: Spring Boot 3 / Spring Security
* **Seguridad**: JSON Web Tokens (JWT) / Claves Asimétricas RSA / JWKS
* **Base de Datos**: PostgreSQL (bases de datos independientes para cada microservicio stateful)
* **Migraciones de Base de Datos**: Flyway
* **Contenedores y Orquestación**: Docker / Docker Compose
* **Gestor de Construcción**: Gradle

---

## ⚙️ Pasos para Iniciar el Sistema

1. **Compilar los JARs de forma nativa en tu host**:
   ```powershell
   python -c "import subprocess, os; [subprocess.run('.\gradlew.bat clean bootJar -x test', shell=True, cwd=os.path.join(os.getcwd(), s), check=True) for s in ['bff', 'ms-auth', 'ms-book', 'ms-author', 'ms-loan', 'ms-penalty', 'ms-inventory', 'ms-reservation', 'ms-notification', 'ms-review']]"
   ```

2. **Levantar los contenedores de Docker**:
   ```bash
   docker compose up -d --build
   ```

3. **Acceder a Swagger (Documentación Interactiva)**:
   * URL: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
