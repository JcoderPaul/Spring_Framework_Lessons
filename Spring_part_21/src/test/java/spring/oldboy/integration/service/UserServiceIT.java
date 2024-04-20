package spring.oldboy.integration.service;

/* Lesson 79 - Проверка тестами методов уровня 'Сервисов' */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import spring.oldboy.database.entity.Role;
import spring.oldboy.dto.UserCreateEditDto;
import spring.oldboy.dto.UserReadDto;
import spring.oldboy.integration.IntegrationTestBase;
import spring.oldboy.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {
    /* Мы планируем извлекать известную запись с ID = 1 */
    private static final Long USER_1 = 1L;
    /* Нам для тестов понадобится компания с известным ID */
    private static final Integer COMPANY_1 = 1;
    /* В основном мы тут тестируем методы UserService (уровня Сервисов) */
    private final UserService userService;

    /* Тестируем первый метод уровня 'Сервисов' - 'найти всех user-ов' */
    @Test
    void findAllTest() {
        /* Мы знаем что в нашей базе 5-ть записей в таблице users */
        List<UserReadDto> result = userService.findAll();
        /*
        Если метод на слое сервисов отработал верно, то в нашем списке
        должно быть 5-ть элементов, проверяем это методом из пакета AssertJ.
        */
        assertThat(result).hasSize(5);
    }

    @Test
    void findByIdTest() {
        /*
        Метод *.findById() возвращает 'бокс' в котором либо есть, либо нет
        объекта класса UserReadDto, естественно он есть, но первой проверкой
        будет:
        */
        Optional<UserReadDto> maybeUser = userService.findById(USER_1);
        /* Есть ли в базе запись с ID = 1, мы уверены, что есть, поэтому проверка на true */
        assertTrue(maybeUser.isPresent());
        /* Следующая проверка при наличии записи - это проверка на эквивалентность одного из полей */
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }
    /* Тестируем создание user-a в БД */
    @Test
    void createTest() {
        /* Фактически нам нужны все поля user-a кроме ID, создаем user-a без этого поля */
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1,
                /* Lesson 112: Таким образом можно добавить 'Mock заглушку' на пустую картинку */
                new MockMultipartFile("test", new byte[0])
        );
        /* Создаем и тут же читаем нашего созданного user-a, и тут ID созданное БД уже есть */
        UserReadDto actualResult = userService.create(userDto);
        /* Проверяем на эквивалентность созданные нами данные и внесенные (прочитанные из БД) */
        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        assertEquals(userDto.getLastname(), actualResult.getLastname());
        assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
        /* ОТЛИЧИЕ ENUM от обычных полей, в методе сравнения - сравниваются объекты */
        assertSame(userDto.getRole(), actualResult.getRole());
    }

    /* Данный метод очень похож на предыдущий в плане логики действий */
    @Test
    void updateTest() {
        /* Создаем DTO на user-a, именно эти данные мы будем подставлять в БД вместо существующих */
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "test",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1,
                /* Lesson 112: Таким образом можно добавить 'Mock заглушку' на пустую картинку */
                new MockMultipartFile("test", new byte[0])
        );
        /*
        Помним, что на уровне сервисов, в нашем классе UserService
        метод *.update() замещает одни данные другими копированием.
        */
        Optional<UserReadDto> actualResult = userService.update(USER_1, userDto);
        /* Мы помним, что user-a с нужным ID может и не быть, проверяем его наличие */
        assertTrue(actualResult.isPresent());
        /*
        Если же user с нужным ID есть и метод *.update() отработал верно,
        то идет проверка на эквивалентность всех полей измененной записи.
        */
        actualResult.ifPresent(editUser -> {
            assertEquals(userDto.getUsername(), editUser.getUsername());
            assertEquals(userDto.getBirthDate(), editUser.getBirthDate());
            assertEquals(userDto.getFirstname(), editUser.getFirstname());
            assertEquals(userDto.getLastname(), editUser.getLastname());
            assertEquals(userDto.getCompanyId(), editUser.getCompany().id());
            /* ОТЛИЧИЕ ENUM от обычных полей, в методе сравнения - сравниваются объекты */
            assertSame(userDto.getRole(), editUser.getRole());
        });
    }

    /*
    Метод удаления коварен. Почти всегда таблицы БД связанны друг с другом, и любое изменение
    в одной таблице повлияет на поля записи в другой (других) таблицах. Для решения этой
    связанности применяют ON DELETE CASCADE, когда при удалении некой записи из одной таблицы,
    автоматически будет удалена запись из другой - опасная практика. Но для наших текущих нужд
    подойдет.

    Мы можем сделать нужные настройки на уровне БД или на уровне Hibernate, еще при разработке
    БД и создании ее скриптов.

    Но тут мы сделаем просто и грубо изменим resources/db/changelog/db.changelog-1.0.sql внеся
    нужные изменения прямо в скрипт - сделаем некоторые поля ON DELETE CASCADE. Удалим нашу базу
    и создадим снова с новыми параметрами - грубо, но тест на удаление пройдет.

    Так никто не делает! Но мы пока учимся.
    */
    @Test
    void deleteTest() {
        assertFalse(userService.delete(-124L));
        assertTrue(userService.delete(USER_1));
    }
}