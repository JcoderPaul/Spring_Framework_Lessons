### Spring Boot Pagination & Filter

Одна из важных вещей, делающих веб-сайт дружелюбным - быстродействие, вторая - удобство интерфейса. Одним из
вариантов решения этих проблем одновременно является нумерация страниц. Например, на сайте есть сотни руководств,
и мы не хотим видеть их все сразу одним списком (не рационально, руководств может быть больше). Мы решаем получать
их, скажем, списками (порциями) по 10 шт. на станицу отображения. Для того чтобы просмотреть их все мы имеем некий
интерфейс с кнопками, например, отображающими номера этих списков и кнопки следующая и предыдущая.

Тут мы сталкиваемся с такими понятиями как:
- **Paging** - это загрузка одной страницы элементов из базы данных за другой с целью сохранения ресурсов.
- **Pagination** - это элемент пользовательского интерфейса, который предоставляет последовательность номеров страниц, позволяющую пользователю выбрать, какую страницу загружать следующей.

Предположим, что у нас в БД есть таблица учебных пособий (tutorials). Количество записей в таблице 15. Запрос к БД
данных через наше WEB-приложение может выглядеть следующим образом (полный URL сервера опустим):

- `/api/tutorials?page=1&size=5` - Тут мы запросили страницу (page) с индексом 1 (отсчет от 0) и указали количество
                                 записей на одну страницу (size) равный 5-и. Т.е. если мы имеем всего 15 записей,
                                 то при количестве записей на страницу равную 5-и, у нас получится 3-и страницы с
                                 индексами: 0, 1, 2

- `/api/tutorials?size=5` - Тут мы запросили тот же список, но для указания индекса страницы использовали значения по
                          умолчанию, т.е. не указали его явно (обычно это 0, и его желательно задать в настройках).
                          Размер или количество записей на странице (порция данных) задана равной 5-и.

- `/api/tutorials?title=data&page=1&size=3` - Тут уже присутствует фильтрация. Параметр title - заголовок книги в себе
                                            должен содержать слово 'data'; индекс страницы (page) запрошен равный - 1,
                                            количество записей (size) на странице задан - 5.

- `/api/tutorials/published?page=2` - Тут идет обращение или выборка (фильтрация) только по статусу «опубликовано» и
                                    задан индекс интересуемой страницы равный 2-ум, количество записей на странице
                                    не указан, подразумевается что это параметр задан в настройках по умолчанию.

И так мы видим, что для работы с разбиением полного списка записей из БД нам, как минимум, понадобятся следующие
параметры (переменные):
- **полное количество записей** (totalCount или другое название с той же смысловой нагрузкой) - запрашиваем из БД;
- **количество записей на одну страницу** (size или pageSize) - задаем и меняем сами;

Для навигации (перемещению) по всему списку, который мы уже разбили на порции могут понадобится переменные:
- **список пронумерованных страниц** - вычисляется и собирается в коллекцию, как вариант;
- **индекс последней страницы** (поскольку индекс первой обычно известен и равен 0) - вычисляется из величины полного списка проиндексированных страниц минус одни, т.к. индексация идет с 0;

Это тот минимум переменных 'для остраничивания', который позволит просмотреть весь список записей из интересующей нас
таблицы БД. Для реализации данного функционала в Spring Data JPA существуют пакет [org.springframework.data (в частности)](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/package-summary.html). Естественно он содержит намного более 
богатый набор классов, интерфейсов и переменных, чем те четыре, что мы предложили выше.

---
### Разбиение на страницы и фильтрация с помощью Spring Data JPA

Для реализации функционала разбиения данных на станицы, Spring Data JPA предоставляет нам [PagingAndSortingRepository](../PageFilterSort/PagingAndSortingRepository.md)
интерфейс. [PagingAndSortingRepository](../PageFilterSort/PagingAndSortingRepository.md) расширяет [CrudRepository](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html), предоставляя дополнительные методы для извлечения сущностей с использованием абстракции нумерации страниц см. [PagingAndSortingRepository](../PageFilterSort/PagingAndSortingRepository.md). Его метод findAll(Pageable pageable) - возвращает несколько Page сущностей, удовлетворяющих условию разбиения по
страницам, предоставленному объектом Pageable.

[Spring Data](https://spring.io/projects/spring-data) также поддерживает создание множества полезных запросов на основе имен методов, которые мы можем
использовать для фильтрации результатов, например:

```java
	Page<Tutorial> findByPublished(boolean published, Pageable pageable);
	Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
```

Дополнительные поддерживаемые ключевые слова внутри имен методов можно найти здесь - [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/#jpa.query-methods.query-creation)

---
### Spring Data интерфейсы Page и Slice

Чем будет нам полезен объект [Page](../PageFilterSort/PageInterface.md)?

[Page](../PageFilterSort/PageInterface.md) это подинтерфейс [Slice](../PageFilterSort/SliceInterface.md) с парой дополнительных методов. Главное то, что он содержит методы по получению общего
количество элементов и общего количество страниц всего списка см. [Page](../PageFilterSort/PageInterface.md):

```java
	public interface Page<T> extends Slice<T> {
	  static <T> Page<T> empty();
	  static <T> Page<T> empty(Pageable pageable);
	  long getTotalElements();
	  int getTotalPages();
	  <U> Page<U> map(Function<? super T,? extends U> converter);
	}
```

Если мы столкнемся с очень большим количеством элементов в БД, это может повлиять на производительность, в этом случае
нам поможет [Slice](../PageFilterSort/SliceInterface.md). Объект [Slice](../PageFilterSort/SliceInterface.md) знает меньше 
информации, чем [Page](../PageFilterSort/PageInterface.md), например, доступен или нет следующий или предыдущий фрагмент 
полного списка, или этот срез является первым/последним. Мы можем использовать его, когда нам не нужно общее количество 
элементов и общее количество страниц.

```java
	public interface Slice<T> extends Streamable<T> {
	  int getNumber();
	  int getSize();
	  int getNumberOfElements();
	  List<T> getContent();
	  boolean hasContent();
	  Sort getSort();
	  boolean isFirst();
	  boolean isLast();
	  boolean hasNext();
	  boolean hasPrevious();
	  ...
	}
```

---
### Spring Data интерфейс Pageable

Мы видели параметр [Pageable](../PageFilterSort/PageableInterface.md) в методах репозитория см. выше. Инфраструктура 
[Spring Data](https://spring.io/projects/spring-data) автоматически распознает этот параметр, чтобы применить разбивку 
на страницы и сортировку к базе данных.

Интерфейс [Pageable](../PageFilterSort/PageableInterface.md) содержит информацию о запрошенной странице, такую как размер и номер страницы.

```java
	public interface Pageable {
	  int getPageNumber();
	  int getPageSize();
	  long getOffset();
	  Sort getSort();
	  Pageable next();
	  Pageable previousOrFirst();
	  Pageable first();
	  boolean hasPrevious();
	  ...
	}
```

Поэтому, когда мы хотим получить нумерацию страниц (с фильтром или без него) в результатах, мы просто передаем параметр
[Pageable](../PageFilterSort/PageableInterface.md) в метод, например так:

```java
	Page<Tutorial> findAll(Pageable pageable);
	Page<Tutorial> findByPublished(boolean published, Pageable pageable);
	Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
```

Сам же [Pageable](../PageFilterSort/PageableInterface.md) объект мы можем создать, используя класс [PageRequest](../PageFilterSort/PageRequestClass.md), реализующий Pageable интерфейс:

```java
	Pageable paging = PageRequest.of(page, size);
```

Где: 
- page - индекс страницы, начинающийся с нуля, НЕ должен быть отрицательным;
- size - количество возвращаемых элементов на странице должно быть больше 0;

---
### Репозиторий, поддерживающий нумерацию страниц и фильтрацию

В начале статьи мы указали на [PagingAndSortingRepository](/PageFilterSort/PagingAndSortingRepository.md), но далее, чтобы сохранить непрерывность мы будем использовать
преимущества [Spring Data JPA](https://spring.io/projects/spring-data-jpa). Мы применим [JpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html), который расширяет PagingAndSortingRepository интерфейс см. [UserRepository.java](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/java/spring/oldboy/database/repository/user_repository/UserRepository.java)

Если мы хотим вернуть [Page](../PageFilterSort/PageInterface.md) (страницу или [Slice](../PageFilterSort/SliceInterface.md) - фрагмент) элементов в веб-контроллере, 
ему необходимо принять параметр [Pageable](/PageFilterSort/PageableInterface.md), который определяет параметры подкачки, передать его в базу данных, а затем вернуть 
объект Page (страница) клиенту.

---
### Активация веб-поддержки Spring Data

Разбиение на страницы должно поддерживаться базовым уровнем персистентности (степень сохранения данных или состояния системы), 
чтобы доставлять постраничные ответы на любые запросы. Вот почему классы Pageable и Page происходят из модуля Spring Data, а не, 
как можно было бы подозревать, из модуля Spring Web.

В приложении Spring Boot с включенной автоконфигурацией (используется по умолчанию) нам не нужно ничего делать,
поскольку по умолчанию оно загружает [SpringDataWebAutoConfiguration](https://docs.spring.io/spring-boot/3.4/api/java/org/springframework/boot/autoconfigure/data/web/SpringDataWebAutoConfiguration.html), которое включает аннотацию [@EnableSpringDataWebSupport](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/config/EnableSpringDataWebSupport.html), загружающую необходимые bean-компоненты.

В простом приложении Spring без Spring Boot нам придется самостоятельно использовать [@EnableSpringDataWebSupport](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/config/EnableSpringDataWebSupport.html) в классе [@Configuration](https://docs.spring.io/spring-framework/reference/core/beans/java/configuration-annotation.html):

```java
	@Configuration
	@EnableSpringDataWebSupport
	class PaginationConfiguration {
	}
```

Если мы используем аргументы Pageable или Sort в методах веб-контроллера без активации веб-поддержки Spring Data, мы
получим такие исключения:

```
	java.lang.NoSuchMethodException: org.springframework.data.domain.Pageable.<init>()
	java.lang.NoSuchMethodException: org.springframework.data.domain.Sort.<init>()
```

Эти исключения означают, что Spring пытается создать экземпляр Pageable или Sort и терпит неудачу, поскольку у них нет
конструктора по умолчанию. Это исправляется веб-поддержкой Spring Data, поскольку она добавляет в контекст приложения
bean-компоненты [PageableHandlerMethodArgumentResolver](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/PageableHandlerMethodArgumentResolver.html) и [SortHandlerMethodArgumentResolver](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/web/SortHandlerMethodArgumentResolver.html), которые отвечают за поиск
аргументов метода веб-контроллера типов Pageable и Sort и заполнение их значениями страницы, размера и сортировка параметров запроса.

---
### Постраничный вывод данных

Мы помним, что наше приложение имеет несколько слоев. Наш слой контроллеров обрабатывает запрос полученный от
пользователя через web-интерфейс и передает параметры запроса 'вниз по слоям' к БД. Значит именно в запросе пользователя
мы должны получить параметры для объекта Pageable и Filter. Тут мы не рассматриваем фильтрацию подробно, но и ее
пользователь может передать в качестве параметра в запросе.

Сам объект и параметры для Pageable мы можем запросить у Spring Data и по умолчанию получим page = 0 и size = 20 см.
ниже.

```java
	public abstract class PageableHandlerMethodArgumentResolverSupport {
	    . . .
		private static final String DEFAULT_PAGE_PARAMETER = "page";
		private static final String DEFAULT_SIZE_PARAMETER = "size";
		private static final String DEFAULT_PREFIX = "";
		private static final String DEFAULT_QUALIFIER_DELIMITER = "_";
		private static final int DEFAULT_MAX_PAGE_SIZE = 2000;
		static final Pageable DEFAULT_PAGE_REQUEST = PageRequest.of(0, 20);
	    . . .
	}
```

Поэтому наши объекты для фильтрации и пагинации мы передаем в метод контролера и далее вниз по цепочке до БД.
Чтобы иметь возможность самим задавать параметры page и size мы изменим нашу форму запросов: [users_with_pagination.html](https://github.com/JcoderPaul/Spring_Framework_Lessons/blob/master/Spring_part_17/src/main/resources/templates/user/users_with_pagination.html). При этом когда мы первый раз обратимся к этой форме, сможем
увидеть что поля формы для пагинации уже заполнены 'by default'.

---
**Доп. материалы:**
- [Paging and Sorting (Spring Docs)](https://docs.spring.io/spring-data/rest/reference/paging-and-sorting.html)
- [Implementing Pagination, Sorting, and Filtering in Spring Boot](https://medium.com/devxtalks/implementing-pagination-sorting-and-filtering-in-spring-boot-42615dbd74a7)
- [Pagination and Filtering Spring Boot](https://dev.to/devcorner/pagination-and-filtering-spring-boot-1c7a)
- [Pagination and Sorting using Spring Data JPA](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
- [How to filter a PageRequest in spring](https://stackoverflow.com/questions/42959258/how-to-filter-a-pagerequest-in-spring)
- [Spring Boot Pagination & Filter example | Spring JPA, Pageable](https://www.bezkoder.com/spring-boot-pagination-filter-jpa-pageable/)
- [GitHub - Spring Boot Pagination, Filter, Sorting example | Spring JPA, Pageable](https://github.com/bezkoder/spring-boot-jpa-paging-sorting)
- [Part 7: Pagination, Sorting, and Filtering — From Zero to Hero: Building a Full-Stack CRUD Application using Java Spring Boot and React](https://medium.com/@theshikanavod/part-7-pagination-sorting-and-filtering-from-zero-to-hero-building-a-full-stack-crud-050a7cf4d8ac)
- [Pagination and Sorting with Spring Data JPA](https://www.geeksforgeeks.org/advance-java/pagination-and-sorting-with-spring-data-jpa/)
- [A Comprehensive Guide to Pagination and Sorting in Spring Boot Applications](https://blog.stackademic.com/a-comprehensive-guide-to-pagination-and-sorting-in-spring-boot-applications-ec4311576f94)
- [Pagination(with Hateoas), Filtering & Sorting with Spring Boot and JPA](https://www.springcloud.io/post/2022-04/hateoas-spring-boot-and-jpa/#gsc.tab=0)
- [Building a Product Catalog API with Pagination, Filtering & Sorting using Spring Boot](https://hackernoon.com/building-a-product-catalog-api-with-pagination-filtering-and-sorting-using-spring-boot)
- [Implement Pagination in your Spring Boot application](https://ardijorganxhi.medium.com/implement-pagination-at-your-spring-boot-application-a540270b5f60)
- [GraphQL backend — pagination & filters](https://mahendranv.github.io/posts/gql-filters-pagination/)
- [Spring Boot Pagination and Sorting Example](https://howtodoinjava.com/spring-data/pagination-sorting-example/)
- [Create a Spring Endpoint with Filtering, Sorting and Paging](https://www.blog.nilestanner.com/2019/02/05/spring-filter-sort-page/)
