package spring.oldboy.lesson_6;

/* Исследуем области видимости bean-ов singleton и prototype */

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.FirmRepository;
import spring.oldboy.service.FirmService;

public class PrototypeBeansDemo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");

        /*
        После запуска кода несложно заметить отличие bean-ов: firmServiceOne от firmServiceTwo
        и полное совпадение bean-ов: firmRepositoryOne и firmRepositoryTwo. Первые собраны из
        prototype, вторые имеют настройку в resources/application.xml по-умолчанию singleton.
        */

        System.out.println("\n------------------------------------------------------------------\n");
        /* У данного bean область видимости 'bean scope' - prototype см. resources/application.xml */
        FirmService firmServiceOne =
                context.getBean("firmService", FirmService.class);
        FirmRepository firmRepositoryOne =
                context.getBean("firmRepository", FirmRepository.class);


        System.out.println("firmServiceOne -> " + firmServiceOne);
        System.out.println("firmRepositoryOne -> " + firmRepositoryOne);

        System.out.println("\n------------------------------------------------------------------\n");

        FirmService firmServiceTwo =
                context.getBean("firmService", FirmService.class);
        FirmRepository firmRepositoryTwo =
                context.getBean("firmRepository", FirmRepository.class);

        System.out.println("firmServiceTwo -> " + firmServiceTwo);
        System.out.println("firmRepositoryTwo -> " + firmRepositoryTwo);
    }
}
