package spring.oldboy.lesson_2;

/*
Внедрение простых зависимостей в bean-ы через параметры
конструктора с использованием resources/application.xml
*/

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.pool.AdvancedConnectionPool;
import spring.oldboy.pool.StarterConnectionPool;

import java.lang.reflect.Field;

public class ApplicationContextDemo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");
        /*
        Если в resources/application.xml поместить описание bean вида:
        <bean class="spring.oldboy.pool.ConnectionPool"/>
        то исходя из схемы: clazz -> String -> Map<String, Object>, bean будет создан, а
        извлечь наш бин созданный при помощи default конструктора, мы сможем стандартным
        методом *.getBean(ConnectionPool.class).

        Ключом доступа к нему в ассоциативном массиве будет (см. debug) -
        spring.oldboy.pool.ConnectionPool#0, если мы сами специально не задали имя ключа,
        следующий не проименованный бин, но созданный по тому же принципу получит ключ
        spring.oldboy.pool.ConnectionPool#1 и т.д.

        В обоих случаях они будут синглтонами. Однако тут возникает проблемка, если извлекать
        наши одинаковые bean из контейнера методом *.getBean(ConnectionPool.class) будет
        брошено исключение, т.к. непонятно какой из них нам нужен. Класс не основной
        идентификатор - ConnectionPool.class.

        Поскольку ключ задаем не мы, то лучший вариант при использовании метода *.getBean()
        использовать 'name' или 'id' нашего bean, которое мы можем задать в application.xml.

        Однако, и тут есть тонкий момент, метод *.getBean("pool1"), т.е. получение bean по
        'name', вернет Object, а нам бы хотелось - ConnectionPool.

        И тогда можно применить:
        */
        StarterConnectionPool starterConnectionPool =
                context.getBean("pool1", StarterConnectionPool.class);
        System.out.println(starterConnectionPool);

        System.out.println("\n------------------------------------------------------------------\n");
        /* Наиболее наглядно лучше изучать формирование данного Bean-a через debug */
        AdvancedConnectionPool advancedConnectionPool=
                context.getBean("pool3", AdvancedConnectionPool.class);
        Field[] prnFields = advancedConnectionPool.getClass().getDeclaredFields();
        for (Field prn: prnFields) {
            System.out.println(prn);
        }

        System.out.println("\n------------------------------------------------------------------\n");
        String strDriver = context.getBean("driver", String.class);
        System.out.println(strDriver);

    }
}
