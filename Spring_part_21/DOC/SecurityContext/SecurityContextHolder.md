- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html)

---
### Class SecurityContextHolder

**Пакет:** [org.springframework.security.core.context](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/package-summary.html)

```
    java.lang.Object
        org.springframework.security.core.context.SecurityContextHolder
```

```java
    public class SecurityContextHolder 
                        extends Object
```

Связывает заданный [SecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html) 
с текущим потоком выполнения. Этот класс предоставляет ряд статических методов, которые делегируют экземпляр [SecurityContextHolderStrategy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolderStrategy.html).

Цель класса — предоставить удобный способ указать стратегию, которую следует использовать для данной JVM. Это настройка
всей JVM, поскольку все в этом классе статично, что упрощает использование кода при вызове.

Чтобы указать, какую стратегию следует использовать, необходимо указать настройку режима. Параметр режима — это один из
трех допустимых параметров MODE_, определенных как статические конечные поля, или полное имя класса для конкретной
реализации [SecurityContextHolderStrategy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolderStrategy.html), которая предоставляет общедоступный конструктор без аргументов.

Есть два способа указать желаемую строку режима стратегии:
- Первый — указать его через системное свойство, указанное в ключе [SYSTEM_PROPERTY](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html#SYSTEM_PROPERTY).
- Второй — вызвать [setStrategyName(String)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html#setStrategyName(java.lang.String)) перед использованием класса.

Если ни один из подходов не используется, класс по умолчанию будет использовать [MODE_THREADLOCAL](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html#MODE_THREADLOCAL), который обратно
совместим, имеет меньше несовместимостей с JVM и подходит для серверов (тогда как [MODE_GLOBAL](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html#MODE_GLOBAL) определенно не подходит
для использования на сервере).

---
#### Поля

- `static final String MODE_GLOBAL`
- `static final String MODE_INHERITABLETHREADLOCAL`
- `static final String MODE_THREADLOCAL`
- `static final String SYSTEM_PROPERTY`

**См. [Constant Field Values](https://docs.spring.io/spring-security/site/docs/current/api/constant-values.html)

---
#### Конструктор

[SecurityContextHolder()](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html#%3Cinit%3E())

---
#### Методы

- `static void clearContext()` - Явно очищает значение контекста из текущего потока.
- `static SecurityContext createEmptyContext()` - Делегирует создание нового пустого контекста настроенной стратегии.
- `static SecurityContext getContext()` - Получите ток SecurityContext.
- `static SecurityContextHolderStrategy getContextHolderStrategy()` - Позволяет получить контекстную стратегию.
- `static Supplier<SecurityContext> getDeferredContext()` - Получает объект [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html), который возвращает текущий контекст.
- `static int getInitializeCount()` - В первую очередь для целей устранения неполадок этот метод показывает, сколько раз класс повторно инициализировал свой файл SecurityContextHolderStrategy.
- `static void setContext(SecurityContext context)` - Связывает новый поток SecurityContext с текущим потоком выполнения.

---
- `static void setContextHolderStrategy(SecurityContextHolderStrategy strategy)` - Используйте эту [SecurityContextHolderStrategy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolderStrategy.html). Вызовите либо [setStrategyName(String)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html#setStrategyName(java.lang.String)), либо этот метод, но не оба одновременно.
Этот метод не является потокобезопасным. Изменение стратегии во время выполнения запросов может привести к возникновению условий гонки.

[SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html) поддерживает статическую ссылку на предоставленный [SecurityContextHolderStrategy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolderStrategy.html). Это означает, что стратегия и ее элементы не будут подлежать сборке мусора, пока вы не удалите свою стратегию. 

Чтобы обеспечить сбор мусора, запомните исходную стратегию следующим образом:

```java
     SecurityContextHolderStrategy original = SecurityContextHolder.getContextHolderStrategy();
     SecurityContextHolder.setContextHolderStrategy(myStrategy);
```

И затем, когда вы будете готовы к сбору мусора в myStrategy, вы можете сделать:

```java
    SecurityContextHolder.setContextHolderStrategy(original);
```

**Параметр:** `strategy` - используемый [SecurityContextHolderStrategy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolderStrategy.html);

---
- `static void setDeferredContext(Supplier<SecurityContext> deferredContext)` - Устанавливает объект [Supplier (поставщика)](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html), который будет возвращать текущий контекст.
Реализации могут переопределить значение по умолчанию, чтобы избежать вызова [Supplier.get()](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html#get()).

**Параметры:** `deferredContext` — [поставщик](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html), который возвращает [SecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html).

---
- `static void setStrategyName(String strategyName)` - Изменяет предпочтительную стратегию. НЕ вызывайте этот метод более одного раза для данной JVM, так как это приведет к повторной инициализации стратегии и отрицательно повлияет на любые существующие потоки, использующие старую стратегию.

**Параметры:** `StrategyName` — полное имя класса стратегии, которую следует использовать.

---

- `String toString()`

---
- Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html): clone, equals, finalize, getClass, hashCode, notify, notifyAll, wait

---
**Доп. материалы:**
- [GitHub SecurityContextHolder.java](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/core/context/SecurityContextHolder.java)
- [Servlet Authentication Architecture (from spring.io)](https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html)
- [Spring Security Architecture and Implementation (from spring.io)](https://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/html/technical-overview.html)
- [Persisting Authentication (from spring.io)](https://docs.spring.io/spring-security/reference/servlet/authentication/persistence.html)
- [How to Manually Authenticate User with Spring Security](https://www.baeldung.com/manually-set-user-authentication-spring-security)
- [How to manually set an authenticated user in Spring Security / SpringMVC](https://stackoverflow.com/questions/4664893/how-to-manually-set-an-authenticated-user-in-spring-security-springmvc)
- [Java Examples for org.springframework.security.context.SecurityContextHolder](https://www.javatips.net/api/org.springframework.security.context.securitycontextholder)
- [What is SecurityContext and SecurityContextHolder in Spring Security?](https://www.javacodegeeks.com/2018/02/securitycontext-securitycontextholder-spring-security.html)
- [Spring Security: SecurityContextHolder](https://www.javaguides.net/2024/04/spring-security-securitycontextholder.html)
- [Spring SecurityContext Explained: The Missing Piece Most Developers Don’t Understand](https://medium.com/@gaddamnaveen192/spring-securitycontext-explained-the-missing-piece-most-developers-dont-understand-4e0ca80ab1bb)
- [Understanding SecurityContext and SecurityContextHolder in Spring Security](https://medium.com/@CodeWithTech/understanding-securitycontext-and-securitycontextholder-in-spring-security-e8ec9c030819)
- [Spring Security - Understanding Security Context](https://xinghua24.github.io/SpringSecurity/Spring-Security-Understanding-Security-Context/)
