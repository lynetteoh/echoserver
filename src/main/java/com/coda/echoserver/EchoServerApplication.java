package com.coda.echoserver;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EchoServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EchoServerApplication.class, args);
	}

}
