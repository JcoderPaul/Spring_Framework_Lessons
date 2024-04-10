package spring.oldboy.lesson_21;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.oldboy.config.ApplicationConfiguration;
import spring.oldboy.repository.UserRepository;

public class BeanJavaConfigDemo {
    public static void main(String[] args) {

        /* Получаем контекст из *.xml */
        try(AnnotationConfigApplicationContext myContext =
                    new AnnotationConfigApplicationContext(ApplicationConfiguration.class)){

        /*
        Bean с именем id="companyRepository" есть только в application.xml, но теперь мы не
        обращаемся к файлу *.xml, и если вдруг затребуем bean с таким именем получим:

        Exception in thread "main" org.springframework.beans.factory.NoSuchBeanDefinitionException:
        No bean named 'companyRepository' available

        Зато CompanyRepository.java аннотирован как @Repository("companyRep") и вот к нему при
        текущих настройках доступ у нас есть (т.е. он находится в IoC контейнере)
        */

        UserRepository userRep =
                myContext.getBean("userRepository", UserRepository.class);
        System.out.println(userRep);

        }
    }
}
