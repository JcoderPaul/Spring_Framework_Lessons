package spring.oldboy.repository;

/*
Применение аннотации @Autowired, @Qualifier, @Value
над внедряемыми полем и коллекциями
*/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import spring.oldboy.pool.StarterConnectionPool;

import java.util.List;

public class StockAllInjectRepository {
    /*
    Аннотация @Autowired в Spring Framework используется для автоматического
    связывания компонентов bean-a между собой. Она позволяет автоматически
    настраивать свойства bean-a и методы, упрощая тем самым процесс инъекции
    зависимостей.

    В нашем application.xml, как минимум два однотипных bean-a:

    <bean id="pool2" name="poolchic" class="spring.oldboy.pool.StarterConnectionPool"/>
    <bean id="poolStarter" name="poolGate" class="spring.oldboy.pool.StarterConnectionPool"/>

    Чтобы решить проблему неопределенности при выборе bean-a, можно использовать
    аннотацию @Qualifier. Она позволяет указать, какой bean следует использовать
    при внедрении. В этом примере, Spring внедрит бин с именем «pool2».

    То что мы пытались сделать аннотацией @InjectBean делают:
    - аннотация @Resource(name = "poolStarter") из пакета javax.annotation;
    - @Autowired и @Qualifier("poolStarter") из org.springframework.beans.factory.annotation
    */

    /*
    Во всех случаях, если у нас в Spring контейнере хранятся более одного однородного
    bean-a, рекомендуется их именовать и явно прописывать имя используемого для внедрения,
    например: @Resource(name = "poolStarter")
    */
    @Autowired
    /* Явно указываем имя внедряемого bean-a */
    @Qualifier("poolStarter")
    private StarterConnectionPool starterConnectionPool;

    /* Мы можем внедрять целые коллекции и поля, естественно над параметрами конструктора */
    @Autowired
    private List<StarterConnectionPool> pools;
    /* Использовать при внедрении EL или SPEL */
    @Value("${db.pool.size}")
    private Integer poolSize;

    /* Для просмотра */
    public StarterConnectionPool getStarterConnectionPool() {
        return starterConnectionPool;
    }

    public List<StarterConnectionPool> getPools() {
        return pools;
    }

    public Integer getPoolSize() {
        return poolSize;
    }
}