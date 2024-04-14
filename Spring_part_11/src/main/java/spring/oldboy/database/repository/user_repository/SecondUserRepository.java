package spring.oldboy.database.repository.user_repository;
/* Lesson 51 - 52 */
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.entity.Role;
import spring.oldboy.database.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/* Изменим наш класс на интерфейс и расширим JpaRepository */
@Repository
public interface SecondUserRepository extends JpaRepository<User, Long> {

    /*
    Lesson 51:

    При тестировании spring/oldboy/database/repository/UserRepository.java мы разобрались, чтобы не
    получить исключение:

    Expecting a SELECT query : `update User u set u.role = :role where u.id in (:ids)`

    Нам нужно аннотация @Modifying. Как уже было сказано, что такие запросы идут в обход текущего
    персистентого контекста. И значит, если мы попробуем проверить внесенные изменения в ходе теста
    мы снова получим исключение:

    expected: <USER> but was: <ADMIN>
    Expected :USER
    Actual   :ADMIN

    Проблема решается внесением параметров clearAutomatically и flushAutomatically в аннотацию
    @Modifying см. DOC/ModifyingAnnotationInterface.txt, т.е. мы задаем следует ли очищать базовый
    персистентный контекст после / перед выполнением модифицирующего запроса, что бы мы могли увидеть
    внесенные изменения, а в тестах подтвердить их.
    */
    @Modifying(clearAutomatically = true,
               flushAutomatically = true)
    @Query("update User u " +
            "set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long... ids);

    /*
    Lesson 52:

    Попробуем передать некие ограничивающие параметры запроса в названии метода.
    В данном случае мы ожидаем получить одну сущность User
    */
    Optional<User> findTopByOrderByIdDesc();
    /*
    Lesson 52:

    Мы пробуем получить список сущностей User, ограничив результат нашего запроса
    3-мя сверху списка сущностями отсортированными по убыванию дня рождения.
    */
    List<User> findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate birthDate);
    /*
    Lesson 52:

    Видоизменим наш предыдущий метод, пусть будет не 3, а два User-a на выходе, а
    также используем класс Sort (см. DOC/SortClass.txt) для передачи динамической
    составляющей сортировки. В тестовом классе используется так же вложенный
    подкласс Sort.TypedSort для улучшения (см. DOC/Sort_TypedSort_Class.txt).
    */
    List<User> findTop2ByBirthDateBefore(LocalDate birthDate, Sort sort);
    /*
    Lesson 52:

    В данном методе запроса в качестве параметра используется интерфейс Pageable
    см. DOC/PageableInterface.txt.
    */
    List<User> findAllUserBy(Pageable pageable);

}
