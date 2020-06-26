package com.flowable.fltest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(proxyBeanMethods = false)
public class FltestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FltestApplication.class, args);
	}

}
