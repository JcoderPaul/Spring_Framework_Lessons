- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html)

---
### Class - WebSecurity

**Пакет:**[org.springframework.security.config.annotation.web.builders](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/package-summary.html)

```
java.lang.Object
    org.springframework.security.config.annotation.AbstractSecurityBuilder<O>
        org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder<jakarta.servlet.Filter, WebSecurity>
            org.springframework.security.config.annotation.web.builders.WebSecurity
```

**Все реализованные интерфейсы:** 
- [Aware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/Aware.html),
- [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html),
- [SecurityBuilder<jakarta.servlet.Filter>](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/SecurityBuilder.html),
- [ServletContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ServletContextAware.html)

```java
public final class WebSecurity extends AbstractConfiguredSecurityBuilder<jakarta.servlet.Filter, WebSecurity>
                    implements SecurityBuilder<jakarta.servlet.Filter>, 
                    org.springframework.context.ApplicationContextAware, 
                    org.springframework.web.context.ServletContextAware
```

[WebSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html) создается [WebSecurityConfiguration](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.html) для создания [FilterChainProxy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/FilterChainProxy.html), известного, как цепочка фильтров безопасности Spring (springSecurityFilterChain). SpringSecurityFilterChain — это фильтр,
которому делегирует DelegatingFilterProxy.

Настроить [WebSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html) можно путем создания [WebSecurityConfigurer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/WebSecurityConfigurer.html) или предоставления bean-компонента [WebSecurityCustomizer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityCustomizer.html)

---
#### Вложенные классы

- `class WebSecurity.IgnoredRequestConfigurer` - Позволяет регистрировать RequestMatcher экземпляры, которые Spring Security должен игнорировать.

---
#### Конструктор

- `WebSecurity(ObjectPostProcessor<Object> objectPostProcessor)` - Создает новый экземпляр.

Где, параметр: 
- objectPostProcessor - используемый [ObjectPostProcessor](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/ObjectPostProcessor.html).

**Смотреть также:** [WebSecurityConfiguration](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.html)

---
#### Методы

- `WebSecurity addSecurityFilterChainBuilder(SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder)` - Добавляет сборщиков для создания [SecurityFilterChain](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/SecurityFilterChain.html) экземпляров.
- `WebSecurity debug(boolean debugEnabled)` - Управляет поддержкой отладки Spring Security.
- `WebSecurity expressionHandler(SecurityExpressionHandler<FilterInvocation> expressionHandler)` - Установите параметр, [SecurityExpressionHandler](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/expression/SecurityExpressionHandler.html) который будет использоваться.
- `SecurityExpressionHandler<FilterInvocation> getExpressionHandler()` - Получает объект [SecurityExpressionHandler](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/expression/SecurityExpressionHandler.html) для использования.
- `WebInvocationPrivilegeEvaluator getPrivilegeEvaluator()` - Получает объект [WebInvocationPrivilegeEvaluator](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/access/WebInvocationPrivilegeEvaluator.html) для использования.
- `WebSecurity httpFirewall(HttpFirewall httpFirewall)` - Позволяет настраивать [HttpFirewall](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/firewall/HttpFirewall.html).
- `WebSecurity.IgnoredRequestConfigurer ignoring()` - Позволяет добавлять [RequestMatcher](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/util/matcher/RequestMatcher.html) экземпляры, которые Spring Security должен игнорировать.
- `protected jakarta.servlet.Filter performBuild()` - Подклассы должны реализовать этот метод для построения возвращаемого объекта.
- `WebSecurity postBuildAction(Runnable postBuildAction)` - Выполняет Runnable сразу после завершения сборки.
- `WebSecurity privilegeEvaluator(WebInvocationPrivilegeEvaluator privilegeEvaluator)` - Установите параметр, [WebInvocationPrivilegeEvaluator](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/access/WebInvocationPrivilegeEvaluator.html) который будет использоваться.
- `WebSecurity requestRejectedHandler(RequestRejectedHandler requestRejectedHandler)` - Устанавливает обработчик для обработки [RequestRejectedException](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/firewall/RequestRejectedException.html)
- `void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)`
- `void setServletContext(jakarta.servlet.ServletContext servletContext)`

---
- **Методы, унаследованные от класса [org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/AbstractConfiguredSecurityBuilder.html):** apply, beforeConfigure, beforeInit, doBuild, getConfigurer, getConfigurers, getOrBuild, getSharedObject, getSharedObjects, objectPostProcessor, postProcess, removeConfigurer, removeConfigurers, setSharedObject, with
- **Методы, унаследованные от класса [org.springframework.security.config.annotation.AbstractSecurityBuilder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/AbstractSecurityBuilder.html):** build, getObject
- **Методы, унаследованные от класса [java.lang.Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html):** clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait
- **Методы, унаследованные от интерфейса [org.springframework.security.config.annotation.SecurityBuilder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/SecurityBuilder.html):** build

---
**Доп. материал:**
- [GitHub WebSecurity](https://github.com/spring-projects/spring-security/blob/main/config/src/main/java/org/springframework/security/config/annotation/web/builders/WebSecurity.java)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web)
- [Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
- [HttpSecurity vs. WebSecurity in Spring Security](https://www.baeldung.com/spring-security-httpsecurity-vs-websecurity)
- [Spring Security with WebSecurityConfig – Authentication and Authorization with Roles](https://josealopez.dev/en/blog/spring-security-authentication-and-authorization-guide)
- [Spring Boot Security, Step By Step — Part 1: Authentication](https://medium.com/@ansgar.nell/spring-boot-security-step-by-step-21ea836499f8)
- [Spring Security Project Example using Java Configuration](https://www.geeksforgeeks.org/springboot/spring-security-project-example-using-java-configuration/)
- [Spring Security for Beginners — The Easiest Guide You’ll Ever Read](https://dev.to/jps27cse/spring-security-for-beginners-the-easiest-guide-youll-ever-read-2cib)
