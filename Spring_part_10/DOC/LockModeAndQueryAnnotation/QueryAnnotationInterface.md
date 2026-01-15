### Annotation Interface Query

Аннотация для объявления запросов поиска непосредственно в методах репозитория.

---
```Java
  @Retention(RUNTIME)
  @Target({METHOD,ANNOTATION_TYPE})
  @Documented
  public @interface Query
```

---
#### Элементы

- String countName - Возвращает имя объекта, NamedQuery которое будет использоваться для выполнения запросов подсчета
                     при использовании нумерации страниц. По умолчанию будет задано имя именованного запроса с
                     суффиксом .count.

- String countProjection - Определяет проекционную часть запроса подсчета, создаваемого для разбиения на страницы. Если
                           ни countQuery(), ни countProjection() не настроены, мы получим запрос подсчета из исходного
                           запроса.

- String countQuery - Определяет специальный запрос подсчета, который будет использоваться для запросов разбиения на
                      страницы для поиска общего количества элементов на странице. Если ничего не настроено, мы получим
                      запрос подсчета из исходного запроса или запроса countProjection(), если таковой имеется.

- String name - Именованный запрос, который будет использоваться. Если не определено, будет использоваться NamedQuery
                с именем $domainClass.${queryMethodName}}.

- boolean nativeQuery - Определяет, является ли данный запрос нативным SQL. По умолчанию - false.

- Class<? extends QueryRewriter> queryRewriter - Определите объект QueryRewriter, который должен быть применен к строке
                                                 запроса после полной сборки запроса.

- String value - Определяет запрос JPA, который будет выполняться при вызове аннотированного метода.

---
- [См. оригинал (ENG)](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/Query.html);

---
**Статьи по теме:**
- [Spring Data JPA @Query](https://www.baeldung.com/spring-data-jpa-query);
- [Ultimate Guide: Custom Queries with Spring Data JPA’s @Query Annotation](https://thorben-janssen.com/spring-data-jpa-query-annotation/);
- [Spring Data JPA @Query Annotation with Example](https://www.geeksforgeeks.org/java/spring-data-jpa-query-annotation-with-example/);
