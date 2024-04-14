package spring.oldboy.database.repository.user_repository;
/* Lesson 53 - Интерфейсы Slice, Pageable */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.entity.User;

/* Изменим наш класс на интерфейс и расширим JpaRepository */
@Repository
public interface FourthUserRepository extends JpaRepository<User, Long> {

    /*
    Lesson 53:
    В DOC/SpringDataJPATutorial/5_IntroductionQueryMethods.txt мы уже выяснили,
    что можем возвращать Collection, Stream, так же Spring позволяет возвращать:
    Streamable, Slice, Page.

    В данном методе мы наш Lise<User>, просто поменяем на Slice:
    */
    Slice<User> findAllUserBy(Pageable pageable);
    /*
    Lesson 53:
    В DOC/SpringDataJPATutorial/5_IntroductionQueryMethods.txt мы уже выяснили,
    что можем возвращать Collection, Stream, так же Spring позволяет возвращать:
    Streamable, Slice, Page.

    Вернем теперь Page<User>:
    */
    Page<User> findAllUserPagesBy(Pageable pageable);

    /* При помощи аннотации @Query мы можем влиять на count */
    @Query(value = "select u from User u",
           countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllUserPagesWithCountBy(Pageable pageable);

}
