package spring.oldboy.repository;

/* Применение аннотации @Autowired над сеттером поля внедрения */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import spring.oldboy.pool.StarterConnectionPool;

public class StockSetRepository {
    /*
    Аннотация @Autowired в Spring Framework используется для автоматического
    связывания компонентов bean-a между собой. Она позволяет автоматически
    настраивать свойства bean-a и методы, упрощая тем самым процесс инъекции
    зависимостей.

    Во всех случаях, если у нас в Spring контейнере хранятся более одного однородного
    bean-a, рекомендуется их именовать и явно прописывать имя используемого для внедрения.

    Если в StockRepository.java мы использовали @Qualifier("poolStarter") для явного
    указания имени внедряемого bean-a, то в данном примере мы сразу используем имя из
    application.xml:

    <bean id="poolStarter" name="poolGate" class="spring.oldboy.pool.StarterConnectionPool"/>

    в качестве названия поля. Хотя данный пример некорректен.
    */
    @Qualifier
    private StarterConnectionPool poolStarter;

    /* Использование аннотации @Autowired над сеттером */
    @Autowired
    public void setPoolStarter(StarterConnectionPool poolStarter) {
        this.poolStarter = poolStarter;
    }
    /* Для просмотра */
    public StarterConnectionPool getStarterConnectionPool() {
        return poolStarter;
    }
}