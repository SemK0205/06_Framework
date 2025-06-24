package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.project.common.interceptor.CategoryInterceptor;

@Configuration
public class TestInterceptorConfig implements WebMvcConfigurer {
	
	@Bean
	public CategoryInterceptor categoryInterceptor() {
		return new CategoryInterceptor();
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( categoryInterceptor() )
		.addPathPatterns("/**")
		.excludePathPatterns("/css/**", "/js/**", "/images/**", "/favicon.ico"); 
	}
}
