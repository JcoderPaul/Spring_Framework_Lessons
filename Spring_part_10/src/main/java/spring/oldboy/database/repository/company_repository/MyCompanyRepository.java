package spring.oldboy.database.repository.company_repository;

/*
Lesson 48 - 49 - применение интерфейса JpaRepository,
именованные запросы, стратегия создания запросов к БД
на основании имени метода запроса:
см. DOC/SpringDataJPATutorial/6_QueriesFromMethodNames.txt
см. DOC/SpringDataJPATutorial/8_QueriesWithNamedQueries.txt
*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import spring.oldboy.database.entity.Company;

import java.util.List;
import java.util.Optional;

/*
Lesson 48 - 49:

Используем другой интерфейс в качестве расширяемого JpaRepository, см.
DOC/RepositoryInterfaceAndClass/JpaRepository.txt наш репозитарий работает
с Company и мы будем использовать формирование запросов по имени метода см.
DOC/SpringDataJPATutorial/6_QueriesFromMethodNames.txt

Имена методов строятся исходя из принципов описанных в:
https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods.query-creation
*/
public interface MyCompanyRepository extends JpaRepository<Company, Integer> {

    /*
    Lesson 48:

    В случае возврата единственного результата можем возвращать:
    Optional, Entity, Future
    см. DOC/SpringDataJPATutorial/5_IntroductionQueryMethods.txt
    */
    Optional<Company> findByName(String name);

    /*
    В случае возврата более одного результата мы можем возвращать:
    Collection, Stream (batch, close)
    см. DOC/SpringDataJPATutorial/5_IntroductionQueryMethods.txt

    В данном случае Containing - … where x.name like ?1 (параметр
    завернутый в %), а IgnoreCase вытекает из названия - регистр не
    имеет значения.
    */
    List<Company> findAllByNameContainingIgnoreCase(String fragment);

    /*
    Lesson 49:

    Применение именованных запросов, вторая часть запроса, вернее ее
    HQL (JPQL) реализация располагается в коде сущности Company.

    Ниже приведен пример использования параметров и аннотации @Param
    комментарии к методу так же расположены в Company под аннотацией
    @NamedQuery - определяющей именованные запросы.
    */
    Optional<Company> findCompanyByName(String name);

    Optional<Company> findCompanyByNameWithParam(@Param("companyName") String name);

}