package spring.oldboy.lesson_12;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.CrudRepository;

public class MyOwnTransactionDemo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {

            CrudRepository companyRepository =
                    context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
        }
    }
}
