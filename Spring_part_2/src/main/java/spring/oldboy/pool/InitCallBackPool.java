package spring.oldboy.pool;

/*
В данном классе применяется инициализирующий обратный вызов - Initialisation CallBack,
но в отличие от Spring_part_1, сокращаем resources/application.xml и применяем аннотации:
@PostConstruct и @PreDestroy.
*/

import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

public class InitCallBackPool implements InitializingBean {
    /* Зададим 4-и разноплановых поля */
    private final String username;
    private final String login;
    private final Integer poolSize;
    private final List<Object> args;
    private final Map<String, Object> properties;

    /* Конструктор, который необходимо явно конфигурировать в application.xml */

    public InitCallBackPool(String username,
                            String login,
                            Integer poolSize,
                            List<Object> args,
                            Map<String, Object> properties) {
        this.username = username;
        this.login = login;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }

    /* Наш init метод, теперь указульку на его функционал мы проводим через аннотацию */
    @PostConstruct
    private void init() {
        /* Некая логика нашего init - обратного вызова */
        System.out.println("Init connection pool");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AfterProperties set");
    }

    /*
    Наш *.destroy() метод так же аннотируем, но он продолжает выполнять свои функции, как и его собрат из
    Spring_part_1\src\main\java\spring\oldboy\pool\InitCallBackPool.java, который использует настройки и
    параметры прописанные в application.xml. Аннотирование один из вариантов передачи bean definition, см.
    Spring_part_1/DOC/BeansLifeCycle/LifeCycleCallbacks.jpg (Bean Definition - описание bean-а, определение
    его чертежных свойств, из которых он и будет построен для контекст контейнера)
    */
    @PreDestroy
    private void destroy() {
        System.out.println("Clean connection pool");
    }
}