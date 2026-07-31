### Annotation Type Audited

При применении данной аннотации к классу указывает, что все его свойства должны подвергаться аудиту. При применении к
полю указывает, что это поле следует проверять.

---
**Пакет:** [org.hibernate.envers](https://docs.hibernate.org/orm/5.1/javadocs/org/hibernate/envers/package-summary.html)

---
```Java
  @Retention(value=RUNTIME)
  @Target(value={TYPE,METHOD,FIELD})
  public @interface Audited
```

---
### Дополнительные параметры аннотации

- **java.lang.String modifiedColumnName** - Имя столбца измененного поля. Аналогично атрибуту имени аннотации @Column. Игнорируется, если withModifiedFlag имеет значение false.

- **RelationTargetAuditMode targetAuditMode** - Указывает, следует ли проверять сущность, являющуюся целевой для связи, или нет. Если нет, то при чтении исторической версии объекта аудита
отношение всегда будет указывать на «текущий» объект. Это полезно для сущностей, подобных словарю, которые не изменяются и не требуют аудита.

- **boolean withModifiedFlag** - Должен ли храниться флаг модификации для каждого свойства в аннотированном классе или для аннотированного свойства. Флаг хранит информацию, если свойство было изменено в
данной версии. Это можно использовать, например, в запросах.

- **ModificationStore modStore** - Устарело. Начиная с версии 5.2, будет удален в версии 6.0 без замены.
- **java.lang.Class[] auditParents** - Устарело. Используйте @AuditOverride(forClass=SomeEntity.class) вместо этого.

---
См. оф. док. (ENG): [Audited.html](https://docs.hibernate.org/orm/5.2/javadocs/org/hibernate/envers/Audited.html)
