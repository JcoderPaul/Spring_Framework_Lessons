package spring.oldboy.lesson_14;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.pool.StarterConnectionPool;
import spring.oldboy.repository.CrudRepository;

public class AnnotationComponentDemo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {
            var connectionPool =
                    context.getBean("pool1", StarterConnectionPool.class);
            System.out.println(connectionPool);

            var companyRepository =
                    context.getBean("companyRepository", CrudRepository.class);
            System.out.println(companyRepository.findById(1));
            /*
                2.postProcessBeanFactory - VerifyPropertyBeanFactoryPostProcessor
                1.postProcessBeanFactory - LogBeanFactoryPostProcessor
                Init connection pool
                init company repository
                init company repository
                spring.oldboy.pool.StarterConnectionPool@fba92d3
                Open transaction
                Audit method: findById
                findById method...
                Time execution: 178988
                Close transaction
                Optional[Company[id=1]]
                Clean connection pool
            */
        }
    }
}
