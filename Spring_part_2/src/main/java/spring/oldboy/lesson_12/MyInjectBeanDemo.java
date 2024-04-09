package spring.oldboy.lesson_12;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.UserRepository;

public class MyInjectBeanDemo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {

            UserRepository userRepository =
                    context.getBean("userRepository", UserRepository.class);
            /*
            В случае неудачи, мы, скорее всего, поймали бы что-то вроде:
            'Exception encountered during context initialization', а
            поскольку все 'ок', и в консоли все видно - значит, нужные
            bean-ы собраны и внедрены там где надо. (см. прогон в debug-е)
            */
            System.out.println("\n" + userRepository + "\n");

        }
    }
}
