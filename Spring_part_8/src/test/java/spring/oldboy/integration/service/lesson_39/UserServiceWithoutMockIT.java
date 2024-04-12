package spring.oldboy.integration.service.lesson_39;

/* Пример кеширования контекста */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.integration.annotation.IT;
import spring.oldboy.pool.ConnectionPool;
import spring.oldboy.service.UserService;

@IT
@RequiredArgsConstructor
public class UserServiceWithoutMockIT {

    private final UserService userService;
    private final ConnectionPool pool1;

    @Test
    void test() {
        System.out.println();
    }
}
