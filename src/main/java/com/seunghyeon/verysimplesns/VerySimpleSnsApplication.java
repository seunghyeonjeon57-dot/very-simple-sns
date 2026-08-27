package com.seunghyeon.verysimplesns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VerySimpleSnsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VerySimpleSnsApplication.class, args);
	}

}
