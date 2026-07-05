- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/GrantedAuthority.html)

---
### Interface GrantedAuthority

**Пакет:** [org.springframework.security.core](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/package-summary.html)

**Все супер-интерфейсы:** [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html)

**Все реализующие классы:** 
- [FactorGrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/authority/FactorGrantedAuthority.html)
- [JaasGrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/jaas/JaasGrantedAuthority.html),
- [LdapAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/LdapAuthority.html),
- [OAuth2UserAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/user/OAuth2UserAuthority.html),
- [OidcUserAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/oidc/user/OidcUserAuthority.html),
- [SimpleGrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/authority/SimpleGrantedAuthority.html),
- [SwitchUserGrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/switchuser/SwitchUserGrantedAuthority.html)

```
  public interface GrantedAuthority extends Serializable
```

Представляет полномочия, предоставленные объекту [аутентификации - Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html).

GrantedAuthority должен либо представлять себя как строку, либо специально поддерживаться [AccessDecisionManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authorization/AuthorizationManager.html).

---
#### Методы

- `@Nullable String getAuthority()` - Если GrantedAuthority может быть представлен как строка и эта строка имеет достаточную точность, чтобы на нее можно было положиться при принятии решения по управлению доступом с помощью AccessDecisionManager (или делегата), этот метод должен возвращать такую строку.

Если GrantedAuthority не может быть выражен с достаточной точностью в виде строки, должно быть возвращено значение null. Для возврата значения null потребуется AccessDecisionManager (или делегат) для специальной поддержки реализации GrantedAuthority, поэтому следует избегать возврата значения null, если это действительно не требуется.

**Возвращает:** Представление предоставленных полномочий (или значение null, если предоставленные полномочия не могут быть выражены в виде строки с достаточной точностью).

---
**Доп. материал:**
- [Authorization](https://docs.spring.io/spring-security/site/docs/5.2.1.RELEASE/reference/html/authorization.html)
- [Spring Security Roles and Permissions](https://javadevjournal.com/spring-security/spring-security-roles-and-permissions/)
- [Authorization Architecture](https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html)
- [Granted Authority Versus Role in Spring Security](https://www.baeldung.com/spring-security-granted-authority-vs-role)
- [Java Examples for org.springframework.security.core.GrantedAuthority](https://www.javatips.net/api/org.springframework.security.core.grantedauthority)
- [Difference between Role and GrantedAuthority in Spring Security](https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security)
- [Spring Security: Granted Authority](https://www.javaguides.net/2024/04/spring-security-granted-authority.html)
- [Difference between Role and GrantedAuthority in Spring Security](https://www.geeksforgeeks.org/advance-java/difference-between-role-and-grantedauthority-in-spring-security/)
- [Authentication and authorization with Spring-Boot](https://dev.to/m1guelsb/authentication-and-authorization-with-spring-boot-4m2n)
- [Implementing Authentication And Authorization using Spring Security, Kotlin and JWT | An easy and straightforward way](https://blog.devgenius.io/implementing-authentication-and-authorization-using-spring-security-kotlin-and-jwt-an-easy-and-cc82a1f20567)
- [UserDetails and GrantedAuthority](https://hyperskill.org/learn/step/35364)
