package spring.oldboy.database.repository.user_repository;

/* Lesson 57 - создаем наш кастомный репозитарий. */

import spring.oldboy.database.entity.User;
import spring.oldboy.dto.UserFilterDto;

import java.util.List;

public interface FilterUserRepository {
    /*
    Название метода подчиняется правилам именования методов репозитария описанных в
    DOC/SpringDataJPATutorial/6_QueriesFromMethodNames.txt и в документации Spring-a:
    https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods.query-creation
    и
    https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#repository-query-keywords
    ключевое слово Filter совпадает с названием переданного параметра filter.
    */
    List<User> findAllByFilter(UserFilterDto filter);
}
