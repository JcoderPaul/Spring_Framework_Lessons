package spring.oldboy.integration.database.repository.lesson_57;

/* Lesson 57 - Тесты на кастомный репозиторий и его метод */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.dto.UserFilterDto;
import spring.oldboy.integration.annotation.IT;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/* Это интеграционный тест и мы используем нашу аннотацию */
@IT
/*
@RequiredArgsConstructor - позволит получить конструктор с параметром для каждого поля,
но эти параметры потребуют специальной обработки.  Все неинициализированные final поля
получают параметр, также как все остальные поля, помеченные @NonNull, которые не
инициализированные при объявлении.
*/
@RequiredArgsConstructor
class CustomUserRepositoryTest {

    private final UserRepository userRepository;

    @Test
    void checkCustomImplementationRepositoryTest() {
        /* Создаем новый фильтр и передаем 2-а параметра: фильтруем по фамилии и дате рождения */
        UserFilterDto filter = new UserFilterDto(null,
                                                 "%ov%",
                                                         LocalDate.now());
        /* Применяем наш фильтрующий метод из кастомного репозитория */
        List<User> users = userRepository.findAllByFilter(filter);
        /* Проверяем утверждение, что отфильтрованных User-ов будет 4 */
        assertThat(users).hasSize(4);

        users.forEach(System.out::println);
    }

}