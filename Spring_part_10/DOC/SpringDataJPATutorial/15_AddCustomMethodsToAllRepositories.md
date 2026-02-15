- [Исходник статьи (ENG)](https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-adding-custom-methods-into-all-repositories/)
- [Код на GitHub](https://github.com/pkainulainen/spring-data-jpa-examples/tree/master)

---
- [См. настройка Spring проекта](https://start.spring.io/)

---
### Spring Data JPA Tutorial: Adding Custom Methods to All Repositories - Добавление пользовательских методов во все репозитории

[В предыдущей части](./14_AddCustomMethodsToSingleRepository.md) мы научились добавлять собственные методы в один репозиторий. 
Хотя это очень полезный навык, он нам не поможет, когда нам придется добавить один и тот же метод во все репозитории нашего 
приложения.

К счастью для нас, Spring Data предоставляет возможность добавлять собственные методы во все репозитории.

---
#### Создание интерфейса базового репозитория

Когда мы **хотим добавить пользовательские методы во все репозитории [`Spring Data JPA`](https://spring.io/projects/spring-data-jpa)**, первое, что нам нужно - это **создать
базовый интерфейс**, который объявляет пользовательские методы.

Мы можем **создать интерфейс** базового репозитория, выполнив следующие шаги:
- **Шаг 1.** Создадим интерфейс `BaseRepository` со следующими параметрами типа:
            - Параметр типа `<T>` - это **тип управляемого объекта**.
            - Параметр типа *идентификатора ID* - это **тип первичного ключа управляемого объекта**. Обратим внимание, что этот параметр типа должен расширять интерфейс `Serializable`.
- **Шаг 2.** Расширим интерфейс репозитория и укажем необходимые параметры типа.
- **Шаг 3.** Аннотируем созданный интерфейс аннотацией `@NoRepositoryBean`. Это гарантирует, что `Spring Data JPA` не попытается создать реализацию интерфейса `BaseRepository`.
- **Шаг 4.** Добавим метод `deleteById()` в созданный интерфейс. Эти методы принимают идентификатор удаленного объекта в качестве параметра метода и возвращают объект `Optional<T>`.

Исходный код интерфейса `BaseRepository` выглядит следующим образом:

```java
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

    Optional<T> deleteById(ID id);
}
```

Помним, что интерфейс базового репозитория должен расширять тот же интерфейс, который расширяется интерфейсами нашего
репозитория. Другими словами, если интерфейсы нашего репозитория расширяют интерфейс `CrudRepository`, интерфейс
`BaseRepository` также должен расширять его.

После того, как мы создали наш базовый интерфейс репозитория, нам, естественно, нужно его реализовать. Давайте выясним,
как мы можем создать базовый класс репозитория, который удаляет запрошенную сущность с помощью `EntityManager`.

---
#### Реализация интерфейса базового репозитория

`SimpleJpaRepository` - это реализация по умолчанию интерфейсов репозитория `Spring Data JPA`. Поскольку мы хотим добавить
методы, объявленные нашим базовым интерфейсом репозитория, во все репозитории, нам необходимо создать собственный класс
базового репозитория, который расширяет класс `SimpleJpaRepository` и реализует интерфейс `BaseRepository`.

Мы можем **создать собственный базовый класс** репозитория, выполнив следующие действия:
- Создаем класс `BaseRepositoryImpl` с двумя параметрами типа:
    - Параметр типа `<T>` - это **тип управляемого объекта**.
    - Параметр типа идентификатора `ID` - это **тип первичного ключа управляемого объекта**. Обратим внимание, что этот параметр типа должен расширять интерфейс `Serializable`.
- Убедимся, что класс `BaseRepositoryImpl` расширяет класс `SimpleJpaRepository` и реализует интерфейс `BaseRepository`. Не забудем указать необходимые параметры типа.
- Добавим *private* поле `EntityManager` в созданный класс и отметим его как `final`.
- Добавим *конструктор*, который принимает два аргумента конструктора:
    - Объект `Class`, представляющий класс управляемой сущности.
    - Объект `EntityManager`.
- Реализуем конструктор, вызвав конструктор суперкласса - `SimpleJpaRepository` и сохранив ссылку на объект `EntityManager` в закрытом поле *EntityManager*.
- Добавим метод `deleteById()` в созданный класс и реализуем его, выполнив следующие шаги:
    - **Шаг 1.** Аннотируем метод аннотацией `@Transactional`. Это гарантирует, что метод всегда вызывается внутри транзакции чтения-записи.
    - **Шаг 2.** Найдем удаленный объект сущности, используя предоставленный идентификатор в качестве критерия поиска.
    - **Шаг 3.** Если объект сущности найден, удалим найденный объект сущности и вернем Optional объект, содержащий удаленный объект сущности.
    - **Шаг 4.** Если объект сущности не найден, вернем пустой Optional объект.

Исходный код класса `BaseRepositoryImpl` выглядит следующим образом:

```java
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;

public class BaseRepositoryImpl <T, ID extends Serializable> extends SimpleJpaRepository<T, ID>  implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public Optional<T> deleteById(ID id) {
        T deleted = entityManager.find(this.getDomainClass(), id);
        Optional<T> returned = Optional.empty();

        if (deleted != null) {
            entityManager.remove(deleted);
            returned = Optional.of(deleted);
        }
        return returned;
    }
}
```

---
**Дополнительное чтение:**
- [Javadoc класса SimpleJpaRepository](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/support/SimpleJpaRepository.html)
- [Javadoc интерфейса EntityManager](https://docs.oracle.com/javaee/6/api/javax/persistence/EntityManager.html)

---
После того, как мы создали наш базовый класс репозитория, нам нужно создать собственный `RepositoryFactoryBean`.

Если мы используем *Spring Data JPA 1.9.X* или новее, мы можем пропустить этот шаг, поскольку создание
`RepositoryFactoryBean` больше не является обязательным (мы все равно можем это сделать, если хотим).

---
#### Создание пользовательского RepositoryFactoryBean

`RepositoryFactoryBean` - это компонент, который отвечает за реализацию интерфейсов репозитория `Spring Data JPA`.
Поскольку мы хотим заменить реализацию по умолчанию *SimpleJpaRepository* на нашу собственную реализацию - **BaseRepositoryImpl**, 
нам **нужно создать собственный RepositoryFactoryBean**.

Мы можем сделать это, выполнив следующие шаги:
- **Шаг 1.** Создадим класс `BaseRepositoryFactoryBean` с тремя параметрами типа:
  - Параметр типа `<R>` - это **тип репозитория**. Этот параметр типа должен расширять интерфейс `JpaRepository`.
  - Параметр типа `<T>` - это **тип управляемого объекта**.
  - Параметр `<I> type` - это **тип закрытого ключа объекта**. Обратим внимание, что этот параметр типа должен расширять интерфейс `Serializable`.
- **Шаг 2.** Расширим класс `JpaRepositoryFactoryBean` и укажем необходимые параметры типа.
- **Шаг 3.** Добавим *private static* класс `BaseRepositoryFactory` в созданный класс и расширим класс `JpaRepositoryFactory`.

Реализуем этот класс, выполнив следующие шаги:
- **Шаг 3.1.** Добавим в класс `BaseRepositoryFactory` два параметра типа:
            - Параметр типа `<T>` — это **тип управляемого объекта**.
            - Параметр `<I> type` — это **тип закрытого ключа объекта**. Обратим внимание, что этот параметр типа должен расширять интерфейс `Serializable`.
- **Шаг 3.2.** Добавим *private final* поле `EntityManager` в класс `BaseRepositoryFactory` и отметим это поле как *final*.
- **Шаг 3.3.** Добавим конструктор, который принимает объект `EntityManager` в качестве аргумента конструктора, и реализуем его, выполнив следующие действия:
  - **Шаг 3.3.1** Вызовем конструктор суперкласса и передадим объект `EntityManager` в качестве аргумента конструктора.
  - **Шаг 3.3.2** Сохраним ссылку на объект `EntityManager` в *private* поле `EntityManager`.
- **Шаг 3.4.** Переопределим метод `getTargetRepository`(метаданные RepositoryMetadata) и реализуем его, вернув новый объект `BaseRepositoryImpl`.
- **Шаг 3.5.** Переопределим метод `getRepositoryBaseClass`(RepositoryMetadata метаданные) и реализуем его, вернув `BaseRepositoryImpl.class`
- **Шаг 4.** Переопределим метод `createRepositoryFactory(EntityManager em)` класса `JpaRepositoryFactoryBean` и реализуем его, вернув новый объект `BaseRepositoryFactory`.

Исходный код класса `BaseRepositoryFactoryBean` выглядит следующим образом:

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseRepositoryFactory(em);
    }

    private static class BaseRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager em;

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

        @Override
        protected Object getTargetRepository(RepositoryMetadata metadata) {
            return new BaseRepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), em);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }
}
```

---
**Дополнительное чтение:**
- [Javadoc класса JpaRepositoryFactoryBean](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/support/JpaRepositoryFactoryBean.html)
- [Javadoc класса JpaRepositoryFactory](https://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/support/JpaRepositoryFactory.html)

---
Давайте выясним, как нам нужно настроить `Spring Data JPA` для использования нашего специального `RepositoryFactoryBean`.

---
#### Настройка Spring Data JPA

Мы можем настроить Spring Data JPA, используя один из следующих способов:

---
#### Настройка Spring Data JPA при использовании Spring Data JPA < 1.9.X

Если мы используем Spring Data JPA < 1.9.X, мы можем настроить фабричный компонент репозитория, установив значение атрибута `repositoryFactoryBeanClass` аннотации `@EnableJpaRepositories`.

Соответствующая часть класса `PersistenceContext` выглядит следующим образом:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(
                        basePackages = {"net.petrikainulainen.springdata.jpa.todo"},
                        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class
)
@EnableTransactionManagement
class PersistenceContext {

}
```

Если мы используем конфигурацию *XML*, мы можем настроить используемый `RepositoryFactoryBean`, установив значение атрибута Factory-Class элемента репозитория.

---
#### Настройка Spring Data JPA при использовании Spring Data JPA 1.9.X или новее

Если мы используем Spring Data JPA 1.9.X или новее, нам НЕ нужно создавать класс `RepositoryFactoryBean`. Мы можем просто
настроить базовый класс репозитория, установив значение атрибута `repositoryBaseClass` аннотации `@EnableJpaRepositories`.

Соответствующая часть класса `PersistenceContext` выглядит следующим образом:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableJpaRepositories(
                        basePackages = {"net.petrikainulainen.springdata.jpa.todo"},
                        repositoryBaseClass = BaseRepositoryImpl.class
)
@EnableTransactionManagement
class PersistenceContext {

}
```

Если мы используем конфигурацию `XML`, мы можем настроить базовый класс репозитория, установив значение атрибута
*repository element's repository-base-class* элемента репозитория.

Теперь мы готовы добавить новый метод `deleteById()` в интерфейсы нашего репозитория.

---
#### Изменение реальных интерфейсов репозитория

Прежде чем мы сможем использовать наш новый метод `deleteById()`, нам необходимо внести некоторые изменения в интерфейсы
нашего репозитория. Мы можем внести эти изменения в интерфейс `TodoRepository`, выполнив следующие действия:
- Расширим интерфейс `BaseRepository` и укажем следующие параметры типа:
    - *Тип управляемой сущности* - `Todo`.
    - *Тип закрытого ключа* объекта - `Long`.
- Удалим «старый» метод delete().

Исходный код интерфейса `TodoRepository` выглядит следующим образом:

```java
import net.petrikainulainen.springdata.jpa.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


interface TodoRepository extends BaseRepository<Todo, Long> {

    List<Todo> findAll();

    @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%')) " +
            "ORDER BY t.title ASC")
    List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm);

    Optional<Todo> findOne(Long id);

    void flush();

    Todo save(Todo persisted);
}
```

Вот и все. Теперь мы можем использовать наш новый метод `deleteById()`.

---
#### Подведем итог:
- Если мы хотим добавить собственные методы во все репозитории, нам придется заменить реализацию репозитория по
  умолчанию **SimpleJpaRepository** нашей собственной реализацией репозитория.
- Если мы используем Spring Data JPA 1.9.X или новее, нам не нужно создавать собственный `RepositoryFactoryBean`.
- Наши интерфейсы репозитория должны расширять базовый интерфейс репозитория, который объявляет методы, добавляемые во все репозитории.
