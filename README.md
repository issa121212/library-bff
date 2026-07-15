# Sistema de Biblioteca - Arquitectura de Microservicios

Este repositorio implementa una solución completa para la gestión de una biblioteca digital, utilizando una arquitectura desacoplada basada en microservicios y un portal de orquestación centralizado bajo el patrón **BFF (Backend for Frontend)**.

---

## 🏛️ Arquitectura del Sistema (C4 Model)

### 1. Nivel C2: Contenedores (Software Pieces)
El nivel C2 detalla las diferentes piezas de software ejecutables que componen el sistema, sus tecnologías y bases de datos aisladas:

```mermaid
graph TD
    Client[Cliente / Postman / Client.http] -- HTTP/REST: Puerto 8080 --> BFF[BFF Gateway :8080 <br> Spring Boot]

    %% Enlaces desde BFF a microservicios
    BFF -- REST --> msAuth[ms-auth :8081]
    BFF -- REST --> msBook[ms-book :8082]
    BFF -- REST --> msAuthor[ms-author :8083]
    BFF -- REST --> msLoan[ms-loan :8084]
    BFF -- REST --> msPenalty[ms-penalty :8085 <br> Stateless]
    BFF -- REST --> msInventory[ms-inventory :8086]
    BFF -- REST --> msReservation[ms-reservation :8087]
    BFF -- REST --> msNotification[ms-notification :8088 <br> Stateless]
    BFF -- REST --> msReview[ms-review :8089]

    %% Bases de Datos
    msAuth --> dbAuth[(auth_db <br> PostgreSQL)]
    msBook --> dbBook[(book_db <br> PostgreSQL)]
    msAuthor --> dbAuthor[(author_db <br> PostgreSQL)]
    msLoan --> dbLoan[(loan_db <br> PostgreSQL)]
    msInventory --> dbInventory[(inventory_db <br> PostgreSQL)]
    msReservation --> dbReservation[(reservation_db <br> PostgreSQL)]
    msReview --> dbReview[(review_db <br> PostgreSQL)]
```

### 2. Nivel C3: Componentes de un Microservicio
El nivel C3 describe la estructura interna estándar de cada uno de los microservicios, asegurando la separación de responsabilidades en capas:

```mermaid
graph LR
    BFF[BFF Gateway] -- JSON / HTTP --> Controller[Controller <br> Mapeo REST / Valida Record]
    Controller --> Service[Service <br> Lógica de Negocio / Logs @Slf4j]
    Service --> Repository[Repository <br> Spring Data JPA]
    Repository --> DB[(PostgreSQL)]

    %% Record DTOs
    RecordDTO[Java Record DTOs] -.-> Controller
    RecordDTO -.-> Service
```

---

## 🛠️ Detalle de las Capas e Inmutabilidad
* **Capa Controller**: Expone las rutas de cada entidad. Es responsable de recibir la petición HTTP y validar los payloads inmutables.
* **Capa Service**: Implementa la lógica de negocio y encapsula la escritura de logs mediante `@Slf4j`. Todos los comentarios siguen el estándar JavaDoc (`/** ... */`) en español.
* **Java Records (DTOs)**: Todo el transporte de datos se realiza a través de records inmutables, previniendo alteraciones de datos accidentales en tránsito.

---

## ⚙️ Requisitos Previos e Instalación

### Requisitos:
* **Java**: JDK 25 instalado.
* **Docker y Docker Desktop** instalados y activos.

### Pasos para iniciar el sistema:

1. **Compilar los JARs de forma nativa**:
   ```powershell
   # Ejecutar en la raíz para compilar todos los proyectos
   python -c "import subprocess, os; [subprocess.run('.\gradlew.bat clean bootJar -x test', shell=True, cwd=os.path.join(os.getcwd(), s), check=True) for s in ['bff', 'ms-auth', 'ms-book', 'ms-author', 'ms-loan', 'ms-penalty', 'ms-inventory', 'ms-reservation', 'ms-notification', 'ms-review']]"
   ```

2. **Levantar los servicios con Docker Compose**:
   ```bash
   docker compose up -d --build
   ```

3. **Verificar estado de contenedores**:
   ```bash
   docker compose ps
   ```

---

## 📖 Documentación Interactiva (Swagger)
El Swagger de documentación está disponible a través de la interfaz del BFF:
* **URL**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
