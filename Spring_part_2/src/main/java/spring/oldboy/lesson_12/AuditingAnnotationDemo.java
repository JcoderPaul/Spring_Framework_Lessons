package spring.oldboy.lesson_12;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.entity.Company;
import spring.oldboy.repository.CrudRepository;

public class AuditingAnnotationDemo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {

            CrudRepository companyRepository =
                    context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
            Company forDel = new Company(2);
            companyRepository.delete(forDel);
            /*
                Audit method: findById
                Open transaction
                findById method... from CompanyRepository
                Close transaction
                Time execution: 300908

                Optional[Company[id=1]]

                Audit method: delete
                Open transaction
                delete method...from CompanyRepository
                Close transaction
                Time execution: 173238
            */
        }
    }
}