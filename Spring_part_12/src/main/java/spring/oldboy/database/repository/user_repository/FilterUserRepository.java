package spring.oldboy.database.repository.user_repository;

/*
Part 10: Lesson 57 - создаем наш кастомный репозитарий.
Part 11: Lesson 60 - применение JDBC в Spring приложениях
Part 11: Lesson 61 - применение batch запросов в Spring приложении средствами JDBC
*/

import spring.oldboy.database.entity.Role;
import spring.oldboy.database.entity.User;
import spring.oldboy.dto.PersonalInfo;
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

    /*
    Part 11: Lesson 60: Запросы JDBC идут в обход Hibernate и его персистивного контекста
    приложения, а значит мы можем работать только с DTO классами. Естественно его нужно
    реализовать см. FilterUserRepositoryImpl.java
    */
    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    /*
    Part 11: Lesson 61: В данный метод мы передадим список пользователей (не одного, а
    именно список), чтобы одним пакетом обносить данные по ним, см. пояснения в
    DOC/Batch_JDBC_HIBERNATE.txt
    */
    void updateCompanyAndRole(List<User> users);

    /*
    Part 11: Lesson 61: Чтобы понять отличия запросов с именованными параметрами от
    параметризованных '?', см. DOC/SpringJDBC/NamedParameterJdbcTemplate.txt
    */
    void updateCompanyAndRoleNamed(List<User> users);
}
