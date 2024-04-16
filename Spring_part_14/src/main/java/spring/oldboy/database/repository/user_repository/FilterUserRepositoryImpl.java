package spring.oldboy.database.repository.user_repository;

/*
Part 10: Lesson 57 - создаем класс реализующий наш кастомный интерфейс FilterUserRepository.java;
Part 11: Lesson 60 - применяем JDBC в Spring приложении;
Part 11: Lesson 61 - реализация метода для batch запросов в Spring приложении средствами JDBC;
*/

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import spring.oldboy.database.entity.Role;
import spring.oldboy.database.entity.User;
import spring.oldboy.dto.PersonalInfo;
import spring.oldboy.dto.UserFilterDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
Название класса должно совпадать с названием реализуемого интерфейса с
постфиксом Impl - это очень важно, только так его нужно именовать
(либо лезть в настройки Spring-a и курочить его под свои нужды).

Lesson 60 - не магия, но удобство, т.к. мы работаем с FilterUserRepository
то обращение пойдет к уже готовому его bean-у, хотя мы в данном уроке
подключили (внедрили) JDBC и применяем метод с его функционалом (технологией).
*/
@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {
    /* Нам нужен EntityManager и он есть в SpringContext */
    private final EntityManager entityManager;
    /* Наш метод должен вернуть список User, подпадающих под фильтр */

    /* Part 11: Lesson 60 - SQL запрос для метода реализующего механизм JDBC - *.findAllByCompanyIdAndRole() */
    private static final String FIND_BY_COMPANY_AND_ROLE = """
        SELECT 
            firstname,
            lastname,
            birth_date
        FROM users
        WHERE company_id = ?
            AND role = ?
        """;
    /* Part 11: Lesson 61 - SQL запрос для метода реализующего batch запрос - *.updateCompanyAndRole() */
    private static final String UPDATE_COMPANY_AND_ROLE = """
        UPDATE users
        SET company_id = ?,
            role = ?
        WHERE id = ?
        """;
    /*
    Part 11: Lesson 61 - SQL запрос с именованными параметрами для метода
    реализующего batch запрос - *.updateCompanyAndRoleNamed см.
    DOC/SpringJDBC/NamedParameterJdbcTemplate.txt. Каждый параметр
    имеет имя и его мы применим при передаче параметров в метод
    */
    private static final String UPDATE_COMPANY_AND_ROLE_NAMED = """
        UPDATE users
        SET company_id = :companyId,
            role = :role
        WHERE id = :id
        """;

    /*
    Part 11: Lesson 60 - внедрим (подключим) зависимость JdbcTemplate,
    чтобы использовать методы передачи запросов к БД через JDBC.
    */
    private final JdbcTemplate jdbcTemplate;
    /*
    Part 11: Lesson 60 - внедрим (подключим) зависимость NamedParameterJdbcTemplate,
    чтобы использовать методы передачи запросов к БД через JDBC с именованными
    параметрами см. DOC/SpringJDBC/NamedParameterJdbcTemplate.txt.
    */
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    /* Part 10: Lesson 57 */
    @Override
    public List<User> findAllByFilter(UserFilterDto filter) {
        /*
        Все что связано с созданием запросов с Criteria API можно посмотреть в
        https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_5
        */
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);

        Root<User> user = criteria.from(User.class);
        criteria.select(user);

        /* Нам нужен Predicate из *.persistence.criteria */
        List<Predicate> predicates = new ArrayList<>();
        /*
        Создаем фильтры применив классический like и lessThan см. пример с комментариями к методам и логике запроса:
        https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_5/src/main/java/oldboy/dao/UserDaoWithCriteriaAPI.java
        */
        if (filter.firstname() != null) {
            predicates.add(cb.like(user.get("firstname"), filter.firstname()));
        }
        if (filter.lastname() != null) {
            predicates.add(cb.like(user.get("lastname"), filter.lastname()));
        }
        if (filter.birthDate() != null) {
            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
        }
        criteria.where(predicates.toArray(value -> new Predicate[value]));

        return entityManager.createQuery(criteria).getResultList();
    }

    /*
    Part 11: Lesson 60 - реализуем метод с JDBC функционалом см. описание
    класса и методов в DOC/SpringJDBC/JdbcTemplateClass.txt
    */
    @Override
    public List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role) {
        /*
        В данном случае мы используем обычный select запрос в стиле findBy...,
        мы будем использовать запрос *.query(), а не более универсальный
        *.execute(), которым можно реализовать практически любой запрос к БД.

        Используем <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) -
        Запрос заданный SQL для создания подготовленного оператора из SQL и списка
        аргументов для привязки к запросу, сопоставляя каждую строку с объектом результата
        через RowMapper.

        Где: sql - SQL-запрос для выполнения;
             rowMapper - обратный вызов, который будет отображать один объект в каждой строке;
             args - аргументы для привязки к запросу (оставляя возможность угадать соответствующий
                    тип SQL). Также может содержать SqlParameterValue объекты, которые указывают
                    не только значение аргумента, но также тип SQL и, возможно, масштаб.

        Кроме String SQL запроса, идет RowMapper см. DOC/RowMapperInterface.txt в который передается
        лямда (либо анонимный объект) с нашим DTO. И конечно пара аргументов по которым мы делаем
        выборку из БД.
        */
        return jdbcTemplate.query(FIND_BY_COMPANY_AND_ROLE,
                (rs, rowNum) -> new PersonalInfo(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("birth_date").toLocalDate()
                ), companyId, role.name());
    }

    /*
    Part 11: Lesson 61 - реализуем метод с пакетным (batch) запросом.

    Применим метод int[] batchUpdate(String sql, List<Object[]> batchArgs) из
    JdbcTemplate, который запустит пакетное обновление данных, используя
    предоставленный оператор SQL со списком LIST предоставленных аргументов,
    см. DOC/SpringJDBC/JdbcTemplateClass.txt
    */
    @Override
    public void updateCompanyAndRole(List<User> users) {
        /*
        Мы список наших user-ов преобразуем в stream, а затем его средствами в список Object-ов,
        при этом необходимо быть максимально внимательным и помнить порядок параметров в SQL запросе:
        у нас сначала идет ID компании, затем Role user-a, и только потом ID user-a. Только так в
        случае неименованного запроса.
        */
        List<Object[]> args = users
                .stream()
                .map(user -> new Object[]{user.getCompany().getId(), user.getRole().name(), user.getId()})
                .toList();
        /*
        В данном случае мы избежали настройки BatchPreparedStatement и его
        параметров, в том числе batchSize - размер передаваемого пакета.
        */
        jdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE, args);
    }

    /*
    Part 11: Lesson 61 - применим именование параметров в SQL запросе,
    описание см. DOC/SpringJDBC/NamedParameterJdbcTemplate.txt
    */
    @Override
    public void updateCompanyAndRoleNamed(List<User> users) {
        /* Специально смешаем порядок передаваемых параметров при картировании stream-a */
        MapSqlParameterSource[] args = users
                .stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "companyId", user.getCompany().getId(),
                        "role", user.getRole().name()
                ))
                .map(values -> new MapSqlParameterSource(values))
                .toArray(value -> new MapSqlParameterSource[value]);
        /* Даже с перемешанными параметрами пакетный запрос пройдет */
        namedJdbcTemplate.batchUpdate(UPDATE_COMPANY_AND_ROLE_NAMED, args);
    }
}
