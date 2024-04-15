package spring.oldboy.database.repository.user_repository;

/* Part 10: Lesson 55 - применение аннотаций @Lock и @QueryHints в запросах к БД */

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.entity.User;

import java.time.LocalDate;
import java.util.List;

/* Изменим наш класс на интерфейс и расширим JpaRepository */
@Repository
public interface SixthUserRepository extends JpaRepository<User, Long> {

    /* Part 10: Lesson 55: Применим @Lock */
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

    /* Part 10: Lesson 55: Применим @Lock */
    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "1"))
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> findTop2ByBirthDateBefore(LocalDate birthDate, Sort sort);

}
