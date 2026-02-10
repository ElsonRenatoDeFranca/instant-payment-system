package com.example.instantpaymentsystem;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfigTest {

    private static final String CONNECTION_STRING = "mongodb://%s:%s@%s:%d";

    @Bean
    @Primary
    public MongoTemplate mongoTemplate(@Value("${spring.data.mongodb.port}") int mongoPort,
                                       @Value("${spring.data.mongodb.host}") String host,
                                       @Value("${spring.data.mongodb.database}") String dataBaseName,
                                       @Value("${spring.data.mongodb.username}") String username,
                                       @Value("${spring.data.mongodb.password}") String password) {
        return new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, username, password, host, mongoPort)), dataBaseName);
    }
}
