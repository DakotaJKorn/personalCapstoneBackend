package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class PracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/userlogin").allowedOrigins("https://finalprojectdakota.web.app");
				registry.addMapping("/userlogin/loginattempt").allowedOrigins("https://finalprojectdakota.web.app");
				registry.addMapping("/useraccounts").allowedOrigins("https://finalprojectdakota.web.app");
				registry.addMapping("/userlogin").allowedOrigins("https://finalprojectdakota.web.app");
				registry.addMapping("/user").allowedOrigins("https://finalprojectdakota.web.app");
			}
		};
	}

}
