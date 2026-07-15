# Estrategia de Tests y Organización de Git - Sistema de Biblioteca

Este documento detalla la arquitectura de pruebas, los tipos de tests unitarios y de integración con H2 implementados en el proyecto, y la estrategia de control de versiones utilizada.

---

## 1. Unit Test de Controller
* **Archivo Ejemplo**: `LibraryControllerTest.java` (en [LibraryControllerTest.java](file:///c:/Users/admn/Downloads/library/bff/src/test/java/com/library/bff/controller/LibraryControllerTest.java))
* **Objetivo**:
  * Validar que los endpoints del controlador armen correctamente los objetos de respuesta `ResponseEntity` y devuelvan los códigos HTTP correspondientes (`200 OK`, `201 Created`, `204 No Content`, `404 Not Found`).
  * Aislar por completo la capa web/REST de la lógica de negocio simulando los servicios mediante mocks.
* **Puntos Clave**:
  * Se instancia el controlador de forma manual y directa en cada método de prueba (`new LibraryController(...)`), evitando reflection mágica.
  * Se configuran las respuestas de las dependencias (`BookService`, `AuthorService`) usando Mockito.
  * Se ejecutan los asserts clásicos de JUnit 5 para validar el cuerpo y estado de los objetos `ResponseEntity` resultantes.

---

## 2. Unit Test de Service
* **Archivo Ejemplo**: `AuthorServiceImplTest.java` (en [AuthorServiceImplTest.java](file:///c:/Users/admn/Downloads/library/bff/src/test/java/com/library/bff/service/AuthorServiceImplTest.java)) o `BookServiceImplTest.java` (BFF)
* **Objetivo**:
  * Verificar de forma estricta las reglas lógicas del negocio (ej. validación previa de existencia del autor al crear un libro en el BFF).
  * Validar la tolerancia a fallos del BFF (resiliencia), comprobando que si el microservicio de autores falla, el servicio intercepte la excepción y devuelva el libro con el autor en `null` en lugar de lanzar un error 500.
* **Puntos Clave**:
  * Son pruebas unitarias puras, sin levantar el contexto de Spring (máxima velocidad).
  * Se utiliza Mockito para simular los clientes HTTP (`AuthorClient`, `BookClient`).
  * Se utiliza `assertThrows` para validar que se lancen y propaguen correctamente las excepciones de negocio correspondientes (como entidades no encontradas).

---

## 3. Unit Test de DTOs y Validación (Equivalente a Utils)
* **Archivo Ejemplo**: `BffDtoTest.java` (en [BffDtoTest.java](file:///c:/Users/admn/Downloads/library/bff/src/test/java/com/library/bff/dto/BffDtoTest.java)) o `BookDtoTest.java`
* **Objetivo**:
  * Asegurar que las validaciones declarativas de Jakarta en los Java Records (como `@NotBlank` o `@NotNull`) se apliquen correctamente y detengan las solicitudes inválidas.
* **Puntos Clave**:
  * Se instancia manualmente el motor de validación `jakarta.validation.Validator`.
  * Se construyen Records con atributos inválidos (ej. títulos vacíos o años nulos) y se verifica con asserts que el set de violaciones (`Set<ConstraintViolation>`) no esté vacío.

---

## 4. Test de Repository con H2
* **Archivo Ejemplo**: `BookRepositoryTest.java` (en [BookRepositoryTest.java](file:///c:/Users/admn/Downloads/library/ms-book/src/test/java/com/library/book/repository/BookRepositoryTest.java))
* **Objetivo**:
  * Validar el comportamiento de las consultas personalizadas de base de datos JPA (como las búsquedas case-insensitive y búsquedas parciales de libros).
* **Puntos Clave**:
  * Se levanta el contexto parcial de Spring mediante `@SpringBootTest`.
  * Se utiliza la base de datos en memoria **H2** configurada a través del perfil `test` (que se activa automáticamente en pruebas).
  * Flyway ejecuta las migraciones DDL iniciales (`V1__...sql`) en la base de datos H2 al arrancar el test, asegurando que el esquema de base de datos esté perfectamente sincronizado con el modelo.

---

## 5. Herramienta para Correr Tests
Para ejecutar toda la suite de pruebas unitarias y de integración de todo el proyecto, ejecuta desde la raíz del mismo el siguiente comando en la terminal:
```bash
./gradlew test
```
*Si quieres correr pruebas solo sobre un microservicio específico, ingresa a su respectiva carpeta y ejecuta el mismo comando.*

---

## 6. Cobertura de Código (JaCoCo)
El proyecto cuenta con el plugin de cobertura **JaCoCo** configurado en todos los módulos. Al finalizar la ejecución de las pruebas, se generan reportes detallados automáticamente ejecutando:
```bash
./gradlew test jacocoTestReport
```

### Ubicación de los Reportes:
* **Reporte HTML (Visualizable en navegador)**:
  * BFF: `bff/build/reports/jacoco/test/html/index.html`
  * ms-auth: `ms-auth/build/reports/jacoco/test/html/index.html`
  * ms-book: `ms-book/build/reports/jacoco/test/html/index.html`
  * ms-author: `ms-author/build/reports/jacoco/test/html/index.html`
* **Reporte XML (Para herramientas de integración)**:
  * BFF: `bff/build/reports/jacoco/test/jacocoTestReport.xml`

---

## 7. Estrategia de Control de Versiones en Git
* **Rama `main`**: Contiene el código fuente estable de producción que compila y se ejecuta en contenedores Docker de forma garantizada.
* **Ramas de funcionalidad (`feature/...`)**: Utilizadas para el desarrollo aislado de tareas independientes (ej. `feature/update-author-bff`, `feature/testing-manual-instantiation`). Una vez verificadas de manera local por el equipo, se mezclan a `main` a través de solicitudes de pull request (PR).
