import os

root_dir = r"C:\Users\admn\Downloads\library-bff"
new_services = ["ms-loan", "ms-penalty", "ms-inventory", "ms-reservation", "ms-notification", "ms-review"]

for service in new_services:
    s_dir = os.path.join(root_dir, service)
    if not os.path.exists(s_dir):
        continue
        
    pkg_name = service.replace("-", "")
    config_dir = os.path.join(s_dir, "src", "main", "java", "com", "library", pkg_name, "config")
    os.makedirs(config_dir, exist_ok=True)
    
    file_path = os.path.join(config_dir, "SecurityConfig.java")
    
    content = f"""package com.library.{pkg_name}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {{
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }}
}}
"""
    with open(file_path, "w", encoding="utf-8") as f:
        f.write(content)
        
    print(f"Added SecurityConfig to {service}")

print("=== ALL SECURITY CONFIGS ADDED SUCCESSFULLY ===")
