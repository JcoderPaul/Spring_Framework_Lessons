package spring.oldboy;

/* Lesson 25 - создаем наше первое SpringBoot приложение. */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringProperties;
import spring.oldboy.config.DatabaseProperties;
import spring.oldboy.config.ImmutableDatabaseProperties;
import spring.oldboy.pool.ConnectionPool;

import java.util.Map;

/*
Аннотация указанная над 'точкой входа' в SpringBoot приложение
должна быть единственной на все наше приложение.
*/
@SpringBootApplication
@ConfigurationPropertiesScan
public class FirstSpringAppRunner {

    public static void main(String[] args) {

        /*
        Тут создается контекст приложения (контейнер bean-ов), но
        не как в прошлых приложениях, когда мы 'сами создавали'
        контекст. В данном случае всю работу делает метод *.run.
        */
        ConfigurableApplicationContext context =
                SpringApplication.run(FirstSpringAppRunner.class, args);

        /* Можем получить наши bean definition */
        String[] beanDefPrn = context.getBeanDefinitionNames();
        for (String prn: beanDefPrn) {
            System.out.println(prn);
        }
        /* Посчитаем их */
        System.out.println(context.getBeanDefinitionCount());

        System.out.println("\n---------------------------------------------------------\n");

        /* Lesson 28 - Получим данные из resources/spring.properties */
        String sp = SpringProperties.getProperty("test.message");
        System.out.println(sp);

        System.out.println("\n---------------------------------------------------------\n");

        /*
        В файле application.yml параметр db.pool.size = 12, однако,
        в файле application-qa.yml, более приоритетном, параметр
        переназначен на db.pool.size = 24, что мы и видим в консоли
        или debug-e.
        */
        ConnectionPool poolToGetData = context.getBean("pool2", ConnectionPool.class);
        System.out.println(poolToGetData.getPoolSize());
        System.out.println(poolToGetData.getUsername());

        System.out.println("\n---------------------------------------------------------\n");

        /* Попробуем получить доступ к bean-у свойств БД */
        DatabaseProperties databaseProperties = context.getBean(DatabaseProperties.class);
        /* Выведем на экран некоторые из них */
        System.out.println(databaseProperties.getUsername());
        Map<String, Object> propertiesPrn = databaseProperties.getProperties();
        propertiesPrn.forEach((k, v) -> {
            System.out.println(k + " - " + v);
        });

        System.out.println("\n---------------------------------------------------------\n");

        /* Попробуем получить доступ к bean-у свойств БД */
        ImmutableDatabaseProperties immutableDBProperties =
                context.getBean(ImmutableDatabaseProperties.class);
        /* Выведем на экран некоторые из них */
        System.out.println(immutableDBProperties.username());
        Map<String, Object> propPrn = immutableDBProperties.properties();
        propPrn.forEach((k, v) -> {
            System.out.println(k + " - " + v);
        });
    }
}
