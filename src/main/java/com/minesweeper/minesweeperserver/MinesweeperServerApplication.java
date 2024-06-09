package com.minesweeper.minesweeperserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MinesweeperServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperServerApplication.class, args);
	}

}
