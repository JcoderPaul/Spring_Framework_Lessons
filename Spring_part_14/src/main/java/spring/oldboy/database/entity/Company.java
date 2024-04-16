package spring.oldboy.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/*
Part 10: Lesson_49 - Добавим аннотацию именованного запроса и настроим ее,
зададим имя - name, по которому мы будем к ней обращаться и сам запрос в
HQL - query.

Если мы хотим использовать стратегию именования Spring Data JPA
по умолчанию, нам нужно указать имя именованного запроса, используя
следующий синтаксис: [имя класса сущности].[имя вызванного метода запроса].

Подробнее см. DOC/SpringDataJPATutorial/8_QueriesWithNamedQueries.txt
 */
@NamedQuery(
        /*
        Данный запрос имеет точно такое же имя, как и метод в нашем
        репозитарии, но имеет преимущество и при запросе будет вызван
        именно он. Так же он чуть отличается своим наполнением за счет
        параметра query - тут мы отдаем предпочтение нижнему регистру.
        */
        name = "Company.findCompanyByName",
        /*
        Если мы используем в качестве параметра не конкретное значение или '?1',
        а в нашем случае поле 'name' точно так же должен называться параметр
        переданный в метод запроса *.findByName(String name) в наш репозитарий,
        в данном примере MyCompanyRepository.java, иначе мы словим исключение.
        */
        query = "select c from Company c where lower(c.name) = lower(:name)"
)
@NamedQuery(
        name = "Company.findCompanyByNameWithParam",
        /*
        Пример того как использовать параметр в методе репозитария. Если мы
        используем в качестве параметра некое название, например 'companyName',
        то именно его надо передать в метод запроса репозитария
        *.findCompanyByNameWithParam(@Param("companyName") String name),
        в данном примере MyCompanyRepository.java, иначе мы словим исключение,
        как было описано выше.
        */
        query = "select c from Company c where lower(c.name) = lower(:companyName)"
)
/*
Используем аннотации Lombok для создания
конструкторов, геттеров, сеттеров и т.д.
*/
@Data
/* Конструктор без параметров обязателен в Hibernate */
@NoArgsConstructor
@AllArgsConstructor
@Builder
/* Помечаем нашу сущность Hibernate */
@Entity
/*
Аннотируем с конкретным именем таблицы, если название сущности
с маленькой буквы совпадает с названием соответствующей таблицы,
специально именовать не требуется, но лучше это сделать - для
наглядности и удобства.
*/
@Table(name = "company")
public class Company implements BaseEntity<Integer> {

    /* Текущее поле соответствует полю в БД - ID и оно авто-генерируется */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /*
    Если имя текущего поля класса совпадает с именем
    поля в БД, специально именовать его необязательно,
    но мы это сделаем (результат будет тот же)
    */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /*
    Для таблицы company_locales мы не будем создавать сущность,
    т.к. это элемент локализации (соответствие страны - языку)
    или basic объект см. ниже.
    */
    @Builder.Default
    /*
    Установили взаимосвязь с таблицей локалей, для понимания
    сути происходящего см. DOC/ElementCollectionAnnotation.txt
    */
    @ElementCollection
    /* Указываем с какой таблицей устанавливаем связь и по какому полю таблицы будет идти связь */
    @CollectionTable(name = "company_locales", joinColumns = @JoinColumn(name = "company_id"))
    /* Определили какое поле таблицы БД будет ключом для MAP коллекции */
    @MapKeyColumn(name = "lang")
    /* Определили какое поле таблицы БД будет value для MAP коллекции */
    @Column(name = "description")
    /* Сразу инициализируем коллекцию, что бы не выбрасывалось NullPointerException */
    private Map<String, String> locales = new HashMap<>();
}
