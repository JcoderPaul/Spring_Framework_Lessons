- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html)

---
### Interface AuthenticationManager

**Пакет:** [org.springframework.security.authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/package-summary.html)

**Известные реализующие классы:** 
- [ObservationAuthenticationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ObservationAuthenticationManager.html),
- [ProviderManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ProviderManager.html)

```
  @FunctionalInterface
  public interface AuthenticationManager
```

Обрабатывает запрос [аутентификации](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) или [Authentication](../Authentication/AuthenticationInterface.md)

---
#### Методы

- `Authentication authenticate(Authentication authentication)` - Пытается аутентифицировать переданный Authentication объект, возвращая полностью заполненный Authentication объект (включая предоставленные права доступа) в случае успеха.

AuthenticationManager должен соблюдать следующий контракт, касающийся исключений:
- DisabledException должно быть выдано, если учетная запись отключена и AuthenticationManager может проверить это состояние см. [DisabledException](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/DisabledException.html)
- Исключение LockedException должно быть выдано, если учетная запись заблокирована и AuthenticationManager может проверить блокировку учетной записи см. [LockedException](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/LockedException.html)
- При предоставлении неверных учетных данных должно быть выброшено исключение BadCredentialsException. Хотя приведенные выше исключения являются необязательными, AuthenticationManager всегда должен проверять учетные данные см. [BadCredentialsException](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/BadCredentialsException.html)

Исключения должны быть проверены и, если применимо, выбраны в порядке, указанном выше (т.е. если учетная запись отключена или заблокирована, запрос на аутентификацию немедленно отклоняется и процесс проверки учетных данных не выполняется). Это предотвращает проверку учетных данных на отключенных или заблокированных учетных записях.

**Параметры:** `authentication` - объект запроса аутентификации;

**Возвращает:** полностью аутентифицированный объект, включая учетные данные;

**Исключения:** `AuthenticationException` — если [аутентификация не удалась](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/AuthenticationException.html);

---
**Доп. материалы:**
- [Spring Security AuthorizationManager](https://www.baeldung.com/spring-security-authorizationmanager)
- [Authentication and authorization with Spring-Boot](https://dev.to/m1guelsb/authentication-and-authorization-with-spring-boot-4m2n)
- [Security Architecture with Spring](https://examples.javacodegeeks.com/security-architecture-with-spring/)
- [Spring Security Authentication Process Explained In Detailed](https://medium.com/spring-framework/spring-security-authentication-process-explained-in-detailed-5bc0a424a746)
- [Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/index.html)
- [Spring Security: Authentication Manager](https://www.javaguides.net/2024/04/spring-security-authentication-manager.html)
- [Using Spring Security for authentication and authorisation](https://www.springcloud.io/post/2023-06/spring-security/#gsc.tab=0)
- [How To Implement Multi-Factor Authentication with Spring Security](https://medium.com/axgr-dev/how-to-implement-multi-factor-authentication-with-spring-security-bb23aaf874e7)

---
**From medium.com by [Java Jedi](https://java-jedi.medium.com/)**
- [Spring Security Crash Course with Kotlin & Spring Boot](https://java-jedi.medium.com/spring-security-crash-course-with-kotlin-spring-boot-3953c564bb12)
- [Spring Security, Part II: In-Memory Authentication with UserDetailsService](https://java-jedi.medium.com/spring-security-part-ii-in-memory-authentication-with-userdetailsservice-f9ed0e911fc3)
- [Spring Security, Part III: Custom UserDetailsService with Database Authentication](https://java-jedi.medium.com/spring-security-part-iii-custom-userdetailsservice-with-database-authentication-1c39d42f4d8a)
- [Spring Security, Part IV — Custom Authentication with API Key](https://java-jedi.medium.com/spring-security-part-iv-custom-authentication-with-api-key-a9f289e13964)
- [Spring Security, Part V — Implementing Multiple Authentication Providers](https://java-jedi.medium.com/spring-security-part-v-implementing-multiple-authentication-providers-f80a459a5ec3)
- [Spring Security, Part VI — Exploring Authorization](https://java-jedi.medium.com/spring-security-part-vi-exploring-authorization-b34a4795fb4e)
- [Spring Security, Part VII — Exploring Method-Level Authorization](https://java-jedi.medium.com/spring-security-part-viii-exploring-method-level-authorization-ba5ff6f22b10)
