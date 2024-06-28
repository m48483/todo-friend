package com.example.todo_friend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
@SpringBootApplication
public class TodoFriendApplication implements WebFluxConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(TodoFriendApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	@Value("${cors.allowed-origins}")
	private String allowedOrigins;

	@Value("${cors.allowed-methods}")
	private String allowedMethods;

	@Value("${cors.allowed-headers}")
	private String allowedHeaders;

	@Value("${cors.allow-credentials}")
	private boolean allowCredentials;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(allowedOrigins.split(","))
				.allowedMethods(allowedMethods.split(","))
				.allowedHeaders(allowedHeaders)
				.allowCredentials(allowCredentials);
	}
}
