package spring.oldboy.integration.database.repository.lesson_58;

/*
Lesson 57 - Тесты на кастомный репозиторий и его метод.
Lesson 59 - Добавим аннотацию над тестовым методом для фиксации
            изменений с использованием функционала Envers.
*/

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.integration.annotation.IT;

/* Это интеграционный тест и мы используем нашу аннотацию */
@IT
/*
@RequiredArgsConstructor - позволит получить конструктор с параметром для каждого поля,
но эти параметры потребуют специальной обработки.  Все неинициализированные final поля
получают параметр, также как все остальные поля, помеченные @NonNull, которые не
инициализированные при объявлении.
*/
@RequiredArgsConstructor
class UserAuditingTest {

    private final UserRepository userRepository;

    @Test
    /*
    Lesson 59:
    Для того чтобы проверить работу механизма Hibernate Envers
    и зафиксировать изменения внесенные данным тестовым методом
    в запись таблицы users нам нужна аннотация @Commit.
    */
    @Commit
    void checkUpdateAuditing() {
        User userForUpdate = userRepository.findById(1L).get();
        userForUpdate.setBirthDate(userForUpdate.getBirthDate().plusYears(1L));
        userRepository.flush();
    }

}