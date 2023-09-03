package com.archpj.GetATestBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GetATestBotApplication {
	static final Logger logger = LoggerFactory.getLogger(GetATestBotApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GetATestBotApplication.class, args);
	}

}
