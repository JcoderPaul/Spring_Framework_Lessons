- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationProvider.html)

---
### Interface AuthenticationProvider

**Пакет:** [org.springframework.security.authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/package-summary.html)

**Известные реализующие классы:** 
- [AbstractJaasAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/jaas/AbstractJaasAuthenticationProvider.html),
- [AbstractLdapAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/authentication/AbstractLdapAuthenticationProvider.html), 
- [AbstractUserDetailsAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/AbstractUserDetailsAuthenticationProvider.html), 
- [ActiveDirectoryLdapAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/authentication/ad/ActiveDirectoryLdapAuthenticationProvider.html), 
- [AnonymousAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AnonymousAuthenticationProvider.html), 
- [AuthenticationManagerBeanDefinitionParser.NullAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/authentication/AuthenticationManagerBeanDefinitionParser.NullAuthenticationProvider.html), 
- [CasAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/cas/authentication/CasAuthenticationProvider.html), 
- [ClientSecretAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/ClientSecretAuthenticationProvider.html), 
- [DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html), 
- [DefaultJaasAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/jaas/DefaultJaasAuthenticationProvider.html), 
- [DPoPAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/DPoPAuthenticationProvider.html), 
- [JaasAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/jaas/JaasAuthenticationProvider.html), 
- [JwtAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/JwtAuthenticationProvider.html), 
- [JwtClientAssertionAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/JwtClientAssertionAuthenticationProvider.html), 
- [KerberosAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/kerberos/authentication/KerberosAuthenticationProvider.html), 
- [KerberosServiceAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/kerberos/authentication/KerberosServiceAuthenticationProvider.html), 
- [LdapAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/authentication/LdapAuthenticationProvider.html), 
- [OAuth2AuthorizationCodeAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2AuthorizationCodeAuthenticationProvider.html), 
- [OAuth2AuthorizationCodeAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationCodeAuthenticationProvider.html), 
- [OAuth2AuthorizationCodeRequestAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationCodeRequestAuthenticationProvider.html), 
- [OAuth2AuthorizationConsentAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationConsentAuthenticationProvider.html), 
- [OAuth2ClientCredentialsAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientCredentialsAuthenticationProvider.html), 
- [OAuth2ClientRegistrationAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientRegistrationAuthenticationProvider.html), 
- [OAuth2DeviceAuthorizationConsentAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceAuthorizationConsentAuthenticationProvider.html), 
- [OAuth2DeviceAuthorizationRequestAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceAuthorizationRequestAuthenticationProvider.html), 
- [OAuth2DeviceCodeAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceCodeAuthenticationProvider.html), 
- [OAuth2DeviceVerificationAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceVerificationAuthenticationProvider.html), 
- [OAuth2LoginAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2LoginAuthenticationProvider.html), 
- [OAuth2PushedAuthorizationRequestAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2PushedAuthorizationRequestAuthenticationProvider.html), 
- [OAuth2RefreshTokenAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2RefreshTokenAuthenticationProvider.html), 
- [OAuth2TokenExchangeAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenExchangeAuthenticationProvider.html), 
- [OAuth2TokenIntrospectionAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenIntrospectionAuthenticationProvider.html), 
- [OAuth2TokenRevocationAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenRevocationAuthenticationProvider.html), 
- [OidcAuthorizationCodeAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/oidc/authentication/OidcAuthorizationCodeAuthenticationProvider.html), 
- [OidcClientConfigurationAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcClientConfigurationAuthenticationProvider.html), 
- [OidcClientRegistrationAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcClientRegistrationAuthenticationProvider.html), 
- [OidcLogoutAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcLogoutAuthenticationProvider.html), 
- [OidcUserInfoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcUserInfoAuthenticationProvider.html), 
- [OneTimeTokenAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ott/OneTimeTokenAuthenticationProvider.html), 
- [OpaqueTokenAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/OpaqueTokenAuthenticationProvider.html), 
- [PreAuthenticatedAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/preauth/PreAuthenticatedAuthenticationProvider.html), 
- [PublicClientAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/PublicClientAuthenticationProvider.html), 
- [RememberMeAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/RememberMeAuthenticationProvider.html), 
- [RunAsImplAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/intercept/RunAsImplAuthenticationProvider.html), 
- [TestingAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/TestingAuthenticationProvider.html), 
- [WebAuthnAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/webauthn/authentication/WebAuthnAuthenticationProvider.html), 
- [X509ClientCertificateAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/X509ClientCertificateAuthenticationProvider.html)

```java
  public interface AuthenticationProvider
```

Указывает, что класс может обрабатывать определенную реализацию [аутентификации - Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html).

### Методы:

---
- `Authentication authenticate(Authentication authentication)` - Выполняет аутентификацию с помощью того же контракта, что и [AuthenticationManager.authenticate(Authentication)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html#authenticate(org.springframework.security.core.Authentication)).

- **Параметры:** `authentication` - объект запроса аутентификации.
- **Возвращает:** полностью аутентифицированный объект, включая учетные данные. Может вернуться null, если `AuthenticationProvider` не может поддерживать аутентификацию переданного Authentication объекта. В таком случае будет опробован следующий класс `AuthenticationProvider`, поддерживающий представленный Authentication класс.
- **Исключения:** [AuthenticationException](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/AuthenticationException.html) - если аутентификация не удалась.

---
- `boolean supports(Class<?> authentication)` - Возвращает, true если это `AuthenticationProvider` поддерживает указанный `Authentication` объект.

Возврат true не гарантирует, что AuthenticationProvider-у удастся аутентифицировать представленный экземпляр класса Authentication. Это просто указывает на то, что он может поддержать более тщательную оценку этого вопроса. Из AuthenticationProvider все еще может вернуться null из метода authenticate(Authentication), чтобы указать, AuthenticationProvider-у, что следует попробовать другой.

Выбор AuthenticationProvider способного выполнить аутентификацию проводится во время выполнения ProviderManager.

- **Параметры:** `authentication` - поддерживаемый класс.
- **Возвращает:** true если реализация может более внимательно оценить Authentication представленный класс.

---
**Доп. материал:**
- [The AuthenticationManager, ProviderManager and AuthenticationProviders](https://docs.spring.io/spring-security/site/docs/3.1.x/reference/core-services.html)
- [Spring Security Authentication Provider](https://www.baeldung.com/spring-security-authentication-provider)
- [Implement custom AuthenticationProvider in Spring Security 2.06](https://stackoverflow.com/questions/8649818/implement-custom-authenticationprovider-in-spring-security-2-06)
- [Why AuthenticationProvider is the Heart of Spring Security (And How to Use It)](https://medium.com/@praveengaddam319/why-authenticationprovider-is-the-heart-of-spring-security-and-how-to-use-it-eb053988d047)
- [Spring Security - Authentication Providers](https://www.geeksforgeeks.org/java/spring-security-authentication-providers/)
- [Spring Security - Authentication Provider](https://www.tutorialspoint.com/spring_security/spring_security_authentication_provider.htm)
- [Spring Security: Authentication Provider](https://www.javaguides.net/2024/04/spring-security-authentication-provider.html)
- [Dive into the Spring Security Architecture](https://dev.to/sohailshah/dive-into-the-spring-security-architecture-1dhf)
