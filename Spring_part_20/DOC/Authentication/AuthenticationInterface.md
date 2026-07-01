- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html)

---
### Interface Authentication

**Пакет:** [org.springframework.security.core](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/package-summary.html)

**Все супер-интерфейсы:** 
- [Principal](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/security/Principal.html),
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html)
                      
**Все известные реализующие классы:**
- [AbstractAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AbstractAuthenticationToken.html)
- [AbstractOAuth2TokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/AbstractOAuth2TokenAuthenticationToken.html)
- [AnonymousAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AnonymousAuthenticationToken.html)
- [BearerTokenAuthentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/BearerTokenAuthentication.html)
- [BearerTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/BearerTokenAuthenticationToken.html) 
- [CasAssertionAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/cas/authentication/CasAssertionAuthenticationToken.html) 
- [CasAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/cas/authentication/CasAuthenticationToken.html), 
- [CasServiceTicketAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/cas/authentication/CasServiceTicketAuthenticationToken.html), 
- [DPoPAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/DPoPAuthenticationToken.html), 
- [JaasAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/jaas/JaasAuthenticationToken.html) 
- [JwtAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/JwtAuthenticationToken.html) 
- [KerberosServiceRequestToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/kerberos/authentication/KerberosServiceRequestToken.html) 
- [KerberosUsernamePasswordAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/kerberos/authentication/KerberosUsernamePasswordAuthenticationToken.html) 
- [OAuth2AccessTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AccessTokenAuthenticationToken.html) 
- [OAuth2AuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2AuthenticationToken.html) 
- [OAuth2AuthorizationCodeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2AuthorizationCodeAuthenticationToken.html) 
- [OAuth2AuthorizationCodeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationCodeAuthenticationToken.html) 
- [OAuth2AuthorizationCodeRequestAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationCodeRequestAuthenticationToken.html) 
- [OAuth2AuthorizationConsentAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationConsentAuthenticationToken.html) 
- [OAuth2AuthorizationGrantAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationGrantAuthenticationToken.html) 
- [OAuth2ClientAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientAuthenticationToken.html) 
- [OAuth2ClientCredentialsAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientCredentialsAuthenticationToken.html) 
- [OAuth2ClientRegistrationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientRegistrationAuthenticationToken.html) 
- [OAuth2DeviceAuthorizationConsentAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceAuthorizationConsentAuthenticationToken.html) 
- [OAuth2DeviceAuthorizationRequestAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceAuthorizationRequestAuthenticationToken.html) 
- [OAuth2DeviceCodeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceCodeAuthenticationToken.html) 
- [OAuth2DeviceVerificationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceVerificationAuthenticationToken.html) 
- [OAuth2LoginAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2LoginAuthenticationToken.html) 
- [OAuth2PushedAuthorizationRequestAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2PushedAuthorizationRequestAuthenticationToken.html) 
- [OAuth2RefreshTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2RefreshTokenAuthenticationToken.html) 
- [OAuth2TokenExchangeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenExchangeAuthenticationToken.html) 
- [OAuth2TokenExchangeCompositeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenExchangeCompositeAuthenticationToken.html) 
- [OAuth2TokenIntrospectionAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenIntrospectionAuthenticationToken.html) 
- [OAuth2TokenRevocationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenRevocationAuthenticationToken.html) 
- [OidcClientRegistrationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcClientRegistrationAuthenticationToken.html) 
- [OidcLogoutAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcLogoutAuthenticationToken.html) 
- [OidcUserInfoAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcUserInfoAuthenticationToken.html) 
- [OneTimeTokenAuthentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ott/OneTimeTokenAuthentication.html) 
- [OneTimeTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ott/OneTimeTokenAuthenticationToken.html) 
- [PreAuthenticatedAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/preauth/PreAuthenticatedAuthenticationToken.html) 
- [RememberMeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/RememberMeAuthenticationToken.html) 
- [RunAsUserToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/intercept/RunAsUserToken.html) 
- [Saml2AssertionAuthentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/saml2/provider/service/authentication/Saml2AssertionAuthentication.html) 
- [Saml2Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/saml2/provider/service/authentication/Saml2Authentication.html) 
- [Saml2AuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/saml2/provider/service/authentication/Saml2AuthenticationToken.html) 
- [TestingAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/TestingAuthenticationToken.html)
- [UsernamePasswordAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/UsernamePasswordAuthenticationToken.html) 
- [WebAuthnAuthentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/webauthn/authentication/WebAuthnAuthentication.html)
- [WebAuthnAuthenticationRequestToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/webauthn/authentication/WebAuthnAuthenticationRequestToken.html)

```
  public interface Authentication extends Principal, Serializable
```

Представляет токен для запроса проверки подлинности или для участника, прошедшего проверку подлинности, после обработки
запроса методом [AuthenticationManager.authenticate(Authentication)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html)

После аутентификации запроса аутентификация обычно сохраняется в локальном потоке SecurityContext, управляемом
SecurityContextHolder с помощью используемого механизма аутентификации. Явная аутентификация может быть достигнута
без использования одного из механизмов аутентификации Spring Security, создав экземпляр аутентификации и используя
код:

```java
 SecurityContext context = SecurityContextHolder.createEmptyContext();
 context.setAuthentication(anAuthentication);
 SecurityContextHolder.setContext(context);
```

**!!! Обратите внимание !!! Если для свойства Authentication не установлено значение true, он все равно будет
аутентифицирован любым перехватчиком безопасности (для методов или веб-вызовов), который его встретит.**

В большинстве случаев платформа прозрачно заботится об управлении контекстом безопасности и объектами
аутентификации за вас.

---
#### Методы

---
- `Collection<? extends GrantedAuthority> getAuthorities()` - Устанавливается AuthenticationManager для указания полномочий, предоставленных принципалу.

**!!! Обратите внимание !!! Классы не должны полагаться на это значение как на допустимое, если оно не установлено доверенным AuthenticationManager.**

Реализации должны гарантировать, что изменения в возвращаемом массиве коллекции не влияют на состояние объекта аутентификации или не используют неизменяемый экземпляр.

**Возвращает:** Полномочия, предоставленные принципалу, или пустую коллекцию, если токен не был аутентифицирован. Никогда не возвращает null.

---
- `Object getCredentials()` - Учетные данные, подтверждающие правильность принципала. Обычно это пароль, но может быть и что угодно, относящееся к AuthenticationManager. Ожидается, что вызывающие абоненты заполнят учетные данные.

**Возвращает:** Полномочия, удостоверяющие личность принципала.

---
- `Object getDetails()` - Хранит дополнительные сведения о запросе аутентификации. Это может быть IP-адрес, серийный номер сертификата и т.д.

**Возвращает:** Дополнительные сведения о запросе аутентификации или значение null, если не используется.

---
- `Object getPrincipal()` - Личность аутентифицируемого принципала. В случае запроса аутентификации с именем пользователя и паролем это будет имя пользователя. Ожидается, что вызывающие стороны заполнят субъект для запроса аутентификации.

Реализация AuthenticationManager часто возвращает аутентификацию, содержащую более подробную информацию, в качестве принципала для использования приложением. Многие поставщики аутентификации создают объект UserDetails в качестве субъекта.

**Возвращает:** Принципала аутентифицируемого или аутентифицированный принципал после аутентификации.

---
- `boolean isAuthenticated()` - Используется для указания AbstractSecurityInterceptor-у следует ли предоставлять токен аутентификации для AuthenticationManager-а. Обычно AuthenticationManager (или, чаще, один из его AuthenticationProviders) возвращает неизменяемый токен аутентификации после успешной аутентификации, и в этом случае этот токен может безопасно вернуть true этому методу.

Возврат true повысит производительность, поскольку вызов AuthenticationManager для каждого запроса больше не понадобится.

По соображениям безопасности реализации этого интерфейса должны быть очень осторожны при возврате true из этого метода, если только они не являются неизменяемыми или не имеют какого-либо способа гарантировать, что свойства не были изменены с момента первоначального создания.

**Возвращает:** True, если токен был аутентифицирован и AbstractSecurityInterceptor не нужно снова представлять токен AuthenticationManager для повторной аутентификации.

---
- `void setAuthenticated(boolean isAuthenticated)` - См. `isAuthenticated()`. Реализации всегда должны позволять вызывать этот метод с параметром false, поскольку он используется различными классами для указания того, что токену аутентификации не следует доверять.

Если реализация желает отклонить вызов с параметром true (который указывает на то, что токену аутентификации доверяют, что представляет собой потенциальную угрозу безопасности), реализация должна выдать исключение IllegalArgumentException.

**Параметры:** isAuthenticated — true, если токену следует доверять (что может привести к исключению), или false, если токену нельзя доверять.

**Исключения:** IllegalArgumentException — если попытка сделать токен аутентификации доверенным (путем передачи true в качестве аргумента) отклонена из-за того, что реализация является неизменяемой или реализует собственный альтернативный подход к isAuthenticated().

---
Методы, унаследованные от интерфейса [Principal](https://docs.oracle.com/javase/8/docs/api/java/security/Principal.html): equals, getName, hashCode, implies, toString

---
**См. доп. материалы:**
- [Username/Password Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/index.html)
- [Spring Boot Security, Step By Step — Part 1: Authentication](https://medium.com/@ansgar.nell/spring-boot-security-step-by-step-21ea836499f8)
- [Spring Security Basic Authentication](https://www.baeldung.com/spring-security-basic-authentication)
- [Authentication and Authorization in Spring Boot 3.0 with Spring Security](https://www.geeksforgeeks.org/advance-java/authentication-and-authorization-in-spring-boot-3-0-with-spring-security/)
- [Getting started with Spring Security - Authentication and Authorization](https://dev.to/jhonifaber/getting-started-with-spring-security-authentication-and-authorization-32de)
- [Spring Security Authentication Provider](https://www.baeldung.com/spring-security-authentication-provider)
