package spring.oldboy.lesson_18;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.oldboy.repository.CrudRepository;
import spring.oldboy.repository.StockRepository;

public class JSR330Demo {
    public static void main(String[] args) {

        try (var context = new ClassPathXmlApplicationContext("application.xml")) {


            StockRepository stockRepository =
                    context.getBean("stockRep", StockRepository.class);
            System.out.println(stockRepository.getPools());
            System.out.println(stockRepository.getPoolSize());
        }
    }
}
