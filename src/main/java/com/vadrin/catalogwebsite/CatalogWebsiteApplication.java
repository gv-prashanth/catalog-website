package com.vadrin.catalogwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CatalogWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogWebsiteApplication.class, args);
	}
}
