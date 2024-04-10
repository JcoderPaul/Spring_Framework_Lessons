package spring.oldboy.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import spring.oldboy.pool.StarterConnectionPool;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserRepository {

    /*
    На данном этапе если просмотреть все возможные настройки, то получим StarterConnectionPool
    bean-ов аж две шт.: StarterConnectionPool.java аннотированный как @Component("pool1") и
    метод в ApplicationConfiguration.java аннотированный как @Bean("pool2"). И в данном случае
    непонятно какой из них мы хотим получить, первый вариант вызвать одноименный bean (т.е.
    например - 'private final StarterConnectionPool pool1 (или pool2)')
    */
    private final StarterConnectionPool starterConnectionPool;

    /*
    И в конструктор передать именно его без четкого указания через аннотацию @Qualifier("pool1")
    и такой вариант все равно работал бы. Но остановимся на этом, т.е. через аннотирование параметра.
    */
    public UserRepository(@Qualifier("pool1") StarterConnectionPool starterConnectionPool) {
        this.starterConnectionPool = starterConnectionPool;
    }
}
