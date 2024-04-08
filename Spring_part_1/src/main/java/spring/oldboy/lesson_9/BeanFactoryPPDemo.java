package spring.oldboy.lesson_9;

/*
Исследуем BeanFactoryPostProcessor и порядок
запуска экземпляров BeanFactoryPostProcessor
*/

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.pool.InitCallBackPool;

public class BeanFactoryPPDemo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");

        System.out.println("\n------------------------------------------------------------------\n");

        InitCallBackPool initCallBackPool =
                context.getBean("intCbPool", InitCallBackPool.class);

        context.close();

        System.out.println("\n------------------------------------------------------------------\n");
    }
}
