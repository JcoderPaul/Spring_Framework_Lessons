package spring.oldboy.lesson_13;
/*
Пример работы bean-ов в которые процесс внедрения bean-ов идет при помощи аннотаций.
Смотреть настройки аннотаций в:
 - StockRepository.java;
 - StockSetRepository.java;
 - StockAllInjectRepository.java;
*/
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.StockAllInjectRepository;
import spring.oldboy.repository.StockRepository;
import spring.oldboy.repository.StockSetRepository;

public class AutowiredQualifierDemo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {

            System.out.println("\n-------------------------------------------------------------\n");
            /* Берем bean с аннотированным полем и смотрим его StarterConnectionPool */
            StockRepository stockRepository =
                    context.getBean("stockRepository", StockRepository.class);
            System.out.println(stockRepository.getStarterConnectionPool());

            System.out.println("\n-------------------------------------------------------------\n");
            /* Берем bean с аннотированием сеттера и смотрим его StarterConnectionPool*/
            StockSetRepository stockSetRepository =
                    context.getBean("stockSetRepository", StockSetRepository.class);
            System.out.println(stockSetRepository.getStarterConnectionPool());


            System.out.println("\n-------------------------------------------------------------\n");
            StockAllInjectRepository stockAllInjectRepository =
                    context.getBean("coolStockRepository", StockAllInjectRepository.class);
            System.out.println(stockAllInjectRepository.getStarterConnectionPool());
            System.out.println(stockAllInjectRepository.getPoolSize());
            stockAllInjectRepository.getPools().forEach(System.out::println);

        }
    }
}