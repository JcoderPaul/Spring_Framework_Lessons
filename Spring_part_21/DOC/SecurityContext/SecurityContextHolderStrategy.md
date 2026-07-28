- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolderStrategy.html)

---
### Interface SecurityContextHolderStrategy

**Пакет:** [org.springframework.security.core.context](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/package-summary.html)

**Реализующие классы:** 
- [ListeningSecurityContextHolderStrategy](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/ListeningSecurityContextHolderStrategy.html),
- [TestSecurityContextHolderStrategyAdapter](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/test/context/TestSecurityContextHolderStrategyAdapter.html)

```java
  public interface SecurityContextHolderStrategy
```

Стратегия хранения информации о контексте безопасности в потоке. Предпочтительная стратегия загружается [SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html).

---
#### Методы

- `void clearContext()` - Очищает текущий контекст.
- `SecurityContext createEmptyContext()` - Создает новую пустую реализацию контекста для использования реализациями SecurityContextRepository при первом создании нового контекста.
- `SecurityContext getContext()` - Получает текущий контекст.
- `default Supplier<SecurityContext> getDeferredContext()` - Получает объект [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html), который возвращает текущий контекст.
- `void setContext(SecurityContext context)` - Устанавливает текущий контекст.
- `default void setDeferredContext(Supplier<SecurityContext> deferredContext)` - Устанавливает объект [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html), который вернет текущий контекст.

---
**Доп. материалы:**
- [Architecture and Implementation (from spring.io)](https://docs.spring.io/spring-security/site/docs/3.1.x/reference/technical-overview.html)
- [Servlet Authentication Architecture (from spring.io)](https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html)
- [What is SecurityContext and SecurityContextHolder in Spring Security?](https://www.javacodegeeks.com/2018/02/securitycontext-securitycontextholder-spring-security.html)
- [Demystifying Authentication in Spring Security](https://dev.to/esteban389/demystifying-authentication-in-spring-security-57oj)
- [Spring Security](https://fizalihsan.github.io/technology/spring-security.html)
