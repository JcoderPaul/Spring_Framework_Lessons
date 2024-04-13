package spring.oldboy.config;

/*
Пусть данный файл будет предназначен для настройки других bean-ов
для работы с БД через JPA (естественно это демо-код без сложной
логики, для демонстрации работы аннотаций и только)
*/

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import spring.oldboy.config.condition.JpaCondition;

@Slf4j
@Conditional(JpaCondition.class)
@Configuration
public class JpaConfiguration {

    @PostConstruct
    void init() {
        log.info("Jpa configuration is enabled");
    }

    @Bean
    @ConfigurationProperties(prefix = "db")
    public DatabaseProperties databaseProperties() {
        return new DatabaseProperties();
    }
}
