package com.bank.management.challenge.application;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.bank.management.challenge.infrastructure.repositories.jpa")
@EntityScan("com.bank.management.challenge.infrastructure.entities")
@ComponentScan("com.bank.management.challenge")
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("v1") String appVersion) {
		return new OpenAPI()
				.components(new Components())
				.info(new Info().title("Bank Management Challenge API").version(appVersion)
						.license(new License().name("Globank 1.0").url("http://springdoc.org")));
	}

}
