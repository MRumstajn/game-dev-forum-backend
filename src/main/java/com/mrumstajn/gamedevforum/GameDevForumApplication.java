package com.mrumstajn.gamedevforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GameDevForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameDevForumApplication.class, args);
	}

}
