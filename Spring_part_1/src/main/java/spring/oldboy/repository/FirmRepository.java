package spring.oldboy.repository;

import spring.oldboy.pool.AdvancedConnectionPool;

public class FirmRepository {

    private final AdvancedConnectionPool advancedConnectionPool;
    /* Конструктор приватный */

    public FirmRepository(AdvancedConnectionPool advancedConnectionPool) {
        this.advancedConnectionPool = advancedConnectionPool;
    }

    /* Метод вызова объектов данного класса */
    public static FirmRepository of(AdvancedConnectionPool connectionPool) {
        return new FirmRepository(connectionPool);
    }
    /* Для вывода на экран */
    public AdvancedConnectionPool getAdvancedConnectionPool() {
        return advancedConnectionPool;
    }
}
