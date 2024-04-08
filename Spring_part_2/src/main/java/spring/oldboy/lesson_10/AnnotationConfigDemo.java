package spring.oldboy.lesson_10;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.pool.InitCallBackPool;

public class AnnotationConfigDemo {
    public static void main(String[] args) {
        /*
        Вспоминаем: для изучения IoC контейнера и взаимодействия bean-ов нам нужен
        ApplicationContext, указываем путь к файлу настроек bean-ов или Bean Definitions.
        Контекст, как и любой массив - ресурс, который желательно освободить после
        использования. Поэтому либо используем *.close() после того, как необходимость в
        нем отпадает, либо блок try-with-resources.
        */
        try (ClassPathXmlApplicationContext context =
                     new ClassPathXmlApplicationContext("application.xml")) {
            /*
            Для наглядности вызовем наш bean содержащий исследуемые аннотации: @PostConstruct и
            @PreDestroy. Нужно помнить, что все singleton bean-ы прописанные в application.xml
            помещаются в IoC контейнер (Bean контейнер). И если у них есть некие 'демонстрационные
            методы' вся их текстовая 'учебно-индикативная' информация в процессе прогона по
            цепочке жизненного цикла будет активирована и выведена на экран при запуске данного
            микро-приложения (см. Spring_part_1\DOC\BeansLifeCycle.jpg).

            Поскольку у нас, как минимум два bean-a класса InitCallBackPool, то и на экране мы
            увидим:
            */
            InitCallBackPool initCallBackPool =
                    context.getBean("initCallBackPool", InitCallBackPool.class);
            System.out.println(initCallBackPool);
            /*
            Init connection pool
            AfterProperties set
            Init connection pool
            AfterProperties set
            spring.oldboy.pool.InitCallBackPool@2a54a73f
            Clean connection pool
            Clean connection pool
            */
        }
    }
}
