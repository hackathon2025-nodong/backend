package org.foreignworker.hackatone.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
      .allowedOrigins("http://localhost:3000")  // 프론트엔드 개발 서버
      .allowedMethods("*")
      .allowedHeaders("*");

    registry.addMapping("/auth/**")
      .allowedOrigins("http://localhost:3000")
      .allowedMethods("*")
      .allowCredentials(true) // 쿠키 포함 요청 허용
      .allowedHeaders("*");
      
    registry.addMapping("/documents/**")
      .allowedOrigins("http://localhost:3000")
      .allowedMethods("*")
      .allowCredentials(true)
      .allowedHeaders("*");
  }
} 