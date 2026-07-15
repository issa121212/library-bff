#!/bin/bash
# Script para compilar todos los microservicios en Linux/macOS (Bash)
set -e

services=("bff" "ms-auth" "ms-book" "ms-author" "ms-loan" "ms-penalty" "ms-inventory" "ms-reservation" "ms-notification" "ms-review")

echo -e "\033[0;32m=== INICIANDO COMPILACIÓN DE TODOS LOS MICROSERVICIOS ===\033[0m"

for service in "${services[@]}"; do
    echo -e "\n\033[0;36mCompilando $service...\033[0m"
    cd "$service"
    chmod +x gradlew
    ./gradlew clean bootJar -x test
    cd ..
done

echo -e "\n\033[0;32m=== COMPILACIÓN COMPLETADA EXITOSAMENTE ===\033[0m"
echo -e "\033[0;33mAhora puedes ejecutar: docker compose up -d --build\033[0m"
