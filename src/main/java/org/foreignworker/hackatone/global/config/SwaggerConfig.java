package org.foreignworker.hackatone.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  
  @Bean
  public OpenAPI openAPI() {
    Info info = new Info()
      .title("외국인 근로자 노동권 침해 감지 API")
      .version("v1.0")
      .description("외국인 근로자의 노동권 침해 사례를 감지하고 분석하는 API 문서");
    
    // Security Scheme 설정 추가
    SecurityScheme securityScheme = new SecurityScheme()
      .type(SecurityScheme.Type.HTTP) // HTTP 방식 인증
      .scheme("bearer") // Bearer Token 사용
      .bearerFormat("JWT"); // JWT 형식 지정
    
    // Security Requirement 추가
    SecurityRequirement securityRequirement = new SecurityRequirement()
      .addList("BearerAuth");
    
    return new OpenAPI()
      .info(info)
      .addSecurityItem(securityRequirement)
      .components(new io.swagger.v3.oas.models.Components()
        .addSecuritySchemes("BearerAuth", securityScheme));
  }
} 