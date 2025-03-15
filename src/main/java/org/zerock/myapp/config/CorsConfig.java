package org.zerock.myapp.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	            .allowedOrigins("https://localhost") // 프론트엔드 도메인
	            .allowedMethods("*")                // 허용할 HTTP 메소드
	            .allowedHeaders("*")                // 모든 헤더 허용
	            .allowCredentials(true);            // 쿠키 전송 허용 여부
	    }
}
