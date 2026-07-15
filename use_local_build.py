import os

root_dir = r"C:\Users\admn\Downloads\library-bff"

services = {
    "bff": 8080,
    "ms-auth": 8081,
    "ms-book": 8082,
    "ms-author": 8083,
    "ms-loan": 8084,
    "ms-penalty": 8085,
    "ms-inventory": 8086,
    "ms-reservation": 8087,
    "ms-notification": 8088,
    "ms-review": 8089
}

for service, port in services.items():
    s_dir = os.path.join(root_dir, service)
    if not os.path.exists(s_dir):
        continue
        
    dockerfile_path = os.path.join(s_dir, "Dockerfile")
    
    new_content = f"""FROM eclipse-temurin:25-jre
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE {port}
ENTRYPOINT ["java", "-jar", "app.jar"]
"""
    
    with open(dockerfile_path, "w", encoding="utf-8") as f:
        f.write(new_content)
        
    print(f"Updated Dockerfile for {service} to use local jar on port {port}")

print("=== ALL DOCKERFILES CONVERTED TO SINGLE-STAGE LOCAL BUILD ===")
