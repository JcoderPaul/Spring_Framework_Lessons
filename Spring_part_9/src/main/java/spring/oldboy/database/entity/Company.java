package spring.oldboy.database.entity;

/* Сущность Company */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

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
