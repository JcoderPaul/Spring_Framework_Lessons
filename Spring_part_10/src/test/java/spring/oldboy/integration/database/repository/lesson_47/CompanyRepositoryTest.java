package spring.oldboy.integration.database.repository.lesson_47;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import spring.oldboy.database.entity.Company;
import spring.oldboy.database.repository.company_repository.CompanyRepository;
import spring.oldboy.integration.annotation.IT;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IT
@RequiredArgsConstructor
/*
Lesson 47 - для прохождения тестов в данном уроке аннотация @Commit должна
            работать (т.е. быть раскомментированой и охватывать весь тестовый
            класс целиком).
Lesson 60 - Поскольку методы deleteCompanyTest() и saveCompanyToBase()
            воздействуют на реальную БД, а усложнять код тестов мы не
            хотим, для создания Q - классов, и избежании ошибок при их
            компиляции, необходимо перед задачей (task) build их
            закомментировать.
*/
@Commit
class CompanyRepositoryTest {

    /*
    !!! Подготовка к тесту delete() !!!
    Установим аннотацию @Commit для сохранения изменений внесенных тестами.
    Запустим тест saveCompanyToBase() и посмотрим в БД номер ID от внесенного Apple.
    Установим этот ID в поле APPLE_ID.
    */
    private static final Integer APPLE_ID = 20;
    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;

    /*
    Перед запуском теста нужно провести подготовку см. выше.

    !!! Внимание !!! Перед build task-ом в Lesson 60 комментируем
    аннотацию @Test данного метода, поскольку он закончится с fail
    вместо true и не позволит создать Q - классы.
    */
    // @Test
    void deleteCompanyTest() {
        /*
        Логика действий проста:
        - извлекаем из БД сущность с указанным нами APPLE_ID;
        - проверяем, смогли ли мы ее извлечь (есть ли она фактически);
        - если сущность есть запускаем метод 'удалить';
        */
        Optional<Company> maybeCompany = companyRepository.findById(APPLE_ID);
        assertTrue(maybeCompany.isPresent());
        maybeCompany.ifPresent(entity -> companyRepository.delete(entity));
        /*
        Метод delete не вызовется до момента commit-a транзакции,
        либо, как у нас до метода *.flush(), для этого он тут и
        нужен, т.к. настройки Spring по-умолчанию тут - Lazy.
        */
        entityManager.flush();
        /* Ну и наконец проверяем удалили ли мы сущность с указанным APPLE_ID */
        assertTrue(companyRepository.findById(APPLE_ID).isEmpty());
    }

    @Test
    void findById() {
        Company company = entityManager.find(Company.class, 1);
        assertNotNull(company);
        assertThat(company.getLocales()).hasSize(2);
    }

    /*
    Перед запуском теста нужно провести подготовку см. выше.

    !!! Внимание !!! Перед build task-ом в Lesson 60 комментируем
    аннотацию @Test данного метода, поскольку он закончится с fail
    вместо true и не позволит создать Q - классы.
    */
    // @Test
    void saveCompanyToBase() {
        Company company = Company.builder()
                .name("Apple")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }
}