package spring.oldboy.database.repository.user_repository;
/* Lesson 56 - применение DTO и проекций */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.entity.User;
import spring.oldboy.dto.PersonalInfo;
import spring.oldboy.dto.PersonalInfoTwo;

import java.util.List;

/* Изменим наш класс на интерфейс и расширим JpaRepository */
@Repository
public interface ProjectionUserRepository extends JpaRepository<User, Long> {

    /*
    Создадим запрос к БД и вернем не сущность, а ее неполное отображение, в данном случае
    мы работаем с конкретной сущностью, а не с чем-то универсальным - <T> - это динамические
    проекции, когда мы возвращаем не какой-то конкретный класс, а получаем 'масштаб для
    маневра' см. статью: DOC/ArticleAboutProjection/SpringDataJPAProjections.txt. Пример ниже.
    */
    List<PersonalInfo> findAllByCompanyId(Integer companyId);

    /*
    Теперь мы можем передавать в метод любой класс проекций (PersonalInfo, PersonalFullName и т.д.)
    и не плодить методы запроса под конкретную сущность.
    */
    <T> List<T> findAllByCompanyId(Integer companyId, Class<T> type);

    /*
    Особенность нативного запроса тут в том, что названия геттеров из PersonalInfoTwo,
    без префикса *.get, должны совпадать с именами полей сущности. Однако, у нас поле
    BirthDate в User отличается от birth_date в БД. И вот тут нам помогает alias, без
    него мы бы получили null.

    Еще особенность такого запроса в том, что в итоге мы получаем список прокси объектов.
    Проекции очень удобно использовать с нативными запросами, главное правильно сопоставить
    названия полей с геттерами интерфейса.
    */
    @Query(value = "SELECT firstname, " +
                   "lastname, " +
                   "birth_date as birthDate " +
                   "FROM users " +
                   "WHERE company_id = :companyId",
                   nativeQuery = true)
    List<PersonalInfoTwo> findAllByCompanyIdWithInterface(Integer companyId);

}
