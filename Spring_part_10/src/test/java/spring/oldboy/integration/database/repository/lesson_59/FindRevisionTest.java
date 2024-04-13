package spring.oldboy.integration.database.repository.lesson_59;

/*
Lesson 59 - Добавим аннотацию над тестовым методом для фиксации
            изменений с использованием функционала Envers.
*/

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.history.Revision;
import org.springframework.test.annotation.Commit;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.integration.annotation.IT;

import java.util.Optional;

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
class FindRevisionTest {

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

    /*
    Lesson 59:
    В предыдущем методе мы внесли изменения в БД по таблице users,
    тут мы пытаемся извлечь последние изменения и вывести их на экран
    */
    @Test
    void checkFindLastRevisionTest(){
        /* Извлекаем последнюю ревизию User с ID - 1 */
        Optional<Revision<Integer, User>> userRevision = userRepository.findLastChangeRevision(1L);
        /* Убеждаемся, что таковая есть */
        assertThat(userRevision.isPresent());
        /* Выводим на экран */
        userRevision.ifPresent(revision -> System.out.println(revision));
    }

}