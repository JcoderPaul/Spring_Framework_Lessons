package spring.oldboy.lesson_19;

/* Демонстрация парной работы application.xml и ApplicationConfiguration.java */

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.CrudRepository;

public class JavaConfigAndXmlDemo {
    public static void main(String[] args) {

        /* Получаем контекст из *.xml, а application.properties из ApplicationConfiguration.java */
        try(ClassPathXmlApplicationContext myContext =
                    new ClassPathXmlApplicationContext("application.xml")){
        /* Bean с именем id="companyRepository" есть только в application.xml */
        CrudRepository companyRep =
                myContext.getBean("companyRepository", CrudRepository.class);
        System.out.println(companyRep);

        }
    }
}
