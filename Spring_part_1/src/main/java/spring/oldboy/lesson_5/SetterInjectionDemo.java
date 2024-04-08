package spring.oldboy.lesson_5;

/*
Внедрение простых зависимостей в bean-ы через параметры
конструктора с использованием resources/application.xml
*/

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.pool.SetterInConnectionPool;

import java.util.Map;

public class SetterInjectionDemo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");

        System.out.println("\n------------------------------------------------------------------\n");
        /*
        У данного bean параметры Map заданные через конструктор, как null,
        все равно установлены через свойства <properties>.
        */
        SetterInConnectionPool setterInConnectionPool =
                context.getBean("poolSetIn", SetterInConnectionPool.class);

        Map<String, Object> prnProperties = setterInConnectionPool.getProperties();
        for (String keys : prnProperties.keySet())
        {
            System.out.println(keys + " - " + prnProperties.get(keys));
        }

        /*
        ------------------------------------------------------------------

        url postgresurl
        password 123
        driver PostgresDriver

        ------------------------------------------------------------------
        */
        System.out.println("\n------------------------------------------------------------------\n");
    }
}
