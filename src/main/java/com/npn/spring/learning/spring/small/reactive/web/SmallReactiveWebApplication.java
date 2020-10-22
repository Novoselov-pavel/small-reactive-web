package com.npn.spring.learning.spring.small.reactive.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class SmallReactiveWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallReactiveWebApplication.class, args);
	}

}
