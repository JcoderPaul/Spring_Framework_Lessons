- [Исходная статья см. (ENG)](https://www.baeldung.com/spring-data-jpa-projections)
- [Исходный код GitHub](https://github.com/eugenp/tutorials/tree/master/persistence-modules/spring-data-jpa-filtering)

---
### Spring Data JPA Projections

При использовании **Spring Data JPA для реализации уровня персистентности репозиторий обычно возвращает один или
несколько экземпляров корневого класса**. Однако **чаще всего нам не нужны все свойства возвращаемых объектов**.

В таких случаях нам может потребоваться получить данные как объекты настраиваемых типов. Эти типы отражают
частичные представления корневого класса, содержащие только те свойства, которые нас интересуют. Вот тут-то
и пригодятся проекции.

Первым шагом является настройка проекта и заполнение базы данных, и настройка зависимостей.

---
### Классы сущностей для наших примеров

Давайте определим два класса сущностей:

```Java
    @Entity
    public class Address {
    
        @Id
        private Long id;
    
        @OneToOne
        private Person person;
    
        private String state;
    
        private String city;
    
        private String street;
    
        private String zipCode;
    
        /* getters and setters */
    }
```

и

```Java
    @Entity
    public class Person {
    
        @Id
        private Long id;
    
        private String firstName;
    
        private String lastName;
    
        @OneToOne(mappedBy = "person")
        private Address address;
    
        /* getters and setters */
    }
```

Отношения между сущностями Person и Address являются двунаправленными, один к одному (OneToOne);
Address (адрес) - это сторона хозяин, собственник (owning side), а Person (персона) - обратная сторона (inverse side).

Обратите внимание, что тут, для примера мы используем встроенную базу данных H2. Когда встроенная база данных настроена,
Spring Boot автоматически генерирует базовые таблицы для определенных нами сущностей.

---
### SQL-скрипты

Мы воспользуемся сценарием `Projection-insert-data.sql` для заполнения обеих вспомогательных таблиц:

```SQL
    INSERT INTO person(id,first_name,last_name) VALUES (1,'John','Doe');
    INSERT INTO address(id,person_id,state,city,street,zip_code) VALUES (1,1,'CA', 'Los Angeles', 'Standford Ave', '90001');
```

Чтобы очистить базу данных после каждого запуска теста, мы можем использовать другой скрипт - `projection-clean-up-data.sql`:

```SQL
    DELETE FROM address;
    DELETE FROM person;
```

---
### Тестовый класс

Затем, чтобы подтвердить, что проекции дают правильные данные, нам нужен тестовый класс:

```Java
    @DataJpaTest
    @RunWith(SpringRunner.class)
    @Sql(scripts = "/projection-insert-data.sql")
    @Sql(scripts = "/projection-clean-up-data.sql", executionPhase = AFTER_TEST_METHOD)
    public class JpaProjectionIntegrationTest {
        /* injected fields and test methods */
    }
```

Используя данные аннотации, Spring Boot создает базу данных, внедряет зависимости, а также заполняет и очищает таблицы
до и после выполнения каждого метода тестирования.

---
### Проекции на основе интерфейса

При проектировании сущности естественно полагаться на интерфейс, поскольку нам не нужно предоставлять реализацию.

---
#### Закрытые проекции

Оглядываясь назад на класс Address, мы видим, что у него много свойств, но не все из них полезны. Например, иногда
для указания адреса достаточно почтового индекса.

Давайте объявим интерфейс проекции для класса Address:

```Java
    public interface AddressView {
        String getZipCode();
    }
```

Затем мы будем использовать его в интерфейсе репозитория:

```Java
    public interface AddressRepository extends Repository<Address, Long> {
        List<AddressView> getAddressByState(String state);
    }
```

Легко заметить, что определение метода репозитория с помощью интерфейса проекции практически такое же, как и с классом
сущности.

Единственное отличие состоит в том, что в качестве типа элемента в возвращаемой коллекции используется интерфейс проекции,
а не класс сущности.

Давайте быстро проверим проекцию адреса:

```Java
    @Autowired
    private AddressRepository addressRepository;
    
    @Test
    public void whenUsingClosedProjections_thenViewWithRequiredPropertiesIsReturned() {
        AddressView addressView = addressRepository.getAddressByState("CA").get(0);
        assertThat(addressView.getZipCode()).isEqualTo("90001");
        // ...
    }
```

За кулисами Spring создает прокси-экземпляр интерфейса проекции для каждого объекта сущности, и все вызовы прокси
перенаправляются на этот объект.

Мы можем использовать проекции рекурсивно. Например, вот интерфейс проекции для класса Person:

```Java
    public interface PersonView {
        String getFirstName();
    
        String getLastName();
    }
```

Теперь мы добавим метод с типом возвращаемого значения PersonView, вложенную проекцию, в проекцию Address:

```Java
    public interface AddressView {
        // ...
        PersonView getPerson();
    }
```

---
**!!! Обратите внимание !!!**

Метод, возвращающий вложенную проекцию, должен иметь то же имя, что и метод корневого класса, возвращающий связанную сущность.

---
Мы проверим вложенные проекции, добавив несколько операторов в только что написанный метод тестирования:

```Java
    // ...
    PersonView personView = addressView.getPerson();
    assertThat(personView.getFirstName()).isEqualTo("John");
    assertThat(personView.getLastName()).isEqualTo("Doe");
```

---
**!!! Обратите внимание !!!** 

Рекурсивные проекции работают только в том случае, если мы переходим от владеющей стороны к обратной стороне. 
Если мы сделаем это наоборот, вложенной проекции будет присвоено значение null.

---
#### Открытые проекции

До этого момента мы рассмотрели закрытые проекции, обозначающие интерфейсы проекций, методы которых точно соответствуют
именам свойств сущности.

Существует еще один вид проекции на основе интерфейса - открытые проекции. Эти проекции позволяют нам определять методы
интерфейса с несовпадающими именами и возвращаемыми значениями, вычисляемыми во время выполнения.

Вернемся к интерфейсу проекции Person и добавим новый метод:

```Java
    public interface PersonView {
        // ...
    
        @Value("#{target.firstName + ' ' + target.lastName}")
        String getFullName();
    }
```

Аргументом аннотации @Value является выражение SpEL, в котором указатель цели указывает поддерживающий объект сущности.

Теперь мы определим еще один интерфейс репозитория:

```Java
    public interface PersonRepository extends Repository<Person, Long> {
        PersonView findByLastName(String lastName);
    }
```

Для простоты мы будем возвращать только один объект проекции вместо коллекции.

Этот тест подтверждает, что открытые проекции работают должным образом:

```Java
    @Autowired
    private PersonRepository personRepository;
    
    @Test
    public void whenUsingOpenProjections_thenViewWithRequiredPropertiesIsReturned() {
    
        PersonView personView = personRepository.findByLastName("Doe");
    
        assertThat(personView.getFullName()).isEqualTo("John Doe");
    }
```

---
**!!! Однако, у открытых проекций есть недостаток !!!** 

Spring Data не может оптимизировать выполнение запроса, поскольку заранее не знает, какие свойства будут использоваться. 
Таким образом, нам следует использовать открытые проекции только в том случае, если закрытые проекции не способны 
удовлетворить наши требования.

---
### Проекции на основе классов

Вместо использования прокси, которые Spring Data создает из интерфейсов проекции, мы можем определить наши собственные
классы проекции.

Например, вот класс проекции для сущности Person:

```Java
    public class PersonDto {
        private String firstName;
        private String lastName;
    
        public PersonDto(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    
        /* getters, equals and hashCode */
    }
```

Чтобы класс проекции работал в тандеме с интерфейсом репозитория, имена параметров его конструктора должны
соответствовать свойствам корневого класса сущности. Мы также должны определить реализации Equals и hashCode - они
позволяют Spring Data обрабатывать объекты проекции в коллекции.

Теперь добавим метод в репозиторий Person:

```Java
    public interface PersonRepository extends Repository<Person, Long> {
        // ...
    
        PersonDto findByFirstName(String firstName);
    }
```

Этот тест проверяет нашу проекцию на основе классов:

```Java
    @Test
    public void whenUsingClassBasedProjections_thenDtoWithRequiredPropertiesIsReturned() {
        PersonDto personDto = personRepository.findByFirstName("John");
    
        assertThat(personDto.getFirstName()).isEqualTo("John");
        assertThat(personDto.getLastName()).isEqualTo("Doe");
    }
```

---
**!!! Обратите внимание !!!** 

При подходе на основе классов мы не можем использовать вложенные проекции.

---
### Динамические проекции

**Класс сущности может иметь множество проекций.** В некоторых случаях мы можем использовать определенный тип, но в других
случаях нам может понадобиться другой тип. Иногда нам также необходимо использовать сам класс сущности.

**Определение отдельных интерфейсов или методов репозитория только для поддержки нескольких типов возврата является
обременительным. Чтобы справиться с этой проблемой, Spring Data предлагает лучшее решение - динамические проекции.**

Мы можем применять динамические проекции, просто объявив метод репозитория с параметром класса:

```Java
    public interface PersonRepository extends Repository<Person, Long> {
        // ...
    
        <T> T findByLastName(String lastName, Class<T> type);
    }
```

Передавая такому методу тип проекции или класс сущности, мы можем получить объект нужного типа:

```Java
    @Test
    public void whenUsingDynamicProjections_thenObjectWithRequiredPropertiesIsReturned() {
        Person person = personRepository.findByLastName("Doe", Person.class);
        PersonView personView = personRepository.findByLastName("Doe", PersonView.class);
        PersonDto personDto = personRepository.findByLastName("Doe", PersonDto.class);
    
        assertThat(person.getFirstName()).isEqualTo("John");
        assertThat(personView.getFirstName()).isEqualTo("John");
        assertThat(personDto.getFirstName()).isEqualTo("John");
    }
```
