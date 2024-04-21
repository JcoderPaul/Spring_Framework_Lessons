package spring.oldboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

/*
Аннотация указанная над 'точкой входа' в Spring приложение
должна быть единственной на все наше приложение.
*/
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringAppRunner {

    public static void main(String[] args) {

        ConfigurableApplicationContext context =
                SpringApplication.run(SpringAppRunner.class, args);

    }
}
