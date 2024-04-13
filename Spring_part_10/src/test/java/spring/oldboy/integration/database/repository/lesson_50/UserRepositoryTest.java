package spring.oldboy.integration.database.repository.lesson_50;

/* Lesson 50 - 51 */

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.entity.Role;
import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.integration.annotation.IT;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* Это интеграционный тест и мы используем нашу аннотацию */
@IT
/*
@RequiredArgsConstructor - позволит получить конструктор с параметром для каждого поля,
но эти параметры потребуют специальной обработки.  Все неинициализированные final поля
получают параметр, также как все остальные поля, помеченные @NonNull, которые не
инициализированные при объявлении.
*/
@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

    /* Lesson 50 - тестируем метод */
    @Test
    void checkQueriesMethodTest() {
        /*
        Мы заранее знаем, что пользователей с такими параметрами
        у нас 3-и шт., немного 'подшаманим' наш тест по утверждению.
        */
        var users = userRepository.findAllBy("a", "ov");
        Assertions.assertThat(users).hasSize(3);
    }

    /* Lesson 51 - тестируем метод */
    @Test
    void checkUpdateMethodTest() {

        var resultCount = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, resultCount);

    }

}