- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html)

---
### Class WebSecurity

**Пакет:** [org.springframework.security.config.annotation.web.builders](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/package-summary.html)

```
java.lang.Object
    org.springframework.security.config.annotation.AbstractSecurityBuilder<O>
        org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder<jakarta.servlet.Filter,WebSecurity>
            org.springframework.security.config.annotation.web.builders.WebSecurity
```

**Все реализованные интерфейсы:** 
- [Aware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/beans/factory/Aware.html),
- [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/ApplicationContextAware.html),
- [SecurityBuilder<jakarta.servlet.Filter>](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/SecurityBuilder.html),
- [ServletContextAware](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/ServletContextAware.html)

```java
public final class WebSecurity
                extends AbstractConfiguredSecurityBuilder<jakarta.servlet.Filter, WebSecurity>
                    implements SecurityBuilder<jakarta.servlet.Filter>,
                               org.springframework.context.ApplicationContextAware,
                               org.springframework.web.context.ServletContextAware
```

[WebSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html) создается [WebSecurityConfiguration](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.html)
для создания [FilterChainProxy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/FilterChainProxy.html), известного, как цепочка фильтров безопасности Spring (springSecurityFilterChain). SpringSecurityFilterChain — это фильтр, которому делегирует DelegatingFilterProxy.

Настроить [WebSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html) можно путем создания [WebSecurityConfigurer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/WebSecurityConfigurer.html) или предоставления bean-компонента [WebSecurityCustomizer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityCustomizer.html)

**См. также:**
- [EnableWebSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/EnableWebSecurity.html)
- [WebSecurityConfiguration](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.html)

---
#### Вложенные классы

- class [WebSecurity.IgnoredRequestConfigurer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.IgnoredRequestConfigurer.html) - Позволяет регистрировать [RequestMatcher](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/util/matcher/RequestMatcher.html) экземпляры, которые Spring Security должен игнорировать.

---
#### Конструктор

- `WebSecurity(ObjectPostProcessor<Object> objectPostProcessor)` - Создает новый экземпляр.

**Параметр:** 
- `objectPostProcessor` - используемый [ObjectPostProcessor](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/ObjectPostProcessor.html).
**Смотреть также:**
- [WebSecurityConfiguration](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.html)

---
#### Методы

- `WebSecurity addSecurityFilterChainBuilder(SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder)` - Добавляет сборщиков для создания [SecurityFilterChain](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/SecurityFilterChain.html) экземпляров.
- `WebSecurity debug(boolean debugEnabled)` - Управляет поддержкой отладки Spring Security.
- `WebSecurity expressionHandler(SecurityExpressionHandler<FilterInvocation> expressionHandler)` - Установите параметр [SecurityExpressionHandler](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/expression/SecurityExpressionHandler.html), который будет использоваться.
- `SecurityExpressionHandler<FilterInvocation> getExpressionHandler()` - Получает объект [SecurityExpressionHandler](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/expression/SecurityExpressionHandler.html) для использования.
- `WebInvocationPrivilegeEvaluator getPrivilegeEvaluator()` - Получает объект [WebInvocationPrivilegeEvaluator](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/access/WebInvocationPrivilegeEvaluator.html) для использования.
- `WebSecurity httpFirewall(HttpFirewall httpFirewall)` - Позволяет настраивать [HttpFirewall](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/firewall/HttpFirewall.html).
- `WebSecurity.IgnoredRequestConfigurer ignoring()` - Позволяет добавлять [RequestMatcher](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/util/matcher/RequestMatcher.html) экземпляры, которые Spring Security должен игнорировать.
- `protected jakarta.servlet.Filter performBuild()` - Подклассы должны реализовать этот метод для построения возвращаемого объекта.
- `WebSecurity postBuildAction(Runnable postBuildAction)` - Выполняет Runnable сразу после завершения сборки.
- `WebSecurity privilegeEvaluator(WebInvocationPrivilegeEvaluator privilegeEvaluator)` - Установите параметр [WebInvocationPrivilegeEvaluator](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/access/WebInvocationPrivilegeEvaluator.html), который будет использоваться.
- `WebSecurity requestRejectedHandler(RequestRejectedHandler requestRejectedHandler)` - Устанавливает обработчик для обработки [RequestRejectedException](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/firewall/RequestRejectedException.html)
- `void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)`
- `void setServletContext(jakarta.servlet.ServletContext servletContext)`

---
- Методы, унаследованные от класса [AbstractConfiguredSecurityBuilder](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/AbstractConfiguredSecurityBuilder.html): apply, beforeConfigure, beforeInit, doBuild, getConfigurer, getConfigurers, getOrBuild, getSharedObject, getSharedObjects, objectPostProcessor, postProcess, removeConfigurer, removeConfigurers, setSharedObject, with
- Методы, унаследованные от класса [AbstractSecurityBuilder](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/AbstractSecurityBuilder.html): build, getObject
- Методы, унаследованные от класса [Object](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait
- Методы, унаследованные от интерфейса [SecurityBuilder](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/SecurityBuilder.html): build

---
**Доп. материаля:**
- [Securing a Web Application (by spring.io)](https://spring.io/guides/gs/securing-web)
- [Spring Security (by spring.io)](https://docs.spring.io/spring-boot/reference/web/spring-security.html)
- [HttpSecurity vs. WebSecurity in Spring Security](https://www.baeldung.com/spring-security-httpsecurity-vs-websecurity)
- [GitHub WebSecurity.java code](https://github.com/spring-projects/spring-security/blob/main/config/src/main/java/org/springframework/security/config/annotation/web/builders/WebSecurity.java)
- [Spring Boot Security, Step By Step — Part 1: Authentication](https://medium.com/@ansgar.nell/spring-boot-security-step-by-step-21ea836499f8)
- [Spring Security for Beginners — The Easiest Guide You’ll Ever Read](https://dev.to/jps27cse/spring-security-for-beginners-the-easiest-guide-youll-ever-read-2cib)
- [Understand Spring @EnableWebSecurity Annotation with Examples](https://www.codejava.net/frameworks/spring/enablewebsecurity-annotation-examples)
- [Spring Boot Security Auto-Configuration](https://www.geeksforgeeks.org/advance-java/spring-boot-security-auto-configuration/)
- [Security Configuration in Spring Boot (The basic)](https://medium.com/@samuelgbenga972/security-configuration-in-spring-boot-the-basic-58721727b3dc)
- [Spring Security: Authentication and Authorization In-Depth](https://www.marcobehler.com/guides/spring-security)
