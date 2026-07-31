**Исходные статьи (оф. док. ENG):**

- [Chapter 15. Envers](https://docs.jboss.org/hibernate/core/4.3/devguide/en-US/html/ch15.html)
- [Chapter 4. Logging data for revisions](https://docs.jboss.org/hibernate/envers/3.6/reference/en-US/html/revisionlog.html)

---
### Регистрация данных ревизий

Envers предоставляет **простой способ регистрации дополнительных данных для каждой ревизии**. Вам просто - **нужно аннотировать
один объект с помощью @RevisionEntity**, и новый экземпляр этого объекта будет сохраняться при создании новой ревизии (то
есть при каждом изменении проверяемого объекта). Поскольку ревизии являются глобальными, вы можете иметь не более одной
сущности редакций.

---
**!!! Обратите внимание, что объект редакции должен быть сопоставленным объектом Hibernate !!!**

--
Эта сущность должна иметь как минимум два свойства:

- *revision number* - свойство с целочисленным (Integer) или 'длинным' значением (Long), помеченное знаком @RevisionNumber. Чаще всего это будет автоматически сгенерированный первичный ключ - ID;
- *revision timestamp* - свойство со значением `long/Long` или `java.util.Date`, помеченное @RevisionTimestamp. Значение этого свойства будет автоматически установлено Envers;

Вы можете либо добавить эти свойства в свою сущность, либо расширить `org.hibernate.envers.DefaultRevisionEntity`, у которого уже есть эти два свойства.

При использовании Date вместо long/Long метки времени редакции старайтесь не использовать сопоставление свойства,
которое приведет к потере точности (например, использование @Temporal(DATE) неверно, поскольку оно не хранит информацию
о времени, поэтому многие из ваших ревизий будут потеряны, т.к. будет казаться, что это произошло в одно и то же время).

Хороший выбор — это @Temporal(TIMESTAMP).

Чтобы наполнить сущность дополнительными данными, вам потребуется реализовать интерфейс [org.jboss.envers.RevisionListener/org.hibernate.envers](https://docs.hibernate.org/orm/5.1/javadocs/org/hibernate/envers/RevisionListener.html).
Его метод newRevision будет вызываться при создании новой ревизии перед сохранением сущности ревизии. Реализация должна
быть без сохранения состояния и потокобезопасной. Затем прослушиватель необходимо присоединить к объекту редакций, указав
его в качестве параметра аннотации @RevisionEntity.

Альтернативно вы можете использовать getCurrentRevision метод интерфейса AuditReader для получения текущей версии и
заполнения ее нужной информацией. У метода есть persist параметр, определяющий, следует ли сохранить объект ревизии перед
возвратом. Если установлено значение true, номер редакции будет доступен в возвращаемом объекте ревизии (поскольку он
обычно генерируется базой данных), но объект ревизии будет сохраняться независимо от того, были ли изменены какие-либо
проверяемые объекты. Если установлено значение false, номер версии будет null, но объект версии будет сохранен только в
том случае, если некоторые проверяемые объекты изменились.

Простейший пример сущности редакций, которая с каждой редакцией связывает имя пользователя, внесшего изменение:

```Java
	package org.jboss.envers.example;
	
	import org.hibernate.envers.RevisionEntity;
	import org.hibernate.envers.DefaultRevisionEntity;
	
	import javax.persistence.Entity;
	
	@Entity
	@RevisionEntity(ExampleListener.class)
	public class ExampleRevEntity extends DefaultRevisionEntity {
		private String username;
	
		public String getUsername() { return username; }
		public void setUsername(String username) { this.username = username; }
	}
```

Или, если вы не хотите расширять какой-либо класс:

```Java
	package org.hibernate.envers.example;
	
	import org.hibernate.envers.RevisionNumber;
	import org.hibernate.envers.RevisionTimestamp;
	import org.hibernate.envers.RevisionEntity;
	
	import javax.persistence.Id;
	import javax.persistence.GeneratedValue;
	import javax.persistence.Entity;
	
	@Entity
	@RevisionEntity(ExampleListener.class)
	public class ExampleRevEntity {
	
	    @Id
	    @GeneratedValue
	    @RevisionNumber
	    private int id;
	
	    @RevisionTimestamp
	    private long timestamp;
	
	    private String username;
	
	    /* Getters, setters, equals, hashCode ... */
	}
```

Пример прослушивателя (слушателя), который, если он используется в приложении JBoss Seam, сохраняет имя пользователя, вошедшего в
систему:

```Java
	package org.hibernate.envers.example;
	
	import org.hibernate.envers.RevisionListener;
	import org.jboss.seam.security.Identity;
	import org.jboss.seam.Component;
	
	public class ExampleListener implements RevisionListener {
	    public void newRevision(Object revisionEntity) {
	        ExampleRevEntity exampleRevEntity = (ExampleRevEntity) revisionEntity;
	        Identity identity = (Identity) Component.getInstance("org.jboss.seam.security.identity");
	
	        exampleRevEntity.setUsername(identity.getUsername());
	    }
	}
```

Наличие «пустой» сущности ревизии, то есть без каких-либо дополнительных свойств, кроме двух обязательных, также
является простым способом изменить имена таблицы и свойств в таблице ревизий, автоматически созданной Envers.

Если объект, помеченный значком , отсутствует @RevisionEntity, будет создана таблица по умолчанию с именем REVINFO.

---
### Имена объектов отслеживания, измененные во время изменений

По умолчанию типы объектов, измененные в каждой версии, не отслеживаются. Это подразумевает необходимость запроса всех
таблиц, хранящих проверенные данные, для получения изменений, внесенных во время указанной ревизии. Пользователям
разрешено реализовать собственный механизм отслеживания измененных имен объектов. В этом случае они должны передать свою
собственную реализацию `org.hibernate.envers.EntityTrackingRevisionListener` интерфейса `EntityTrackingRevisionListener` в
качестве значения `@org.hibernate.envers.RevisionEntity` аннотации. Интерфейс предоставляет один метод, который уведомляет
всякий раз, когда экземпляр проверяемого объекта был добавлен, изменен или удален в пределах границ текущей версии.

Пользовательская реализация классов сущностей отслеживания, измененных во время изменений:

- CustomEntityTrackingRevisionListener.java:

```Java
	public class CustomEntityTrackingRevisionListener implements EntityTrackingRevisionListener {
	
	    @Override
	    public void entityChanged(Class entityClass,
	                              String entityName,
	                              Serializable entityId,
	                              RevisionType revisionType,
	                              Object revisionEntity) {
	        String type = entityClass.getName();
	        ((CustomTrackingRevisionEntity)revisionEntity).addModifiedEntityType(type);
	    }
	
	    @Override
	    public void newRevision(Object revisionEntity) {
	    }
```

- CustomTrackingRevisionEntity.java:
  
```Java
	@Entity
	@RevisionEntity(CustomEntityTrackingRevisionListener.class)
	public class CustomTrackingRevisionEntity {
	
	    @Id
	    @GeneratedValue
	    @RevisionNumber
	    private int customId;
	
	    @RevisionTimestamp
	    private long customTimestamp;
	
	    @OneToMany(mappedBy="revision",
	               cascade={CascadeType.PERSIST,
	                        CascadeType.REMOVE})
	    private Set<ModifiedEntityTypeEntity> modifiedEntityTypes =
	                                              new HashSet<ModifiedEntityTypeEntity>();
	
	    public void addModifiedEntityType(String entityClassName) {
	        modifiedEntityTypes.add(new ModifiedEntityTypeEntity(this, entityClassName));
	    }
	    ...
	}
```

- ModifiedEntityTypeEntity.java:

```Java
	@Entity
	public class ModifiedEntityTypeEntity {
	
	    @Id
	    @GeneratedValue
	    private Integer id;
	
	    @ManyToOne
	    private CustomTrackingRevisionEntity revision;
	
	    private String entityClassName;
	
	    ...
	}
```

```Java
	CustomTrackingRevisionEntity revEntity =
	    getAuditReader().findRevision(CustomTrackingRevisionEntity.class, revisionNumber);
	Set<ModifiedEntityTypeEntity> modifiedEntityTypes = revEntity.getModifiedEntityTypes()
```
