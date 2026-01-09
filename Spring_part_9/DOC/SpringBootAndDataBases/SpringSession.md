### Spring Session

Spring Boot предусматривает авто-конфигурацию [см. Spring Session](https://spring.io/projects/spring-session) для
широкого спектра хранилищ данных. При создании сервлетного веб-приложения следующие хранилища могут быть
автоматически сконфигурированы:

- **JDBC**;
- **Redis**;
- **Hazelcast**;
- **MongoDB**;

Кроме того, [см. Spring Boot для Apache Geode](https://github.com/spring-projects/spring-boot-data-geode)
предусматривает автоконфигурацию для использования Apache Geode в качестве хранилища сессий.

Авто-конфигурация сервлета заменяет необходимость использования аннотации [@Enable*HttpSession](https://docs.spring.io/spring-session/reference/http-session.html).

При создании реактивного веб-приложения следующие хранилища могут быть автоматически сконфигурированы:

- Redis;
- MongoDB;

Реактивная автоконфигурация заменяет необходимость использования аннотации @Enable*WebSession.

Если в classpath присутствует один модуль Spring Session, Spring Boot автоматически использует эту реализацию
хранилища. Если имеется более одной реализации, [необходимо выбрать StoreType](https://github.com/spring-projects/spring-boot/blob/v2.7.5/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/session/StoreType.java),
который вы хотите использовать для хранения сессий.

Например, чтобы использовать JDBC в качестве бэкенд-хранилища, можно сконфигурировать приложение следующим
образом:

in Properties:

```
  spring.session.store-type=jdbc
```

in Yaml:

```YAML
spring:
  session:
    store-type: "jdbc"
```

---
**!!! Внимание !!!** 

Можно отключить Spring Session, установив для store-type значение none.

---
Каждое хранилище имеет определенные дополнительные параметры. Например, можно настроить имя таблицы для хранилища
JDBC, как показано в следующем примере:

in Properties:

```
  spring.session.jdbc.table-name=SESSIONS
```

in Yaml:

```YAML
spring:
  session:
    jdbc:
      table-name: "SESSIONS"
```

Для установки времени ожидания сессии можно использовать свойство `spring.session.timeout`. Если это свойство не
установить в сервлетном веб-приложении, автоконфигурация вернется к значению `server.servlet.session.timeout`.

Можно управлять конфигурацией Spring Session, используя аннотацию @Enable*HttpSession (сервлетное приложение)
или аннотацию @Enable*WebSession (реактивное приложение). Это приведет к откату автоконфигурации. Затем Spring
Session можно будет сконфигурировать, используя атрибуты аннотации, а не описанные ранее конфигурационные
свойства.
