package com.supercoin.coin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EntityScan("com.supercoin.coin.model")
@EnableAsync
public class SupercoinApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupercoinApplication.class, args);
		System.out.println("Jay Shree Ram");
	}

}
