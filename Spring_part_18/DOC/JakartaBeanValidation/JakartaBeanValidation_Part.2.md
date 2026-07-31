- [См. исходник (RUS)](https://alexkosarev.name/2023/07/20/objects-validation-in-jakarta-bean-validation/)
- [Примеры кода на GitHub](https://github.com/alex-kosarev)

---
### Валидация объектов - Jakarta Bean Validation

В этой статье, посвящённой Jakarta Bean Validation, будет подробно рассмотрен процесс валидации объектов: применение
ограничений к элементам классов, их валидация, а также валидация записей (record).

---
#### Ограничения

В рамках классов ограничения могут применяться к полям, свойствам и самим классам. Кроме этого ограничения суммируются
при наследовании.

---
#### Ограничения полей

При работе с объектами возможна валидация полей и свойств классов, конкретных значений и объектов целиком. Примеры
применения ограничений будут продемонстрированы ниже при помощи аннотаций и XML, использовать одновременно и
аннотации, и XML не нужно, старайтесь выбрать какой-то один подход, наиболее удобный для вас.

При использовании ограничения поля аннотация ограничения указывается непосредственно у объявленного поля класса:

```java
    class Candidate {
    
        @Min(18)
        int age;
    
        @NotNull
        Gender gender;
    }
```

При помощи аннотации `@Min` задано ограничение, согласно которому значение поля `age` не должно быть меньше 18, про данное
ограничение более подробно будет написано ниже в этой статье.

В XML ограничения полей задаются в блоке <field>:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<constraint-mappings
        xmlns="https://jakarta.ee/xml/ns/validation/mapping"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/validation/mapping
            https://jakarta.ee/xml/ns/validation/validation-mapping-3.0.xsd"
        version="3.0">

  <bean class="Candidate">
      <field name="age">
          <constraint annotation="jakarta.validation.constraints.Min">
              <element name="value">18</element>
          </constraint>
      </field>
      <field name="gender">
          <constraint annotation="jakarta.validation.constraints.NotNull">
              <element name="value">18</element>
          </constraint>
      </field>
  </bean>

</constraint-mappings>
```

---
#### Ограничения свойств

Если класс соответствует спецификации Java Beans, то ограничение можно применить к методу `get...`:

```java
class Candidate {

    private int age;

    private Gender gender;

    @Min(18)
    int getAge() {
        return this.age;
    }

    @NotNull
    Gender getGender() {
        return this.gender
    }
}
```

Применение аннотаций ограничений к `get…` - методам может вызывать некоторую путаницу, так как применение аннотаций
ограничений к возвращаемым значениям методов и аннотаций перекрёстных ограничений к аргументам методов выглядит
схожим образом, что будет показано ниже.

В XML ограничение свойства задаётся в блоке `<getter>` и не вызывает такой путаницы:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<constraint-mappings
        xmlns="https://jakarta.ee/xml/ns/validation/mapping"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/validation/mapping
            https://jakarta.ee/xml/ns/validation/validation-mapping-3.0.xsd"
        version="3.0">

  <bean class="Candidate">
      <getter name="age">
          <constraint annotation="jakarta.validation.constraints.Min">
              <element name="value">18</element>
          </constraint>
      </getter>
      <getter name="gender">
          <constraint annotation="jakarta.validation.constraints.NotNull">
              <element name="value">18</element>
          </constraint>
      </getter>
  </bean>

</constraint-mappings>
```

---
#### Ограничения класса

Jakarta Bean Validation предоставляет возможность использования ограничений для классов. Это может быть полезным, когда
используется какая-то сложная логика валидации, зависящая от значений свойств объекта. Каких-то стандартных ограничений
для классов спецификация не предоставляет.

Ограничение класса задаётся аннотацией для валидируемого класса:

```java
@ValidCandidate
class Candidate {
}
```

То же самое может быть реализовано в XML при помощи `<class>`:

```java
<?xml version="1.0" encoding="UTF-8" ?>
<constraint-mappings
        xmlns="https://jakarta.ee/xml/ns/validation/mapping"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/validation/mapping
            https://jakarta.ee/xml/ns/validation/validation-mapping-3.0.xsd"
        version="3.0">

  <bean class="Candidate">
      <class>
          <constraint annotation="ValidCandidate"/>
      </class>
  </bean>
</constraint-mappings>
```

`@ValidCandidate` - это собственное ограничение. Создание и использование собственных ограничений будет рассмотрено в
отдельной статье.

---
#### Ограничения элементов контейнера (коллекций)

Jakarta Bean Validation позволяет задавать ограничения для объектов, находящихся в контейнерах. Поддерживаются следующие
виды контейнеров:
- Наследники и реализации java.util.Iterable, включая List, Set, Queue и т.д;
- Наследники и реализации java.util.Map;
- java.util.Optional и родственные классы;
- и некоторые другие;

Для использования ограничения к элементам контейнеров аннотацию нужно добавить к типу обобщения (дженерика) контейнера:

```java
class CandidatesGroup {
    List<@NotNull Candidate> candidates;
}
```

Ограничение `@NotNull` указывает на то, что объект не должен быть `null`. Применение аннотации ограничения к типу обобщения
возможно благодаря значению `ElementType.TYPE_USE` в `@Target`. Ограничения элементов контейнеров могут быть установлены в
XML при помощи `<container-element-type>` следующим образом:

```java
<?xml version="1.0" encoding="UTF-8" ?>
<constraint-mappings
        xmlns="https://jakarta.ee/xml/ns/validation/mapping"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/validation/mapping
            https://jakarta.ee/xml/ns/validation/validation-mapping-3.0.xsd"
        version="3.0">

  <bean class="CandidatesGroup">
      <field name="candidates">
          <container-element-type type-argument-index="0">
              <constraint annotation="NotNull"/>
          </container-element-type>
      </field>
  </bean>
</constraint-mappings>
```

Валидируемый контейнер может быть любым элементом, который можно провалидировать: свойством класса, аргументом метода
или конструктора или возвращаемым значением метода.

---
### Наследование ограничений

Применяемые к элементам классов и интерфейсов ограничения сохраняются при использовании наследования и распространяются
на дочерние типы. Наследники в свою очередь могут добавлять собственные ограничения.

Допустим, в проекте есть интерфейс `OrderDomainEvent`:

```java
interface OrderDomainEvent {
    @NonNull
    OrderId getOrderId();
}
```

а так же класс `AbstractOrderDomainEvent`:

```java
abstract class AbstractOrderDomainEvent implements OrderDomainEvent {

    OrderId orderId;

    Instant createdAt;

    @Override
    public OrderId getOrderId() {
        return this.orderId;
    }

    @NotNull
    public Instant getCreatedAt() {
        return this.createdAt;
    }
}
```

и класс `OrderCreated`:

```java
class OrderCreated extends AbstractOrderDomainEvent {

    @NotNull
    @Future
    Instant reservedUntil;
}
```

Класс `OrderCreated` в дополнение к собственным ограничениям унаследует ограничения, объявленные в `OrderDomainEvent` и `AbstractOrderDomainEvent`.

---
### Особенности записей (record)

В JDK 16 появился новый тип классов - запись (record), который может применяться для описания объектов передачи данных
(Data transfer object; DTO). Ограничения можно применять к элементам записей, ровно, как и валидировать их. Пример
применения ограничений к записи:

```java
@ValidCandidate
record Candidate(@Min(18) int age, @NotNull Gender gender) {
}
```

Особенности применения ограничений к элементам записи при помощи аннотаций:
- Ограничение, указанное для класса `@ValidCandidate`, применяется не только к классу, но и к конструктору.
- Ограничения свойств применяются так же к аргументам главного конструктора и методам, возвращающим значения свойств.
- В XML можно применять ограничения индивидуально к элементам записей.

Идентичный набор ограничений для обычного класса выглядел бы следующим образом:

---
```java
@ValidCandidate
class Candidate {

    @Min(18)
    int age;

    @NotNull
    Gender gender;

    @ValidCandidate
    Candidate(@Min(18) int age, @NotNull Gender gender) {
        // прочий код
    }

    @Min(18)
    int age() {
        // прочий код
    }

    @NotNull
    Gender gender() {
        // прочий код
    }
}
```

---
### Валидация

При работе с классами возможна валидация объектов целиком, отдельных их свойств, а также значений на основе правил
валидации свойств классов.

---
#### Валидация объектов

Валидация объектов выполняется при помощи метода `Validator.validate()`, который принимает два аргумента:
- Валидируемый объект;
- Список групп валидации, который может быть пустым (здесь и далее будет применяться [Default](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/validation/groups/default),
  хоть явное указание этой группы и не требуется);

Результатом валидации является множество экземпляров [ConstraintViolation](https://jakarta.ee/specifications/platform/9/apidocs/jakarta/validation/constraintviolation), описывающих ошибки валидации. В случае успеха метод validate должен вернуть пустое множество.

```java
class ValidationTest {

    Validator validator;

    void test() {
        // 1. Создать валидируемый объект
        var candidate = new Candidate();
        candidate.setAge(17);

        // 2. Провалидировать объект
        var violations = this.validator
                .validate(candidate, Default.class);

        // 3. Проверить, что результат содержит
        //    1 ошибку валидации для свойства age
        assertEquals(1, violations.size());
        assertEquals(PathImpl.createPathFromString("age"),
                violations.iterator().next().getPropertyPath());
    }
}
```

---
### Валидация свойств объекта

Jakarta Bean Validation предоставляет возможность валидации конкретных свойств объектов, для этого может быть
использован метод validateProperty, который принимает следующие аргументы:
- Валидируемый объект;
- Название валидируемого свойства;
- Список групп валидации;

Данный метод, так же как и validate, возвращает множество ошибок валидации в случае несоответствия значений правилам валидации.

```java
class ValidationTest {

    Validator validator;

    void test() {
        // 1. Создать валидируемый объект
        var candidate = new Candidate();
        candidate.setAge(17);

        // 2. Провалидировать свойство объекта
        var violations = this.validator
                .validateProperty(candidate, "age", Default.class);

        // 3. Проверить, что результат содержит
        //    1 ошибку валидации для свойства age
        assertEquals(1, violations.size());
        assertEquals(PathImpl.createPathFromString("age"),
                violations.iterator().next().getPropertyPath());
    }
}
```

---
### Валидация значений

Так же есть метод validateValue, позволяющий валидировать значения. При его помощи можно провести валидацию значения до
присвоения свойству, т.е. провести превалидацию. При этом правила валидации, объявленные для указанного свойства
валидируемого класса, будут применены к валидируемому значению. 

Данный метод принимает четыре аргумента:
- Класс валидируемого объекта;
- Название валидируемого свойства;
- Валидируемое значение;
- Список групп валидации;

```java
class ValidationTest {

    Validator validator;

    void test() {
        // 1. Определить валидируемое значение
        var ageToValidate = 17;

        // 2. Провалидировать значение
        var violations = this.validator.validateValue(Candidate.class,
                "age", ageToValidate, Default.class);

        // 3. Проверить, что результат содержит
        //    1 ошибку валидации для свойства age
        assertEquals(1, violations.size());
        assertEquals(PathImpl.createPathFromString("age"),
                violations.iterator().next().getPropertyPath());
    }
}
```

---
### Каскадирование валидации

По умолчанию валидация применяется только к валидируемому объекту, в то время как валидация вложенных объектов не
производится. Если при валидации объекта требуется провалидировать и вложенные объекты, то такие объекты должны быть
отмечены аннотацией `@Valid`:

```java
class CandidatesGroup {

    @NotNull
    List<@Valid Candidate> candidates;
}
```

То же самое может быть достигнуто при помощи `<valid>` в XML:

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<constraint-mappings
        xmlns="https://jakarta.ee/xml/ns/validation/mapping"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="https://jakarta.ee/xml/ns/validation/mapping
            https://jakarta.ee/xml/ns/validation/validation-mapping-3.0.xsd"
        version="3.0">

  <bean class="CandidatesGroup">
      <field name="candidates">
          <container-element-type type-argument-index="0">
              <valid/>
          </container-element-type>
      </field>
  </bean>
</constraint-mappings>
```

При валидации экземпляра `CandidatesGroup` будет проведена проверка, что свойство candidates не является null, а также,
что все элементы этого списка — тоже валидные объекты. Если хотя бы один элемент списка candidates нарушает ограничения,
то валидация завершится ошибкой.

[См. далее →](../JakartaBeanValidation/JakartaBeanValidation_Part.3.md) 
