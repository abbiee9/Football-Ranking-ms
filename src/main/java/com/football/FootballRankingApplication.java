package com.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class FootballRankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballRankingApplication.class, args);
	}
}
