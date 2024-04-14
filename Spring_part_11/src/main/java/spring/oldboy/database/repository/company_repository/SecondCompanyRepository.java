package spring.oldboy.database.repository.company_repository;
/*
Lesson 50: применение явных запросов с аннотацией @Query,
см. DOC/SpringDataJPATutorial/7_QueriesWithAnnotation.txt
*/
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.oldboy.database.entity.Company;

import java.util.List;
import java.util.Optional;

public interface SecondCompanyRepository extends JpaRepository<Company, Integer> {

    /*
    Lesson 50:

    Как уже было сказано ранее, недостаток именованных запросов в том,
    что мы не можем увидеть вызванный запрос к базе данных из интерфейса
    репозитория.

    Одним из вариантов устранения этого недостатка может быть аннотирование
    с явным указанием имени запроса:

    @Query(name = "Company.findCompanyByName")

    Либо еще более явно и приоритетно по сравнению с именованными запросами
    и запросами сгенерированными на основе их имени будет явное написание
    запроса в теле аннотации. Используем HQL и оперируем уже сущностями.

    Преимущества в простоте, тут мы можем подтянуть fetch сущности, в нашем
    случае locales
    */
    @Query("select c from Company c " +
           "join fetch c.locales cl " +
           "where c.name = :name")
    Optional<Company> findCompanyByName(String name);

    /*
    В случае возврата более одного результата мы можем возвращать:
    Collection, Stream (batch, close)
    см. DOC/SpringDataJPATutorial/5_IntroductionQueryMethods.txt

    В данном случае Containing - … where x.name like ?1 (параметр
    завернутый в %), а IgnoreCase вытекает из названия - регистр не
    имеет значения.
    */
    List<Company> findAllByNameContainingIgnoreCase(String fragment);

}