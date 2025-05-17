package com.example.RESTfulWebApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//hlavní třída aplikace
@SpringBootApplication
// zapnutí cachování
@EnableCaching
public class OdletyWebAplication {
	public static void main(String[] args) {
		SpringApplication.run(OdletyWebAplication.class, args);
	}

}
