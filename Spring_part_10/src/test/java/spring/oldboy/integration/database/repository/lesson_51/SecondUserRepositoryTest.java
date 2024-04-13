package spring.oldboy.integration.database.repository.lesson_51;

/* Lesson 51 */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.entity.Role;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.SecondUserRepository;
import spring.oldboy.integration.annotation.IT;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/* Это интеграционный тест и мы используем нашу аннотацию */
@IT
/*
@RequiredArgsConstructor - позволит получить конструктор с параметром для каждого поля,
но эти параметры потребуют специальной обработки.  Все неинициализированные final поля
получают параметр, также как все остальные поля, помеченные @NonNull, которые не
инициализированные при объявлении.
*/
@RequiredArgsConstructor
class SecondUserRepositoryTest {

    private final SecondUserRepository secondUserRepository;

    /*
    Lesson 51:

    Тестируем метод с использованием параметров в @Modifying, см.
    spring/oldboy/database/repository/SecondUserRepository.java
    */
    @Test
    void checkUpdateMethodTest() {

        User ivan = secondUserRepository.getReferenceById(1L);
        assertSame(Role.ADMIN, ivan.getRole());

        int resultCount = secondUserRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, resultCount);


        User theSameIvan = secondUserRepository.getReferenceById(1L);
        assertSame(Role.USER, theSameIvan.getRole());

    }

    /*
    Lesson 51:

    Данный тест упадет с org.hibernate.LazyInitializationException:
    could not initialize proxy [spring.oldboy.database.entity.Company#1] - no Session

    Поскольку в SecondUserRepository мы настроили, как

    @Modifying(clearAutomatically = true,
               flushAutomatically = true)

    то в данном тесте, после выполнения метода updateRole контекст будет почищен и
    сущности 'ivan' в ней уже не будет - она detach.

    !!! Внимание !!! Перед build task-ом в Lesson 60 комментируем аннотацию
    @Test данного метода, поскольку он явно закончится с ошибкой и помешает
    созданию Q - классов.
    */
    // @Test
    void lazyExceptionInUpdateMethodTest() {

        User ivan = secondUserRepository.getReferenceById(1L);
        assertSame(Role.ADMIN, ivan.getRole());

        int resultCount = secondUserRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, resultCount);

        /*
        И вот тут вылетит исключение. Нужно внимательно следить за настройками @Modifying
        и четко понимать, в какой момент времени сущность ушла из текущего контекста и стоит
        ли вернуть ее обратно. Настройка параметра clearAutomatically вещь тонкая и решать
        как, и когда, его использовать лежит на нас (хотим ли мы видеть или нет внесенные
        изменения).
        */
        ivan.getCompany().getName();

        User theSameIvan = secondUserRepository.getReferenceById(1L);
        assertSame(Role.USER, theSameIvan.getRole());

    }
}