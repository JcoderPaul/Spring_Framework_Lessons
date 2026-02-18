- [Исходник статьи (ENG)](https://www.petrikainulainen.net/programming/testing/writing-tests-for-data-access-code-unit-tests-are-waste/)
- [Код на GitHub](https://github.com/pkainulainen/jooq-with-spring-examples/tree/master/jooq-only/src/integration-test/java/net/petrikainulainen/spring/jooq/todo/repository)
- [Использование jOOQ со Spring](https://www.petrikainulainen.net/using-jooq-with-spring/)

---
[См. настройка Spring проекта](https://start.spring.io/)

---
### Writing Tests for Data Access Code - Unit Tests Are Waste - "Модульные тесты — пустая трата времени"

Несколько лет назад я был одним из тех разработчиков, которые пишут модульные тесты для моего кода доступа к данным.
**Я тестировал все в изоляции и был очень доволен собой. Честно говоря, я думал, что делаю хорошую работу.**

**О боже, как я ошибался!**

В этой статье описывается, почему нам не следует писать модульные тесты для нашего кода доступа к данным, и объясняется,
почему нам следует заменить модульные тесты интеграционными тестами.

Давайте начнем.

---
#### Модульные тесты - ответы на неправильный вопрос

Мы пишем тесты для нашего кода доступа к данным, потому что хотим знать, что он работает должным образом. Другими
словами, мы **хотим найти ответы на следующие вопросы**:
- *Сохраняются ли правильные данные в используемой базе данных?*
- *Возвращает ли наш запрос к базе данных правильные данные?*
- *Могут ли модульные тесты помочь нам найти ответы, которые мы ищем?*

Что ж, **одно из самых фундаментальных правил модульного тестирования заключается в том, что модульные тесты не должны
использовать внешние системы, такие как база данных**. Это **правило не подходит для данной ситуации**, поскольку
ответственность за сохранение правильной информации и возврат правильных результатов запроса разделена между нашим
кодом доступа к данным и используемой базой данных.

Например, **когда наше приложение выполняет один запрос к базе данных**, ответственность распределяется следующим образом:
- **Код доступа к данным**, отвечающий за создание выполняемого запроса к базе данных.
- **База данных отвечает за выполнение запроса** к базе данных и возврат результатов запроса обратно в код доступа к данным.

Дело в том, что если мы изолируем наш код доступа к данным от базы данных, мы можем проверить, что наш код доступа к
данным создает «правильный» запрос, но мы не можем гарантировать, что созданный запрос возвращает правильные результаты
запроса.

Вот почему **модульные тесты не могут помочь нам найти ответы**, которые мы ищем.

---
#### Поучительная история: mock's — часть проблемы

Было время, когда я писал модульные тесты для своего кода доступа к данным. В то время у меня было два правила:
- Каждый фрагмент кода должен тестироваться изолированно.
- Давайте использовать mock.

Я работал над проектом, в котором использовался Spring Data JPA, а динамические запросы были построены с использованием JPA criteria queries.

Если вы не знакомы с Spring Data JPA, возможно, вы захотите прочитать руководство по Spring Data JPA, в котором
объясняется (../SpringDataJPATutorial/9_QueriesWithJPACriteriaAPI.md), как создавать JPA criteria queries с
помощью Spring Data JPA.

В любом случае я создал класс строитель спецификаций, который создает объекты `Specification<Person>`. После того,
как я создал объект `Specification<Person>`, я передал его в свой репозиторий Spring Data JPA, который выполнил
запрос и вернул результаты запроса.

Исходный код класса построителя спецификаций выглядит следующим образом:

```java
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersonSpecifications {

    public static Specification<Person> lastNameIsLike(final String searchTerm) {

        return new Specification<Person>() {
            @Override
            public Predicate toPredicate(Root<Person> personRoot,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
                String likePattern = getLikePattern(searchTerm);
                return cb.like(cb.lower(personRoot.<String>get(Person_.lastName)), likePattern);
            }

            private String getLikePattern(final String searchTerm) {
                return searchTerm.toLowerCase() + "%";
            }
        };
    }
}
```

Давайте посмотрим на тестовый код, который «проверяет», что класс строитель (builder) спецификаций создает «правильный»
запрос. Помните, что я написал этот тестовый класс, следуя своим собственным правилам, а это значит, что результат
должен быть отличным.

Исходный код класса `PersonSpecificationsTest` выглядит следующим образом:

```java
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PersonSpecificationsTest {

    private static final String SEARCH_TERM = "Foo";
    private static final String SEARCH_TERM_LIKE_PATTERN = "foo%";

    private CriteriaBuilder criteriaBuilderMock;

    private CriteriaQuery criteriaQueryMock;

    private Root<Person> personRootMock;

    @Before
    public void setUp() {
        criteriaBuilderMock = mock(CriteriaBuilder.class);
        criteriaQueryMock = mock(CriteriaQuery.class);
        personRootMock = mock(Root.class);
    }

    @Test
    public void lastNameIsLike() {
        Path lastNamePathMock = mock(Path.class);
        when(personRootMock.
                   get(Person_.lastName)).
                   thenReturn(lastNamePathMock);

        Expression lastNameToLowerExpressionMock = mock(Expression.class);
        when(criteriaBuilderMock.
                   lower(lastNamePathMock)).
                   thenReturn(lastNameToLowerExpressionMock);

        Predicate lastNameIsLikePredicateMock = mock(Predicate.class);
        when(criteriaBuilderMock.
                   like(lastNameToLowerExpressionMock, SEARCH_TERM_LIKE_PATTERN)).
                   thenReturn(lastNameIsLikePredicateMock);

        Specification<Person> actual = PersonSpecifications.lastNameIsLike(SEARCH_TERM);
        Predicate actualPredicate = actual.toPredicate(personRootMock, criteriaQueryMock, criteriaBuilderMock);

        verify(personRootMock, times(1)).get(Person_.lastName);
        verifyNoMoreInteractions(personRootMock);

        verify(criteriaBuilderMock, times(1)).lower(lastNamePathMock);
        verify(criteriaBuilderMock, times(1)).like(lastNameToLowerExpressionMock, SEARCH_TERM_LIKE_PATTERN);
        verifyNoMoreInteractions(criteriaBuilderMock);

        verifyZeroInteractions(criteriaQueryMock, lastNamePathMock, lastNameIsLikePredicateMock);

        assertEquals(lastNameIsLikePredicateMock, actualPredicate);
    }
}
```

**Есть ли в этом смысл? НЕТ!**

Я должен признать, что этот тест — кусок дерьма, не имеющий никакой ценности, и его следует удалить как можно скорее.

У этого теста есть **три основные проблемы**:
- Это **НЕ гарантирует, что запрос к базе данных возвращает правильные результаты**.
- Его **трудно читать**, и, что еще хуже, **он описывает, как строится запрос, но не описывает, что он должен возвращать**.
- **Подобные тесты сложно писать и поддерживать**.

На самом деле этот модульный тест представляет собой хрестоматийный пример теста, который никогда не следовало бы
писать. Оно не имеет для нас никакой ценности, но нам все равно приходится его поддерживать. Таким образом, это пустая
трата времени!

И все же именно это произойдет, **если мы напишем модульные тесты для нашего кода доступа к данным**. В итоге **мы получаем
набор тестов, который не проверяет нужные вещи**.

---
#### Тестирование доступа к данным выполнено правильно

Я большой поклонник модульного тестирования, но бывают ситуации, когда это НЕ ЛУЧШИЙ инструмент для работы. Это одна из
таких ситуаций.

Код доступа к данным имеет очень сильную связь с используемым хранилищем данных. Эта связь настолько сильна, что сам код
доступа к данным бесполезен без хранилища данных. Именно поэтому нет смысла изолировать наш код доступа к данным от
используемого хранилища данных.

**Решение этой проблемы простое.**

Если **мы хотим написать комплексные тесты для нашего кода доступа к данным, мы должны протестировать наш код доступа к
данным вместе с используемым хранилищем данных**. Это означает, что нам **придется забыть модульные тесты и начать писать
интеграционные тесты**.

Мы должны понимать, что **только интеграционные тесты могут подтвердить** что:
- *Наш код доступа к данным создает правильные запросы к базе данных*.
- *Наша база данных возвращает правильные результаты запроса*.

Если вы хотите узнать, как писать интеграционные тесты для репозиториев на базе Spring, прочтите эту статью 
["Интеграционное тестирование"](../SpringDataJPATutorial/16_IntegrationTesting.md). Тут рассказано, как писать 
интеграционные тесты для репозиториев Spring Data JPA. Однако вы можете использовать тот же метод при написании 
интеграционных тестов для любого репозитория, использующего реляционную базу данных. Например, интеграционный тест, 
написанный для проверки примера приложения из моего руководства [«Использование jOOQ с Spring» см.](https://www.petrikainulainen.net/using-jooq-with-spring/), 
использует технику, описанную в этой статье.

Краткое содержание:
- Мы узнали, что МОДУЛЬНЫЕ ТЕСТЫ НЕ МОГУТ помочь нам проверить правильность работы нашего кода доступа к данным,
  поскольку мы не можем гарантировать, что в наше хранилище данных вставлены правильные данные или что наши запросы
  возвращают правильные результаты.
- Мы узнали, что нам следует протестировать наш код доступа к данным с помощью интеграционных тестов, поскольку связь
  между нашим кодом доступа к данным и используемым хранилищем данных настолько тесна, что нет смысла их разделять.
