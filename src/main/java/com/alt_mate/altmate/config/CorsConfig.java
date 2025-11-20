package com.example.altmate_operations.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    
    @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:4200}")
    private String[] allowedOrigins;
    
    @Value("${app.cors.allowed-methods:GET,POST,PUT,DELETE,PATCH,OPTIONS}")
    private String[] allowedMethods;
    
    @Value("${app.cors.allowed-headers:*}")
    private String[] allowedHeaders;
    
    @Value("${app.cors.exposed-headers:Authorization,Content-Type}")
    private String[] exposedHeaders;
    
    @Value("${app.cors.allow-credentials:true}")
    private boolean allowCredentials;
    
    @Value("${app.cors.max-age:3600}")
    private long maxAge;
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allowed origins (frontend URLs)
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
        
        // Allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList(allowedMethods));
        
        // Allowed headers
        configuration.setAllowedHeaders(Arrays.asList(allowedHeaders));
        
        // Exposed headers (headers that frontend can access)
        configuration.setExposedHeaders(Arrays.asList(exposedHeaders));
        
        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(allowCredentials);
        
        // How long the response from a pre-flight request can be cached
        configuration.setMaxAge(maxAge);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
