# Script para compilar todos los microservicios en Windows (PowerShell)
$services = @('bff', 'ms-auth', 'ms-book', 'ms-author', 'ms-loan', 'ms-penalty', 'ms-inventory', 'ms-reservation', 'ms-notification', 'ms-review')

Write-Host "=== INICIANDO COMPILACIÓN DE TODOS LOS MICROSERVICIOS ===" -ForegroundColor Green

foreach ($service in $services) {
    Write-Host "`nCompilando $service..." -ForegroundColor Cyan
    Push-Location $service
    try {
        .\gradlew.bat clean bootJar -x test
        if ($LASTEXITCODE -ne 0) {
            Write-Error "Error compilando $service"
            exit $LASTEXITCODE
        }
    }
    finally {
        Pop-Location
    }
}

Write-Host "`n=== COMPILACIÓN COMPLETADA EXITOSAMENTE ===" -ForegroundColor Green
Write-Host "Ahora puedes ejecutar: docker compose up -d --build" -ForegroundColor Yellow
