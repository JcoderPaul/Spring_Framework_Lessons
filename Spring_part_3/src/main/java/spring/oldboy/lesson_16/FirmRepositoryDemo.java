package spring.oldboy.lesson_16;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.CrudRepository;

public class FirmRepositoryDemo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {

            /*
            В данном примере FirmRepository.java практически ничем
            не аннотирован, однако Spring находит его в IoC контейнере,
            поскольку мы соответствующим образом настроили
            */
            CrudRepository firmRepository =
                    context.getBean("firmRepository", CrudRepository.class);
            System.out.println(firmRepository.findById(1));
            /*
                2.postProcessBeanFactory - VerifyPropertyBeanFactoryPostProcessor
                1.postProcessBeanFactory - LogBeanFactoryPostProcessor
                Init connection pool
                init company repository
                init company repository
                init company repository
                findById method...
                Optional[Company[id=1]]
                Clean connection pool
            */
        }
    }
}
