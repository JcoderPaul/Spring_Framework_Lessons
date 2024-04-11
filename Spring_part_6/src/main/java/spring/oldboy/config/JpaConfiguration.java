package spring.oldboy.config;
/*
Пусть данный файл будет предназначен для настройки
других bean-ов для работы с БД через JPA (естественно
это демо-код без сложной логики, для демонстрации
работы аннотаций и только)
*/
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import spring.oldboy.config.condition.JpaCondition;

import javax.annotation.PostConstruct;

/*
Как уже писалось в DOC/ConditionalAnnotationSpring.txt в
данную аннотацию можно передать несколько классов содержащих
условия для размещения данного конфигурационного bean-a в
контекст контейнере.
*/
@Conditional(JpaCondition.class)
/* Помечаем класс конфигурации */
@Configuration
public class JpaConfiguration {
    /*
    Данная аннотация и сам метод нужны нам, что бы увидеть
    добавляется или нет данный конфигурационный bean в
    контекст при исполнении (не исполнении) определенных
    условий заданных в JpaCondition.java.
    */
    @PostConstruct
    void init() {
        System.out.println("Jpa configuration is enabled");
    }

    /*
    Нам нужно получить bean из класса в DatabaseProperties.java,
    ID данного bean-a будет название метода 'databaseProperties'
    */
    @Bean
    /*
    Для того чтобы Spring Boot провел соответствие между application.yml
    и классом DatabaseProperties используется текущая аннотация. Эта
    аннотация именно от Spring Boot, просто Spring не поддерживает ее.

    В качестве параметра передаем префикс из application.yml - это 'db',
    именно уникальность префикса является ключевым моментом.

    Попал ли этот bean в контекст можно проверить через FirstSpringAppRunner.java
    */
    @ConfigurationProperties(prefix = "db")
    public DatabaseProperties databaseProperties() {
        return new DatabaseProperties();
    }
}
