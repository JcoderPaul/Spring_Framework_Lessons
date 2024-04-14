package spring.oldboy.integration.database.repository.lesson_61;

/* Lesson 61 - проверяем работы batch запросов к БД в Spring JDBC. */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.UserRepository;
import spring.oldboy.integration.annotation.IT;

import java.util.List;

@IT
@RequiredArgsConstructor
class CompanyRepositoryBatchTest {

    /*
    Незабываем об иерархии наследования нашего UserRepository и почему
    вдруг нам доступны методы нами не созданные и не реализованные.
    */
    private final UserRepository userRepository;

    @Test
    void checkBatchTest() {
        /*
        Сначала мы получаем всех наших user-ов из базы. Метод *.findAll()
        принадлежит ListCrudRepository<T, ID> extends CrudRepository<T, ID>
        */
        List<User> users = userRepository.findAll();
        /* А затем обновляем их всех их-же данными, просто для теста */
        userRepository.updateCompanyAndRole(users);
        /* В консоли видно как происходит процесс */
    }

    @Test
    void checkBatchWithNameParamTest() {
        /*
        Сначала мы получаем всех наших user-ов из базы. Метод *.findAll()
        принадлежит ListCrudRepository<T, ID> extends CrudRepository<T, ID>
        */
        List<User> users = userRepository.findAll();
        /* А затем обновляем их всех их-же данными, просто для теста */
        userRepository.updateCompanyAndRoleNamed(users);
        /* В консоли видно как происходит процесс */
    }

}