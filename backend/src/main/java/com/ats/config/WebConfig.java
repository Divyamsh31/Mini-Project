package com.ats.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@org.springframework.lang.NonNull ResourceHandlerRegistry registry) {
        System.out.println(">>> Registering Resource Handler for /uploads/**");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
        
        // Static frontend resources
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(@org.springframework.lang.NonNull org.springframework.web.servlet.config.annotation.ViewControllerRegistry registry) {
        // Forward all paths to index.html so React can handle them
        // Exclusion for API paths and static assets is handled by the order/regex
        registry.addViewController("/{path:[^\\.]*}")
                .setViewName("forward:/index.html");
        registry.addViewController("/**/{path:[^\\.]*}")
                .setViewName("forward:/index.html");
    }

    @Override
    public void addCorsMappings(@org.springframework.lang.NonNull org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // In production, replace with your domain
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}

