package spring.oldboy.integration.service.lesson_40;

/* Пример грязного контекста */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import spring.oldboy.integration.annotation.IT;
import spring.oldboy.pool.ConnectionPool;
import spring.oldboy.service.UserService;

@IT
@RequiredArgsConstructor
/*
Аннотация вынуждает перед каждым тестовым методом создавать новый контекст.

Если запустить данный класс, то в консоли мы увидим, как под каждый тест,
создается новый контекст. Данной аннотацией также можно помечать и отельные
тестовые методы.
*/
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceWithDirtiesContextIT {

    private final UserService userService;
    private final ConnectionPool pool1;

    @Test
    void testOne() {
        System.out.println();
    }

    @Test
    void testTwo() {
        System.out.println();
    }

    @Test
    void testThree() {
        System.out.println();
    }
}
