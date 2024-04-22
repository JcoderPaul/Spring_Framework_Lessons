package spring.oldboy.database.repository.user_repository;

/*
Lesson 57 - создаем класс реализующий наш кастомный интерфейс FilterUserRepository.java;
Lesson 60 - применяем JDBC в Spring приложении;
Lesson 61 - реализация метода для batch запросов в Spring приложении средствами JDBC;
*/

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class FilterUserRepositoryImpl implements FilterUserRepository  {
    /* Нам нужен EntityManager и он есть в SpringContext */
    private final EntityManager entityManager;

    /* Lesson 60 - SQL запрос для метода реализующего механизм JDBC - *.findAllByCompanyIdAndRole() */
    private static final String FIND_BY_COMPANY_AND_ROLE = """
        SELECT 
            firstname,
            lastname,
            birth_date
        FROM users
        WHERE company_id = ?
            AND role = ?
        """;
    /* Lesson 61 - SQL запрос для метода реализующего batch запрос - *.updateCompanyAndRole() */
    private static final String UPDATE_COMPANY_AND_ROLE = """
        UPDATE users
        SET company_id = ?,
            role = ?
        WHERE id = ?
        """;
    /*
    Lesson 61 - SQL запрос с именованными параметрами для метода
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
    Lesson 60 - внедрим (подключим) зависимость JdbcTemplate,
    чтобы использовать методы передачи запросов к БД через JDBC.
    */
    private final JdbcTemplate jdbcTemplate;
    /*
    Lesson 60 - внедрим (подключим) зависимость NamedParameterJdbcTemplate,
    чтобы использовать методы передачи запросов к БД через JDBC с именованными
    параметрами см. DOC/SpringJDBC/NamedParameterJdbcTemplate.txt.
    */
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    /*
    Lesson 87 - Метод получает с уровня сервисов объект определяющий критерии
    фильтрации и возвращает список записей из БД удовлетворяющий этим фильтрам.
    */
    @Override
    public List<User> findAllByFilter(UserFilterDto filter) {
        /*
        Все что связано с созданием запросов с Criteria API можно посмотреть в
        https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_5

        Тут мы повторим еще раз, что же происходит:
        - CriteriaBuilder - нужен для создания критериальных запросов (запросов с определенными
                            условиями), составных выборок, выражений, предикатов, упорядочений
                            (сортировок).
        - EntityManager - экземпляр этого класса связан с персистентным контекстом.
        - Персистентный контекст - это набор экземпляров сущности, в котором для любого постоянного
                                   идентификатора сущности существует уникальный экземпляр сущности.
                                   Поскольку персиситивность нечто постоянное, то мы имеем дело с
                                   набором экземпляров 'условно стойких объектов нашего приложения'

        В персистентном контексте осуществляется управление экземплярами сущностей и их жизненным
        циклом. API EntityManager используется для создания и удаления постоянных экземпляров
        сущностей, для поиска сущностей по их первичному ключу и для запроса сущностей.

        Шаг 1. - Получаем из 'контекста' при помощи менеджера сущностей экземпляр 'построителя
        критериев (условий) для запроса к БД'
        */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        /*
        - CriteriaQuery - определяет функциональные возможности, специфичные для запросов верхнего
                          уровня. Т.е. содержит методы позволяющие модифицировать запросы к БД.

        Шаг 2. - Создаем из построителя запросов CriteriaQuery - запрос с условиями, и в параметрах
        метода createQuery() указываем результирующий тип объекта на выходе запроса.
        */
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
        /*
        Любой запрос начинается с некой сущности с которой связны другие сущности (как таблицы БД
        связанны между собой). Мы можем сделать запрос к БД двигаясь от одной 'условно корневой'
        таблицы и в зависимости от критериев запроса можем получить данные из других связанных
        таблиц.

        - Root - Корневой тип в предложении from (SELECT * FROM root). Корни запросов всегда
        ссылаются на сущности. В нашем случае это таблица 'users' и сущность 'user'

        Шаг 3. - Определяем и добавляем корень запроса, соответствующий данному объекту, образуя
        декартово произведение с любыми существующими корнями.
        */
        Root<User> userRoot = userCriteriaQuery.from(User.class);
        /*
        Шаг 4. - Указываем элемент, который должен быть возвращен в результате запроса.
        */
        userCriteriaQuery.select(userRoot);
        /*
        Нам нужен Predicate из *.persistence.criteria. Предикат — это функция, которая возвращает
        true или false в зависимости от переданного значения. У нас будет несколько условий по
        которым мы планируем фильтровать наши запросы. Поэтому мы создали пустой список, куда будут
        складываться такие 'предикаты' или 'утверждения'. Естественно, утверждение может быть, как
        true, так и false при определенных условиях.

        Поскольку, фильтрация у нас будет проводиться по трем полям (условиям), сочетание таких
        условий может быть различным. Например, мы можем фильтровать user-ов по 'firstname' и только
        опуская фильтрацию по 'lastname' и 'birthDate', тогда в наш список предикатов (условий
        фильтрации) попадет лишь один предикат. Фильтруем по n - полям получаем список из n - предикатов.

        Шаг 5. - Создаем список возможных предикатов (условий фильтрации).
        Шаг 6. - Заполняем его в методе - getPredicates(filter, criteriaBuilder, userRoot)
        */
        List<Predicate> predicates = getPredicates(filter, criteriaBuilder, userRoot);
        /*
        - метод *.where() - Создает запрос, который вернет результат запроса в соответствии с сочетанием
        указанных предикатов ограничения. Заменяет ранее добавленные ограничения, если таковые имеются.
        Если ограничения не указаны, любые ранее добавленные ограничения просто удаляются. Данный метод
        перегружен и может принимать массив аргументов или в нашем случае массив предикатов.

        Шаг 7. - Добавляем в наш 'CriteriaQuery - запрос с условиями', условия из массива ограничений.

        Теперь у нас есть 'запрос к БД' с условиями, который имеет корневую сущность user и вернуть должен
        тоже user (или список user - ов).
        */
        userCriteriaQuery.where(predicates.toArray(value -> new Predicate[value]));
        /*
        Тут у нас два действия, сначала мы проводим типизированный запрос с уже ранее сформированными
        условиями (критериями), т.е. мы первым делом получаем экземпляр типизированного запроса и уже
        затем извлекаем из него результат - список типизированный <user>
        */
        List<User> filteredQueryResult = entityManager.createQuery(userCriteriaQuery).getResultList();

        return filteredQueryResult;
    }

    /*
    Lesson 88 - Метод получает с уровня сервисов объект определяющий критерии
    фильтрации UserFilterDto и параметры для отображения определенной страницы
    по запросу Pageable. Возвращает список записей из БД удовлетворяющий этим
    ограничениям.
    */
    @Override
    public Page<User> findAllByFilterAndPage(UserFilterDto filter, Pageable pageable) {

        /* Пока все по шагам, как и в методе findAllByFilter(UserFilterDto filter) */
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(userRoot);

        List<Predicate> predicates = getPredicates(filter, criteriaBuilder, userRoot);

        userCriteriaQuery.where(predicates.toArray(value -> new Predicate[value]));
        /*
        А вот тут небольшое отличие, данный список пользователей нам нужен, чтобы
        получить общее количество отфильтрованных записей из БД
        */
        List<User> filteredQueryResult = entityManager.createQuery(userCriteriaQuery).getResultList();

        TypedQuery<User> typedQuery = entityManager.createQuery(userCriteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        long userCount = filteredQueryResult.size();

        return new PageImpl<>(typedQuery.getResultList(), pageable, userCount);
    }

    /*
    Lesson 60 - реализуем метод с JDBC функционалом см. описание
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
    Lesson 61 - реализуем метод с пакетным (batch) запросом.

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
    Lesson 61 - применим именование параметров в SQL запросе,
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


    private static List<Predicate> getPredicates(UserFilterDto criteriaFilter,
                                                 CriteriaBuilder criteriaBuilder,
                                                 Root<User> userRoot) {

        List<Predicate> predicates = new ArrayList<>();
        /*
        Создаем фильтры применив классический like и lessThan см. пример с комментариями к методам
        и логике запроса:
        https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_5/src/main/java/oldboy/dao/UserDaoWithCriteriaAPI.java

        Теперь, мы проверяем прилетели ли к нам условия фильтрации с уровня сервисов. Три поля, три
        возможных самостоятельных фильтра и их сочетания. При наличии таковых мы помещаем их в список
        предикатов (условий фильтрации).

        Шаг 6. - Заполняем список предикатов в зависимости от наличия того или иного фильтра.
        */
        if (criteriaFilter.firstname() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("firstname"),
                    "%" + criteriaFilter.firstname() + "%"));
        }
        if (criteriaFilter.lastname() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("lastname"),
                    "%" + criteriaFilter.lastname()  + "%"));
        }
        if (criteriaFilter.birthDate() != null) {
            predicates.add(criteriaBuilder.lessThan(userRoot.get("birthDate"),
                    criteriaFilter.birthDate()));
        }
        return predicates;
    }
}
