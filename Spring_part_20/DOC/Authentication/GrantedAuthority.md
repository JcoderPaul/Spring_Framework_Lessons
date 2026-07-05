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
