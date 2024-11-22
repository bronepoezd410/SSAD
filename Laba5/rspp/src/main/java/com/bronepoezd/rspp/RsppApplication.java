package com.bronepoezd.rspp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.bronepoezd.rspp.model")
@EnableJpaRepositories(basePackages = "com.bronepoezd.rspp.repository")
public class RsppApplication {
	public static void main(String[] args) {
		SpringApplication.run(RsppApplication.class, args);
	}
}