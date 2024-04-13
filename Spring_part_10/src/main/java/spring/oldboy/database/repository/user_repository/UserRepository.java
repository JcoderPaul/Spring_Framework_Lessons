package spring.oldboy.database.repository.user_repository;

/*
Lesson 50 - 51;
57 - реализация методов рукописного репозитария;
59 - добавим интерфейс RevisionRepository<User, Long, Integer>,
     где User - сущность чьи изменения мы фиксируем (у нас это User),
         Long - класс ID User (у нас он Long),
         Integer - класс номера ревизии (у нас он Integer).
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.entity.Role;
import spring.oldboy.database.entity.User;

import java.util.List;

/* Изменим наш класс на интерфейс и расширим JpaRepository */
@Repository
public interface UserRepository extends JpaRepository<User, Long>,
                                        FilterUserRepository,
                                        RevisionRepository<User, Long, Integer> {

    /*
    Lesson 50:

    Если бы мы использовали возможность создания запроса по имени метода, то сам
    метод назывался бы: *.findAllByFirstnameContainingAndLastnameContaining().
    Длинно и тяжело читаемо, хотя понятно что будет делаться. Внесем простоту
    и наглядность, добавим явные запросы через аннотации @Query и в одном случае
    мы будем использовать HQL, а в другом случае классический нативный SQL запрос.

    См. DOC/SpringDataJPATutorial/7_QueriesWithAnnotation.txt

    Знак % мы можем поместить в параметры метода, т.е. передать их, что не удобно,
    а можем перенести в HQL запрос и обрамить параметры запроса. !!! Однако, это
    не HQL стандарт, а фича Spring Data !!!
    */
    @Query("select u from User u " +
           "where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(String firstname, String lastname);

    /*
    Lesson 50:

    Используем SQl, в данном случае в аннотацию передаются два параметра:
    - value - в котором мы помещаем наш SQL запрос;
    - nativeQuery - параметр 'разрешающий' использование нативного запроса;

    См. DOC/SpringDataJPATutorial/7_QueriesWithAnnotation.txt

    А вот тут в отличие от HQl запроса (работа с сущностями), мы делаем
    классический SQL и нам нужно вернуть resultSet поля относящиеся к
    сущности Repository, у нас это User (UserRepository)
    */
    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
           nativeQuery = true)
    List<User> findAllByUsername(String username);

    /*
    Lesson 51:

    В данном случае если мы воспользуемся только единственной аннотацией @Query,
    запрос в ее параметрах не выполнится, мы получим исключение:

    Expecting a SELECT query : `update User u set u.role = :role where u.id in (:ids)`

    Для того чтобы мы могли провести операцию модификации данных в БД нам нужна
    аннотация @Modifying.

    Однако нужно понимать, что такие запросы идут в обход текущего персистентого
    контекста. Т.е. если мы будем работать с сущностью до того, как она была изменена
    в данном запросе, то ее изменений мы не зафиксируем см. DOC/PersistenceContextScheme.jpg
    и https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_11.
    */
    @Modifying
    @Query("update User u " +
           "set u.role = :role " +
           "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);
}
