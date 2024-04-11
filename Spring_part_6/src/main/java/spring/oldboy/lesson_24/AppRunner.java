package spring.oldboy.lesson_24;

/*
Исследуем работу аннотации @Conditional. В нашем случае, JpaConfiguration.java - аннотирован
этой аннотацией и зависит от исполнения условий прописанных в JpaCondition.java. Если драйвер
PostgreSQL будет загружен и доступен, хотя бы через build.gradle:
implementation 'org.postgresql:postgresql:42.3.1', то bean конфигурации JPA будет помещен в
контекст и мы увидим соответствующие сообщения в терминале (либо в context и metadata в debug-е),
нет - обратный эффект.
*/
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.oldboy.config.ApplicationConfiguration;
import spring.oldboy.pool.ConnectionPool;
import spring.oldboy.service.CompanyService;

public class AppRunner {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext()) {
            context.register(ApplicationConfiguration.class);
            context.refresh();

            CompanyService companyService =
                    context.getBean(CompanyService.class);
            System.out.println(companyService.findById(1));

            ConnectionPool poolToGetData = context.getBean("pool2", ConnectionPool.class);
            System.out.println(poolToGetData.getPoolSize());
            System.out.println(poolToGetData.getUsername());
        }
    }
}
