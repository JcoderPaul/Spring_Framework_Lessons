package spring.oldboy.pool;

/* Создадим класс без явно указанного пустого конструктора */

import java.util.List;
import java.util.Map;

public class AdvancedConnectionPool {
    /* Зададим 4-и разноплановых поля */
    private final String username;
    private final String login;
    private final Integer poolSize;
    private final List<Object> args;
    private final Map<String, Object> properties;

    /* Конструктор, который необходимо явно конфигурировать в application.xml */

    public AdvancedConnectionPool(String username,
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
}