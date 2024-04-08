package spring.oldboy.pool;
/*
В данном классе применяется инициализирующий
обратный вызов - Initialisation CallBack
*/
import org.springframework.beans.factory.InitializingBean;

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

    /* Наш init метод см. настройки bean в application.xml */
    private void init() {
        /* Некая логика нашего init - обратного вызова */
        System.out.println("Init connection pool");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("AfterProperties set");
    }

    /*
    Наш *.destroy() метод см. настройки bean в application.xml
    см. DOC/BeansLifeCycle/LifeCycleCallbacks.jpg
    */
    private void destroy() {
        System.out.println("Clean connection pool");
    }
}