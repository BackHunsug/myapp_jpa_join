package com.woori.myapp;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class MyappJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyappJpaApplication.class, args);
	}

}
