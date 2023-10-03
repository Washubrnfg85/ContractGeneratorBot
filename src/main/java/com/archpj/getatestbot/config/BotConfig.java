package com.archpj.getatestbot.config;

import com.archpj.getatestbot.services.InMemorySessionStore;
import com.archpj.getatestbot.services.RedisSessionStore;
import com.archpj.getatestbot.services.SessionStore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("application.properties")
@Configuration
public class BotConfig {

    @Bean
    SessionStore sessionStore() {
        return new InMemorySessionStore(); // here you can choose an implementation
//        return new RedisSessionStore();
    }

}