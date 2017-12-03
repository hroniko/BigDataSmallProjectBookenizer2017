package com.bigdata.bookenizer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;

import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAutoConfiguration
@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class BookenizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookenizerApplication.class, args);
	}
}

