### Entity

Классический пример: [User.java](../src/main/java/spring/oldboy/database/entity/User.java)

**Entity** - это легковесный хранимый объект бизнес логики (persistent domain object).
**Entity класс** - это основная программная сущность, которая так же может использовать дополнительные классы,
               которые, в свою очередь, могут использоваться как вспомогательные классы, так и для сохранения
               состояния Entity.

---
Entity класс МОЖЕТ:

- наследоваться от не Entity классов (non-entity classes);
- наследоваться от других Entity классов;
- быть абстрактным классом, при этом он сохраняет все свойства Entity, за исключением того что его нельзя
  непосредственно инициализировать;
- допустимо чтобы НЕ Entity класс наследовался от Entity класса;

---
У JPA спецификации к Entity классам есть ТРЕБОВАНИЯ:

1) Entity класс должен быть отмечен аннотацией Entity или описан в XML файле конфигурации JPA;
2) Entity класс должен содержать public или protected конструктор без аргументов (он также может иметь
   конструкторы с аргументами);
3) Entity класс должен быть классом верхнего уровня (top-level class);
4) Entity класс не может быть enum или интерфейсом;
5) Entity класс не может быть финальным классом (final class);
6) Entity класс не может содержать финальные поля или методы, если они участвуют в маппинге
   (persistent final methods or persistent final instance variables);
7) Если объект Entity класса будет передаваться по значению как отдельный объект (detached object), например,
   через удаленный интерфейс (through a remote interface), он так же должен реализовывать Serializable интерфейс;
8) Поля Entity класс должны быть напрямую доступны только методам самого Entity класса и не должны быть напрямую
   доступны другим классам, использующим этот Entity. Такие классы должны обращаться только к методам
   (getter/setter методам или другим методам бизнес-логики в Entity классе);
9) Entity класс должен содержать первичный ключ, то есть атрибут или группу атрибутов которые уникально определяют
   запись этого Entity класса в базе данных;

---
У Entity классов существует два типа доступа (access) к элементам самих же Entity классов - JPA указывает что она
может работать как со свойствами классов (property), оформленные в стиле JavaBeans, так и с полями (field), то есть
переменными класса (instance variables). Соответственно, при этом тип доступа будет либо property access, либо
field access. Оба типа элементов (типы доступа) Entity класса называются атрибутами Entity класса. Постоянные поля
и свойства класса сущностей, как правило, называются «атрибутами» класса.

В атрибутах (полях или свойствах) Entity класса ДОПУСТИМЫ:

1. примитивные типы и их обертки Java;
2. строки;
3. любые сериализуемые типы Java (реализующие Serializable интерфейс);
4. enums;
5. entity types;
6. embeddable классы;
7. коллекции типов 1-6;

В атрибутах, входящих в первичный ключ Entity класса (составной или простой) можно использовать следующие типы
данных, при условии, что полученный первичный ключ можно использоваться для любой базы данных:

1. примитивные типы и их обертки Java;
2. строки;
3. BigDecimal и BigInteger;
4. java.util.Date и java.sql.Date;

В случае авто-генерируемого первичного ключа (generated primary keys) допустимы только числовые типы.

В случае использования других типов данных в первичном ключе, он может работать только для некоторых
баз данных, т.е. становится не переносимым (not portable) на другие более специфические БД.

---
**Типы связей (relationship) между Entity классами:**

1. **OneToOne** (связь один к одному, то есть один объект Entity может связан не больше чем с одним объектом
   другого Entity);
2. **OneToMany** (связь один ко многим, один объект Entity может быть связан с целой коллекцией других Entity);
3. **ManyToOne** (связь многие к одному, обратная связь для OneToMany);
4. **ManyToMany** (связь многие ко многим);

Каждую из вышеописанных связей можно разделить ещё на два вида:

1. **Bidirectional (двунаправленная)** — когда ссылка на связь устанавливается у всех связных Entity.
   Например, в случае связи OneToOne A-B, в Entity 'A' есть ссылка на Entity 'B', в Entity 'B' есть ссылка на
   Entity 'A'. Entity 'A' считается владельцем этой связи (это важно для случаев каскадного удаления данных,
   тогда при удалении 'A' также будет удалено 'B', но не наоборот);
2. **Unidirectional (однонаправленная)** - когда ссылка на связь устанавливается только с одной стороны, то есть
   в случае OneToOne A-B только у Entity 'A' будет ссылка на Entity 'B', у Entity 'B' ссылки на 'A' не будет;

См. пример: [Hibernate_part_2](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_2)

---
#### Основные операции с Entity

Основные операции над Entity сущностями:

- persist (добавление Entity под управление JPA);
- merge (обновление);
- remove (удаления);
- refresh (обновление данных);
- detach (удаление из управление JPA);
- lock (блокирование Enity от изменений в других thread);
- find (поиск и получение Entity);
- contains;

Основные операции с запросами:

- createQuery;
- createNamedQuery;
- createNativeQuery;
- createNamedStoredProcedureQuery;
- createStoredProcedureQuery;

Основные операции с EntityGraph:

- createEntityGraph;
- getEntityGraph;

Общие операции над всеми Entities:

- close;
- isOpen;
- getProperties;
- setProperty;
- clear;

---
У Entity объекта существует четыре статуса жизненного цикла (Entity Instance’s Life Cycle):
1) **new** — объект создан, но при этом ещё не имеет сгенерированных первичных ключей и пока ещё не сохранен в
   базе данных;
2) **managed** — объект создан, управляется JPA, имеет сгенерированные первичные ключи;
3) **detached** — объект был создан, но не управляется (или больше не управляется) JPA;
4) **removed** — объект создан, управляется JPA, но будет удален после commit-a транзакции;

---
См. рис.

![LifeCycleOfEntity.jpg](https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_1/DOC/LifeCycleOfEntity.jpg)

См. код: [Hibernate_part_1](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_1/src/main/java/oldboy/lesson_6)

---
Операция persist на Entity объекты каждого из четырех статусов влияет следующим образом:

1) Если статус Entity new, то он меняется на managed и объект будет сохранен в базу при commit-е транзакции
   или в результате flush операций;
2) Если статус уже managed, операция игнорируется, однако зависимые Entity могут поменять статус на managed,
   если у них есть аннотации каскадных изменений;
3) Если статус removed, то он меняется на managed;
4) Если статус detached, будет выкинут exception сразу или на этапе commit-а транзакции;

Операция remove на Entity объекты каждого из четырех статусов влияет следующим образом:

1) Если статус Entity new, операция игнорируется, однако зависимые Entity могут поменять статус на removed,
   если у них есть аннотации каскадных изменений и они имели статус managed;
2) Если статус managed, то статус меняется на removed и запись объекта в базе данных будет удалена при commit-е
   транзакции (так же произойдут операции remove для всех каскадно зависимых объектов);
3) Если статус removed, то операция игнорируется;
4) Если статус detached, будет выкинут exception сразу или на этапе commit-а транзакции;

Операция merge на Entity объекты каждого из четырех статусов влияет следующим образом:

1) Если статус detached, то либо данные будет скопированы в существующей managed entity с тем же первичным
   ключом, либо создан новый managed в который скопируются данные;
1) Если статус Entity new, то будет создана новый managed entity, в который будут скопированы данные прошлого
   объекта;
2) Если статус managed, операция игнорируется, однако операция merge сработает на каскадно зависимые Entity,
   если их статус не managed;
3) Если статус removed, будет выкинут exception сразу или на этапе commit-а транзакции;

Операция refresh на Entity объекты каждого из четырех статусов влияет следующим образом:

1) Если статус Entity managed, то в результате операции будут восстановлены все изменения из базы данных
   данного Entity, так же произойдет refresh всех каскадно зависимых объектов;
2) Если статус new, removed или detached, будет выкинут exception;

Операция detach на Entity объекты каждого из четырех статусов влияет следующим образом:

1) Если статус Entity managed или removed, то в результате операции статус Entity (и всех каскадно-зависимых
   объектов) станет detached;
2) Если статус new или detached, то операция игнорируется;

---
#### Настройки fetch стратегий для атрибутов Entity

Если возникла необходимость изменить настройки fetch стратегии любых атрибутов Entity для отдельных запросов
(query) или методов поиска (find), но при этом у Entity класса есть атрибут с fetchType = LAZY, а нам для
конкретного запроса его требуется сделать EAGER, то можно воспользоваться возможностями EntityGraph API.

C помощью аннотации @NamedEntityGraph для Entity, создаются именованные @EntityGraph объекты, которые содержат
список атрибутов у которых нужно поменять fetchType на EAGER, а потом данное имя указывается в hits запросов или
метода find. В результате fetchType атрибутов Entity меняется, но только для этого запроса.

Существует две стандартных property для указания EntityGraph в hit:

1) **javax.persistence.fetchgraph** — все атрибуты перечисленные в EntityGraph меняют fetchType на EAGER, все остальные на LAZY;
2) **javax.persistence.loadgraph** — все атрибуты перечисленные в EntityGraph меняют fetchType на EAGER, все остальные сохраняют свой fetchType (то есть если у атрибута, не указанного в EntityGraph, fetchType был EAGER, то он и останется EAGER);

С помощью @NamedSubgraph можно также изменить fetchType вложенных объектов Entity.

Пример:
```Java
  // Определяем Entity и EntityGraph
  @Entity
  @Table(name = "order")
  @Named(name = "graphOrderItems",
                 attributeNodes = @NamedAttributeNode(attributeNodes = "items")
  )
  public class Order implements Serializable {
  
     ...
  
     @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
     private Set<Item> items = new HashSet<Item>();
  
     @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
     private Set<Features> features = new HashSet<Features>();
  
     @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
     private Set<Comment> comments = new HashSet<Comment>();
     ...
  
  // Вызываем метод поиска c javax.persistence.fetchgraph
  ..
  EntityGraph graph = this.em.getEntityGraph("graphOrderItems");
  
  Map hints = new HashMap();
  hints.put("javax.persistence.fetchgraph", graph);
  
  // items во время запроса будет иметь fetchType = EAGER, а features и comments имеют fetchType = LAZY
  return this.em.find(Order.class, orderId, hints);
  
  // Вызываем метод поиска c javax.persistence.loadgraph
  ..
  EntityGraph graph = this.em.getEntityGraph("graphOrderItems");
  
  Map hints = new HashMap();
  hints.put("javax.persistence.loadgraph", graph);
  
  // items и features во время запроса будет иметь fetchType = EAGER, а  comments все также имеет fetchType = LAZY
  return this.em.find(Order.class, orderId, hints);
```

---
См. пример (RUS): [Hibernate_part_7](https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_7)
