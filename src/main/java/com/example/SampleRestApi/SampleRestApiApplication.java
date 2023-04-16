package com.example.SampleRestApi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Student REST API",
				description = "Student App API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Vishal",
						email = "vishal.pvn.edu@gmail.com"
				)
//		license = @License(<name>, <url>)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Student App Documentation",
				url = "https://github.com/Vishal-Marvel/SimpleRestAPI"
		)
)
@Controller
public class SampleRestApiApplication {


	@GetMapping("/")
	public String index(){
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(SampleRestApiApplication.class, args);

	}

}
