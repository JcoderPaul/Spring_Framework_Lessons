package spring.oldboy.integration.database.repository.lesson_54;

/* Lesson 54 - Тесты на методы Pageable и Slice с применением аннотации @EntityGraph */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.FifthUserRepository;
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
class FifthUserRepositoryTest {

    private final FifthUserRepository fifthUserRepository;

    /* Lesson 54: Тестируем работу аннотированного @EntityGraph метода с применением именованного графа */
    @Test
    void checkPaginationWithNamedEntityGraphTest() {
        PageRequest myPageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> myFirstPage = fifthUserRepository.findAllUserWithNamedEntityGraphBy(myPageable);
        /*
        Сформируем вывод данных так, чтобы Spring сделал дополнительный запрос к БД, чтобы получить LAZY
        сущность Company.
        */
        myFirstPage.forEach(user -> System.out.println(user.getCompany().getName()));

        while (myFirstPage.hasNext()) {
            myFirstPage = fifthUserRepository.findAllUserWithNamedEntityGraphBy(myFirstPage.nextPageable());
            myFirstPage.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

    /* Lesson 54: Тестируем работу аннотированного @EntityGraph метода с применением атрибутов графа */
    @Test
    void checkPaginationWithEntityGraphAttributeTest() {
        PageRequest myPageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> myFirstPage = fifthUserRepository.findAllUserWithAttributeEntityGraphBy(myPageable);
        myFirstPage.forEach(user -> System.out.println(user.getCompany().getName()));

        while (myFirstPage.hasNext()) {
            myFirstPage = fifthUserRepository.findAllUserWithAttributeEntityGraphBy(myFirstPage.nextPageable());
            myFirstPage.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }
}