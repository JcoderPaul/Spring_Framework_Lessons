package spring.oldboy.database.repository.company_repository;
/*
Lesson 47 - применение интерфейса Repository, методы без
реализации:
см. DOC/RepositoryInterfaceAndClass/RepositoryInterface.txt
см. DOC/SpringDataJPARepository.txt
*/

import org.springframework.data.repository.Repository;
import spring.oldboy.database.entity.Company;

import java.util.Optional;

/*
1. В Spring Data JPA на данном этапе не принято использовать конкретные реализации поэтому наш класс
   превратится в интерфейс расширяющий Repository.

   Теперь у нас нет зависимостей, как это было реализовано в прошлой части:
   ...\Spring_Lessons\Spring_part_9\src\main\java\spring\oldboy\database\repository\CompanyRepository.java

2. Теперь самое 'страшное' наши методы остаются пустыми - без конкретной реализации, только сигнатуры.
3. Мы убираем все аннотации, даже аннотацию @Repository. Главным для Spring конфигурации является то, что
   мы используем интерфейс Repository.
*/
public interface CompanyRepository extends Repository<Company, Integer> {

    Optional<Company> findById(Integer id);

    void delete(Company entity);
}