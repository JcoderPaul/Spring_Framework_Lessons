package spring.oldboy.lesson_23;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.oldboy.config.ApplicationConfiguration;
import spring.oldboy.pool.ConnectionPool;
import spring.oldboy.service.CompanyService;

public class AppEventDemo {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                                          new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.refresh();

            ConnectionPool connectionPool = context.getBean("pool1", ConnectionPool.class);
            System.out.println(connectionPool);

            CompanyService companyService = context.getBean(CompanyService.class);
            System.out.println(companyService.findById(1));
            System.out.println("\n----------------------------------------------------\n");
            System.out.println(context.getApplicationListeners());
        }
    }
}
