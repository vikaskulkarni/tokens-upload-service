package com.tokens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.tokens.config.FileStorageProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
@EnableConfigurationProperties({ FileStorageProperties.class })
@ComponentScan(basePackages = { "io.swagger", "com.tokens", "io.swagger.configuration" })
public class TokensStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(TokensStreamApplication.class, args);
	}

}
