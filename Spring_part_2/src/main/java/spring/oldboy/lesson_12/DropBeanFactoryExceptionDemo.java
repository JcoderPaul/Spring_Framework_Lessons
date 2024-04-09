package spring.oldboy.lesson_12;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.CrudRepository;


public class DropBeanFactoryExceptionDemo {

    public static void main(String[] args) {

        /* Для демонстрации выберем другой *.xml файл */
        try (var context = new ClassPathXmlApplicationContext("application_drop_bean_factory.xml")) {

            CrudRepository firmRepository = context.getBean("firmRepository", CrudRepository.class);
            System.out.println(firmRepository.findById(1));

            /*
            Exception in thread "main" org.springframework.beans.factory.BeanNotOfRequiredTypeException:
            Bean named 'firmRepository' is expected to be of type 'spring.oldboy.repository.FirmRepository'
            but was actually of type 'jdk.proxy2.$Proxy12'

            Однако тут есть интересный момент, мы искали 'FirmRepository', а нашли 'jdk.proxy2.$Proxy12'.
            В *.postProcessBeforeInitialization() мы использовали Proxy, т.е. динамическое создание bean-a,
            а он точно не FirmRepository, но создан, как и последний, на основании интерфейса:
            - bean.getClass().getInterfaces().

            У нас это CrudRepository, вот к нему мы и можем обратиться, чтобы не получать исключения:
            ************************************************************************************************

            CrudRepository firmRepository = context.getBean("firmRepository", CrudRepository.class);

            ************************************************************************************************

            И все же на этапе пред-инициализации не принято заниматься подменой одних bean-ов другими.
            */
        }
    }
}
