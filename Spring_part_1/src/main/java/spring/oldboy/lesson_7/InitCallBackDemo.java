package spring.oldboy.lesson_7;

/* Исследуем Initialisation CallBack */

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.pool.InitCallBackPool;

public class InitCallBackDemo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");

        /*
        Исследуемый bean имеет *.init() метод, который вызывается после вызова конструктора,
        вызова всех сеттеров см. DOC/BeansLifeCycle/LifeCycleCallbacks.jpg. Процесс явно
        видно в консоли.
        */

        System.out.println("\n------------------------------------------------------------------\n");

        InitCallBackPool initCallBackPool =
                context.getBean("intCbPool", InitCallBackPool.class);

        /*
        Закрываем наш контекст и ждем сработки метода *.destroy()
        у bean-a с id="initCallBackPool" и на экране мы видим
        все 'этапы'.
        */
        context.close();

        System.out.println("\n------------------------------------------------------------------\n");
    }
}
