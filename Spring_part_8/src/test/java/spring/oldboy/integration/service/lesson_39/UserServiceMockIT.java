package spring.oldboy.integration.service.lesson_39;

/* Пример кеширования контекста */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import spring.oldboy.pool.ConnectionPool;
import spring.oldboy.service.UserService;

@SpringBootTest
public class UserServiceMockIT {

    @Autowired
    private UserService userService;
    /*
    Тут мы вновь изменили конфигурацию контекста использовав средства Mockito
    и теперь при комплексном запуске всех тестов у нас будет создано 3-и
    независимых контекста.

    Тут можно сказать что мы сами испортили контекст, сделали его 'грязным'.
    Поскольку в случае использования одного контекста на все тесты, наш
    SPY или MOCK bean может быть использован в других тестах и повредить
    логику тестирования.
    */
    @SpyBean(name = "pool1")
    private ConnectionPool pool1;

    @Test
    void test() {
        System.out.println();
    }
}
