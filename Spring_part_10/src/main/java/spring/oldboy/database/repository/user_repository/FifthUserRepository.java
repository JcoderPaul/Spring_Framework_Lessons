package spring.oldboy.database.repository.user_repository;

/* Lesson 54 - Использование @EntityGraph */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.entity.User;

/* Изменим наш класс на интерфейс и расширим JpaRepository */
@Repository
public interface FifthUserRepository extends JpaRepository<User, Long> {

    /*
    Lesson 54:

    Применим @EntityGraph его параметр value, который работает с именованными
    графами, см. сущность User. Стоит обратить внимание, что Company у нас -
    LAZY сущность и для ее получения, Hibernate сформирует дополнительный запрос.
    */
    @EntityGraph("User.company")
    @Query(value = "select u from User u",
           countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllUserWithNamedEntityGraphBy(Pageable pageable);

    /*
    В данном случае мы уже используем атрибуты аннотации, чтобы подтянуть LAZY сущности,
    а в случае с company.locales мы подтягиваем 'подграф', поскольку локали для сущности
    Company тоже LAZY. Но при этом мы не увеличиваем количество строк кода в User, для
    настройки подграфа и даже не создаем именованный сущностной граф - @NamedEntityGraph
    в сущности UserS.
    */
    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from User u",
           countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllUserWithAttributeEntityGraphBy(Pageable pageable);

}
