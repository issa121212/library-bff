# Estrategia de Pruebas de Integración - Sistema de Biblioteca

Este documento detalla la estrategia de validación e integración automática implementada en el sistema mediante el script `run_client_tests.py`, actuando como el cliente principal del Backend for Frontend (BFF).

---

## 🔬 Estrategia de Pruebas de Integración y Simulación

Debido a que el BFF actúa como la cara de nuestro cliente back-end (Back-for-Frontend) y centraliza toda interacción lógica y de seguridad, la suite de tests se realiza de extremo a extremo (E2E) simulando solicitudes REST directamente al puerto `8080` (BFF) con tokens de autorización dinámicos firmados por `ms-auth`.

---

## 🛠️ Ejecución de la Suite de Pruebas

El script automático de integración en Python se ejecuta desde la raíz de la siguiente manera:

```bash
python run_client_tests.py
```

*Nota: Asegúrate de que todos los contenedores de Docker estén activos (`docker compose ps`) antes de ejecutar el script.*

---

## 📝 Detalle de los 18 Casos de Prueba Incluidos

El script `run_client_tests.py` ejecuta y verifica secuencialmente las siguientes operaciones críticas:

1. **[TEST 1] Registro de Usuario**: Envía las credenciales al BFF (`/api/auth/register`) para registrar un nuevo usuario (retorna `403` si ya existe, lo cual es normal).
2. **[TEST 2] Inicio de Sesión (Login)**: Envía credenciales a `/api/auth/login` para obtener un Token Bearer JWT de acceso válido.
3. **[TEST 3] Creación de Autor**: Registra un nuevo autor literario.
4. **[TEST 4] Listado de Autores**: Comprueba la persistencia recuperando la lista de autores disponibles.
5. **[TEST 5] Creación de Libro**: Registra un libro utilizando un ISBN aleatorio y lo vincula al autor creado en el paso 3. El `stock` inicial devuelto es `0`.
6. **[TEST 6] Listado de Libros**: Comprueba la visualización y composición del libro con su autor.
7. **[TEST 7] Registro en Inventario (Copia Física)**: Crea una copia física asociada al libro con código de barras único.
8. **[TEST 8] Listado de Copias**: Verifica la existencia de copias físicas creadas y su estado (`isAvailable: true`).
9. **[TEST 9] Solicitud de Préstamo**: Crea un préstamo activo asociando la copia física. Se calcula automáticamente el vencimiento a **14 días en el futuro** y `returnDate` se inicia en `null`.
10. **[TEST 10] Listado de Préstamos**: Recupera el listado de préstamos activos e históricos.
11. **[TEST 10b] Registro de Devolución**: Procesa el retorno del libro mediante `POST /loans/{id}/return`. Se verifica que el estado cambie a `"RETURNED"` y `returnDate` se configure con el tiempo exacto actual.
12. **[TEST 11] Creación de Multa**: Calcula una multa para el préstamo actual y la registra en el servicio in-memory `ms-penalty`.
13. **[TEST 12] Listado de Multas**: Recupera el listado temporal de multas registradas.
14. **[TEST 12b] Pago de Multa**: Ejecuta el pago mediante `POST /penaltys/{id}/pay` y comprueba que el estado de la multa cambie a `"PAID"`.
15. **[TEST 13] Registro de Reserva**: Crea una reserva activa para un libro específico.
16. **[TEST 13b] Bloqueo de Reserva Duplicada**: Intenta registrar otra reserva para el mismo libro por el mismo usuario. Comprueba que el microservicio bloquee el duplicado y retorne un error: `"El usuario ya tiene una reserva activa para este libro."`
17. **[TEST 14] Listado de Reservas**: Comprueba la cola de espera de reservas pendientes.
18. **[TEST 15] Envío de Alertas (Notificación)**: Envía una alerta de correo mediante el servicio in-memory `ms-notification`.
19. **[TEST 16] Historial de Notificaciones**: Lista el historial en memoria de las alertas enviadas.
20. **[TEST 17] Registro de Reseña**: Añade comentarios y una puntuación de 1 a 5 estrellas al libro.
21. **[TEST 18] Listado de Reseñas**: Recupera y despliega las opiniones de los usuarios sobre el catálogo de libros.
