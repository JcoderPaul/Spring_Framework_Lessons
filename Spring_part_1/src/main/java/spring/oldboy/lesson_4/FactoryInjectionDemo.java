package spring.oldboy.lesson_4;

/*
Внедрение простых зависимостей в bean-ы через параметры
конструктора с использованием resources/application.xml
*/

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.pool.AdvancedConnectionPool;
import spring.oldboy.repository.FirmRepository;

import java.lang.reflect.Field;

public class FactoryInjectionDemo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");

        System.out.println("\n------------------------------------------------------------------\n");
        /*
        Нам уже не надо извлекать bean от которого зависит следующий bean, все
        уже прописано в resources/application.xml, но мы извлечем его и посмотрим,
        как он выглядит (один pool на все):
        */
        AdvancedConnectionPool advancedConnectionPool =
                context.getBean("pool3", AdvancedConnectionPool.class);
        System.out.println(advancedConnectionPool);


        System.out.println("\n------------------------------------------------------------------\n");
        /*
        Данный bean зависит от starterConnectionPool, но тут мы явно не указывали этой зависимости,
        все прописано в resources/application.xml. Все поля проинициализированные так же передаются
        в bean FirmRepository.
        */
        FirmRepository firmRepository =
                context.getBean("firmRepository", FirmRepository.class);

        System.out.println(firmRepository.getAdvancedConnectionPool());

        Field[] prnFld = firmRepository.
                                getAdvancedConnectionPool().
                                getClass().
                                getDeclaredFields();
        for (Field prn: prnFld) {
            System.out.println(prn);
        }

        System.out.println("\n------------------------------------------------------------------\n");

    }
}
