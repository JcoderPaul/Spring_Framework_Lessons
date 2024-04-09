package spring.oldboy.pool;

/*
Основная проблема инъекции через *.set методы заключается в том, что мы
не можем создать имутабельный объект. Конечно мы теперь можем миксовать
метод инъекции зависимостей.
*/

import java.util.List;
import java.util.Map;

public class SetterInConnectionPool {
    /* Зададим 4-и разноплановых поля */
    private final String username;
    private final String login;
    private final Integer poolSize;
    private final List<Object> args;
    private Map<String, Object> properties;

    /* Конструктор, который необходимо явно конфигурировать в application.xml */

    public SetterInConnectionPool(String username,
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
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}