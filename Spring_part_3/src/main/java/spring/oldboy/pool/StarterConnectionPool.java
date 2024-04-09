package spring.oldboy.pool;
/* Примерный вид bean-a из реального приложения - неизменяем, много аннотаций */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/*
Мы можем сами именовать наш будущий bean, а можем оставить без названия,
тогда название будет задано автоматически 'starterConnectionPool' -
по названию класса с маленькой буквы. Все наши bean-ы имутабильны, поля
final, сеттеров нет.
*/
@Component("pool1")
public class StarterConnectionPool {
    private final String username;
    private final Integer poolSize;

    /* Аннотацию можно и не ставить, т.к. будет вызван доступный конструктор */
    @Autowired
    /* Используя аннотацию @Value и SPEL задаем значения из resources/application.properties */
    public StarterConnectionPool(@Value("${db.username}") String username,
                                 @Value("${db.pool.size}") Integer poolSize) {
        this.username = username;
        this.poolSize = poolSize;
    }

    @PostConstruct
    private void init() {
        System.out.println("Init connection pool");
    }

    @PreDestroy
    private void destroy() {
        System.out.println("Clean connection pool");
    }
}
