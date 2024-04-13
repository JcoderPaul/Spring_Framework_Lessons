package spring.oldboy.integration.database.repository.lesson_55;

/* Lesson 55 - Тесты на методы с применением аннотаций @Lock и @QueryHints */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.SixthUserRepository;
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
class SixthUserRepositoryTest {

    private final SixthUserRepository sixthUserRepository;

    @Test
    void checkDynamicSortTest() {
        /*
        Тут мы получаем и используем вложенный класс TypedSort и оперируем сущностью User и ее
        get-методами. Мы получаем типобезопасный объект TypedSort через который можем делать
        нужную нам сортировку используя методы подкласса Sort.

        Типобезопасность - свойство языка программирования, характеризующее безопасность и
        надёжность в применении его системы типов. Система типов называется безопасной или
        надёжной, если в программах, прошедших проверку согласования типов, исключена возможность
        возникновения ошибок согласования типов во время выполнения.
        */
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        /* Тут мы обращаемся к гетерам сущности User */
        Sort sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname));

        List<User> allUsers = sixthUserRepository.findTop3ByBirthDateBefore(LocalDate.now(), sort);
        assertThat(allUsers).hasSize(3);
    }

    @Test
    void checkDynamicSortWithHintsTest() {
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        Sort sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname));

        List<User> allUsers = sixthUserRepository.findTop2ByBirthDateBefore(LocalDate.now(), sort);
        assertThat(allUsers).hasSize(2);
    }
}