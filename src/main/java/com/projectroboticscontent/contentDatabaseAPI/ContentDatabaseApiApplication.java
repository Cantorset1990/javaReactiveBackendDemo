package com.projectroboticscontent.contentDatabaseAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ContentDatabaseApiApplication {

	public static void main(String[] args) {

		//SpringApplication.run(ContentDatabaseApiApplication.class, args);

		SpringApplication app = new SpringApplication(ContentDatabaseApiApplication.class);
		app.setDefaultProperties(Collections
				.singletonMap("server.port", "8082"));
		app.run(args);
	}

}
