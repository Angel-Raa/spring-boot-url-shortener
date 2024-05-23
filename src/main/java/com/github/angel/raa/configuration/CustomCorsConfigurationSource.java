package com.github.angel.raa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CustomCorsConfigurationSource {
    @Bean
    public CorsConfigurationSource corsConfigurationSource () {
        CorsConfiguration configuration = new CorsConfiguration();
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource  = new UrlBasedCorsConfigurationSource();
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUt"));
        // Cabeceras permitidas en la solicitud
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        // Orígenes permitidos (aquí se puede configurar el dominio específico)
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Disposition"));
        // Tiempo máximo de caché para las respuestas preflight de CORS (en segundos)
        configuration.setMaxAge(3600L);
        // Permitir el envío de cookies y credenciales
        configuration.setAllowCredentials(true);


        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter(corsConfigurationSource());
    }


}
