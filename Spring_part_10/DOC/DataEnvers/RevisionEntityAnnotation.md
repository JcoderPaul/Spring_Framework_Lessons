### Annotation Type RevisionEntity

Помечает объект, который будет создан при создании новой ревизии. Объект редакций должен иметь уникальное свойство с
целочисленным значением (предпочтительно основной идентификатор ID), помеченное знаком, `@RevisionNumber` и свойство с
длинным значением, помеченное как `@RevisionTimestamp`. Эти DefaultRevisionEntity два поля уже есть, поэтому вы
можете расширить его, но вы также можете написать свою собственную сущность редакции с нуля.

---
- Пакет: [org.hibernate.envers](https://docs.hibernate.org/orm/5.6/javadocs/org/hibernate/envers/package-summary.html)
- Описание: [RevisionEntity](https://docs.hibernate.org/orm/5.6/javadocs/org/hibernate/envers/RevisionEntity.html)

---
```Java
  @Retention(value=RUNTIME)
  @Target(value=TYPE)
  public @interface RevisionEntity
```

---
### Переданный параметр

- Class<? extends RevisionListener>	value - Необязательный слушатель, который будет использоваться для заполнения
                                            пользовательской сущности ревизии. Также может быть указано с помощью
                                            org.hibernate.envers.revision_listener свойства конфигурации.
  
---
**См. док. (ENG):**
- [RevisionEntity](https://docs.jboss.org/hibernate/orm/5.4/javadocs/org/hibernate/envers/RevisionEntity.html)
- [Hibernate Doc ch.15](https://docs.jboss.org/hibernate/core/4.1/devguide/en-US/html/ch15.html)
- [Spring док. (ENG)](https://docs.spring.io/spring-data/envers/docs/current/reference/html/)
