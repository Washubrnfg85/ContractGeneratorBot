package com.archpj.GetATestBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GetATestBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetATestBotApplication.class, args);
	}

}
