package com.archpj.GetATestBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration  //прочитать про аннотацию и другие способы конфигурации. И вообще зачем она нужна. С примерами.
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.token}") String token;
    @Value("${bot.name}") String name;
    @Value("${bot.adminId}") long adminTelegramId;

}