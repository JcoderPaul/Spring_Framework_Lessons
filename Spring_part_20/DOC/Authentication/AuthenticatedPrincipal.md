- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/AuthenticatedPrincipal.html)

---
### Interface AuthenticatedPrincipal

**Пакет:** [org.springframework.security.core](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/package-summary.html)

**Все под-интерфейсы:** 
- [OAuth2AuthenticatedPrincipal](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/OAuth2AuthenticatedPrincipal.html),
- [OAuth2User](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/user/OAuth2User.html),
- [OidcUser](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/oidc/user/OidcUser.html),
- [Saml2AuthenticatedPrincipal](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/saml2/provider/service/authentication/Saml2AuthenticatedPrincipal.html)

**Все реализующие классы:** 
- [DefaultOAuth2AuthenticatedPrincipal](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/DefaultOAuth2AuthenticatedPrincipal.html),
- [DefaultOAuth2User](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/user/DefaultOAuth2User.html),
- [DefaultOidcUser](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/core/oidc/user/DefaultOidcUser.html),
- [DefaultSaml2AuthenticatedPrincipal](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/saml2/provider/service/authentication/DefaultSaml2AuthenticatedPrincipal.html),
- [OAuth2IntrospectionAuthenticatedPrincipal](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/introspection/OAuth2IntrospectionAuthenticatedPrincipal.html)

```
  public interface AuthenticatedPrincipal
```

Представление проверенного участника после успешной аутентификации [запроса на аутентификацию](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) с помощью метода [AuthenticationManager.authenticate(Authentication)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html#authenticate(org.springframework.security.core.Authentication)). Разработчики обычно предоставляют собственное представление принципала, которое обычно содержит информацию, описывающую объект принципала, например имя/отчество/фамилию,
адрес, адрес электронной почты, телефон, идентификатор и т. д.

Этот интерфейс позволяет разработчикам предоставлять определенные атрибуты своего пользовательского объекта или
представление принципала в общем виде.

---
**См. так же:**
- [Authentication.getPrincipal()](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html)
- [UserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html)

---
**Методы**

- String getName() - Возвращает имя аутентифицированного файла Principal.

---
**См. так же:**
- [Servlet Authentication Architecture](https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html)
- [Retrieve User Information in Spring Security](https://www.baeldung.com/get-user-in-spring-security)
- [Getting Started with Spring Security: Understand the Concept of Principal and Authentication Object](https://medium.com/@lordpacific/getting-started-with-spring-security-understand-the-concept-of-principal-and-authentication-object-07c89379c8de)
- [Springboot Security: Authentication and Authorization. Part 1](https://kelechidivine.medium.com/the-strategy-of-defence-springboot-security-authentication-and-authorization-part-1-51b03fbe800b)
- [The mechanism of Spring security](https://ducmanhphan.github.io/2019-02-09-The-mechanism-of-spring-security/)
- [Authentication, Authorization and Spring Security](https://medium.com/@yunussiddiqui55/authentication-authorization-and-spring-security-5ff686e12b14)
- [Spring Security: Authentication and Authorization In-Depth](https://www.marcobehler.com/guides/spring-security)
