package spring.oldboy.integration.database.repository.lesson_56;

/* Lesson 55 - Тесты на методы с применением проекций DTO */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.repository.user_repository.ProjectionUserRepository;
import spring.oldboy.dto.PersonalInfo;
import spring.oldboy.dto.PersonalInfoTwo;
import spring.oldboy.dto.PersonalRole;
import spring.oldboy.integration.annotation.IT;

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
class UserRepositoryProjectionsTest {

    private final ProjectionUserRepository projectionUserRepository;

    @Test
    void checkUserProjectionsTest() {
        /* Получим список сотрудников из компании с ID = 1 */
        List<PersonalInfo> projectionsUsers = projectionUserRepository.findAllByCompanyId(1);
        /* Мы точно знаем, что их будет 2-ое, подтверждаем */
        assertThat(projectionsUsers).hasSize(2);
        /* Нарушим тестовые принципы - просто выведем на экран сущности из списка */
        projectionsUsers.forEach(System.out::println);
    }

    @Test
    void checkUserProjectionsWithDynamicParameterTest() {
        /* Получим список сотрудников из компании с ID = 1 */
        List<PersonalRole> projectionsUsers =
                projectionUserRepository.findAllByCompanyId(1, PersonalRole.class);
        /* Мы точно знаем, что их будет 2-ое, подтверждаем */
        assertThat(projectionsUsers).hasSize(2);
        /* Нарушим тестовые принципы - просто выведем на экран сущности из списка */
        projectionsUsers.forEach(System.out::println);
    }

    @Test
    void checkUserProjectionsWithInterfaceTest() {
        /* Получим список сотрудников из компании с ID = 1 */
        List<PersonalInfoTwo> projectionsUsers =
                projectionUserRepository.findAllByCompanyIdWithInterface(1);
        /* Мы точно знаем, что их будет 2-ое, подтверждаем */
        assertThat(projectionsUsers).hasSize(2);
        /* Нарушим тестовые принципы - просто выведем на экран сущности из списка */
        projectionsUsers.forEach(System.out::println);
    }

}