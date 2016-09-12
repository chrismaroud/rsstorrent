package com.bitsfromspace.torrentrss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan({"com.bitsfromspace.torrentrss", "com.bitsfromspace.torrentrss.services"})
@ServletComponentScan("com.bitsfromspace.torrentrss")
@Configuration
public class RssApplication {

	public static void main(String[] args) {
		SpringApplication.run(RssApplication.class, args);
	}
}
