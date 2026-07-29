- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html)

---
### Interface SecurityContext

**Пакет:** [org.springframework.security.core.context](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/package-summary.html)

**Все супер-интерфейсы:** [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html)

**Реализующие классы:** 
- [SecurityContextImpl](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextImpl.html),
- [TransientSecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/TransientSecurityContext.html)

```java
  public interface SecurityContext extends Serializable
```

---
Интерфейс, определяющий минимальную информацию безопасности, связанную с текущим потоком выполнения.

Контекст безопасности хранится в [SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html)

---
#### Методы

- `Authentication getAuthentication()` - Получает участник, прошедший проверку подлинности в данный момент, или токен запроса аутентификации.

**Возвращает:** `Authentication` или `null` если информация для аутентификации недоступна.

---
- `void setAuthentication(Authentication authentication)` - Изменяет текущего аутентифицированного участника или удаляет информацию аутентификации.

**Параметры:** `authentication`- новый `Authentication` токен, или `null` если не требуется хранить дополнительную информацию для аутентификации;

---
**Доп. материалы:**
- [Servlet Authentication Architecture (from docs.spring.io)](https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html)
- [Architecture and Implementation (from docs.spring.io)](https://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/html/technical-overview.html)
- [Retrieve User Information in Spring Security](https://www.baeldung.com/get-user-in-spring-security)
- [SecurityContext with default System authentication/user](https://stackoverflow.com/questions/47078381/securitycontext-with-default-system-authentication-user)
- [Introduction to Spring Method Security](https://www.baeldung.com/spring-security-method-security)
- [Spring Security (video from YouTube)](https://www.youtube.com/playlist?list=PLVz2XdJiJQxynOpTm0DuufOkfWHNamJsF)
