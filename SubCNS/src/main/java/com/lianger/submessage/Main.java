package com.lianger.submessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(value="com.lianger.submessage")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
}
