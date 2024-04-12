package spring.oldboy.integration.service.lesson_39;

/* Пример кеширования контекста */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.oldboy.service.UserService;

/*
Создадим конфигурацию отличную от той, что применяется в различных версиях CompanyService ... IT.
Если запустить все интеграционные тесты разом, то получим, что запускается как минимум 2-а контекста -
это видно в консоли. Тут не переиспользуется один и тот же, уже созданный, контекст.

Все просто у нас разные конфигурации ApplucationContext-ов, хотя бы в
CompanyServiceСonstructorInjection.java и в UserServiceIT.java см. аннотации
над выбранными классами.

Естественно, мы можем использовать нашу самописную аннотацию @IT вместо @SpringBootTest, и тогда мы
получим один контекст на все тесты аннотированные @IT и другой контекст аннотированный только
@SpringBootTest. Так же нужно помнить, что у нас в тестах используется аннотация @ActiveProfiles, что
тоже может привести к созданию своего контекста см. 'Lesson 39 - Кэширование ApplicationContext' из
ReadMe.md
*/
@SpringBootTest
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Test
    void test() {
        System.out.println();
    }
}
