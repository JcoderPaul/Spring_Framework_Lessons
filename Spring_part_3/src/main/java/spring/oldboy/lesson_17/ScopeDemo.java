package spring.oldboy.lesson_17;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.CrudRepository;

public class ScopeDemo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {

            /*
            Теперь мы пометили наш FirmRepository как prototype в @Scope("prototype"),
            либо мы можем использовать константу - @Scope(BeanDefinition.SCOPE_PROTOTYPE)
            */
            CrudRepository firmRepository =
                    context.getBean("firmRepository", CrudRepository.class);
            System.out.println(firmRepository.findById(1));
            System.out.println(firmRepository);

            System.out.println("\n----------------------------------------------------\n");

            CrudRepository firmRepositoryTwo =
                    context.getBean("firmRepository", CrudRepository.class);
            System.out.println(firmRepositoryTwo.findById(2));
            System.out.println(firmRepositoryTwo);
            /*
                init company repository
                findById method...
                Optional[Company[id=1]]
                spring.oldboy.repository.FirmRepository@21e360a

                ----------------------------------------------------

                init company repository
                findById method...
                Optional[Company[id=2]]
                spring.oldboy.repository.FirmRepository@741a8937
            */
            CrudRepository companyRep =
                    context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRep.findById(3));
            System.out.println(companyRep);

            System.out.println("\n----------------------------------------------------\n");

            CrudRepository companyRepTwo =
                    context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepTwo.findById(4));
            System.out.println(companyRepTwo);
            /*
                Open transaction
                Audit method: findById
                findById method...
                Time execution: 88262
                Close transaction
                Optional[Company[id=3]]
                Open transaction
                Audit method: toString
                Time execution: 30379
                Close transaction
                spring.oldboy.repository.CompanyRepository@e15b7e8

                ----------------------------------------------------

                Open transaction
                Audit method: findById
                findById method...
                Time execution: 50905
                Close transaction
                Optional[Company[id=4]]
                Open transaction
                Audit method: toString
                Time execution: 25863
                Close transaction
                spring.oldboy.repository.CompanyRepository@e15b7e8

            И мы видим наглядное различие, т.к. CompanyRepository - singleton, а
            FirmRepository - prototype.
            */

        }
    }
}
