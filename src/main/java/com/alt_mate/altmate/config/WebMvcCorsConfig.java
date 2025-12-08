package com.alt_mate.altmate.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Alternative CORS Configuration using WebMvcConfigurer
 * This is disabled by default. Use CorsConfig (CorsConfigurationSource) instead.
 * Enable this if you prefer global CORS configuration at MVC level.
 */
//@Configuration
public class WebMvcCorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:3000",    // React default
                    "http://localhost:4200",    // Angular default
                    "http://localhost:8081",    // Vue default
                    "http://localhost:5173",     // Vite default
                    "http://192.168.1.5:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
