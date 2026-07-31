- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/CredentialsContainer.html)

---
### Interface CredentialsContainer

**Пакет:** [org.springframework.security.core](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/package-summary.html)

**Известные под-интерфейсы:** [LdapUserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/LdapUserDetails.html)

**Известные реализующие классы:** 
- [AbstractAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AbstractAuthenticationToken.html),
- [AbstractOAuth2TokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/AbstractOAuth2TokenAuthenticationToken.html),
- [AnonymousAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AnonymousAuthenticationToken.html), 
- [BearerTokenAuthentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/BearerTokenAuthentication.html), 
- [BearerTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/BearerTokenAuthenticationToken.html),
- [CasAssertionAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/cas/authentication/CasAssertionAuthenticationToken.html),
- [CasAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/cas/authentication/CasAuthenticationToken.html), 
- [CasServiceTicketAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/cas/authentication/CasServiceTicketAuthenticationToken.html), 
- [DPoPAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/DPoPAuthenticationToken.html), 
- [InetOrgPerson](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/InetOrgPerson.html), 
- [JaasAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/jaas/JaasAuthenticationToken.html), 
- [JwtAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/resource/authentication/JwtAuthenticationToken.html), 
- [KerberosServiceRequestToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/kerberos/authentication/KerberosServiceRequestToken.html), 
- [KerberosUsernamePasswordAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/kerberos/authentication/KerberosUsernamePasswordAuthenticationToken.html), 
- [LdapUserDetailsImpl](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/LdapUserDetailsImpl.html), 
- [OAuth2AccessTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AccessTokenAuthenticationToken.html), 
- [OAuth2AuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2AuthenticationToken.html), 
- [OAuth2AuthorizationCodeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2AuthorizationCodeAuthenticationToken.html), 
- [OAuth2AuthorizationCodeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationCodeAuthenticationToken.html), 
- [OAuth2AuthorizationCodeRequestAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationCodeRequestAuthenticationToken.html),
- [OAuth2AuthorizationConsentAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationConsentAuthenticationToken.html),
- [OAuth2AuthorizationGrantAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2AuthorizationGrantAuthenticationToken.html),
- [OAuth2ClientAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientAuthenticationToken.html),
- [OAuth2ClientCredentialsAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientCredentialsAuthenticationToken.html),
- [OAuth2ClientRegistrationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2ClientRegistrationAuthenticationToken.html),
- [OAuth2DeviceAuthorizationConsentAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceAuthorizationConsentAuthenticationToken.html),
- [OAuth2DeviceAuthorizationRequestAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceAuthorizationRequestAuthenticationToken.html),
- [OAuth2DeviceCodeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceCodeAuthenticationToken.html),
- [OAuth2DeviceVerificationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2DeviceVerificationAuthenticationToken.html),
- [OAuth2LoginAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/client/authentication/OAuth2LoginAuthenticationToken.html),
- [OAuth2PushedAuthorizationRequestAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2PushedAuthorizationRequestAuthenticationToken.html),
- [OAuth2RefreshTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2RefreshTokenAuthenticationToken.html),
- [OAuth2TokenExchangeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenExchangeAuthenticationToken.html),
- [OAuth2TokenExchangeCompositeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenExchangeCompositeAuthenticationToken.html),
- [OAuth2TokenIntrospectionAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenIntrospectionAuthenticationToken.html),
- [OAuth2TokenRevocationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/authentication/OAuth2TokenRevocationAuthenticationToken.html),
- [OidcClientRegistrationAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcClientRegistrationAuthenticationToken.html),
- [OidcLogoutAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcLogoutAuthenticationToken.html),
- [OidcUserInfoAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/server/authorization/oidc/authentication/OidcUserInfoAuthenticationToken.html),
- [OneTimeTokenAuthentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ott/OneTimeTokenAuthentication.html),
- [OneTimeTokenAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ott/OneTimeTokenAuthenticationToken.html),
- [Person](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/Person.html),
- [PreAuthenticatedAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/authentication/preauth/PreAuthenticatedAuthenticationToken.html),
- [RememberMeAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/RememberMeAuthenticationToken.html),
- [RunAsUserToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/access/intercept/RunAsUserToken.html),
- [Saml2AssertionAuthentication](../saml2/provider/service/authentication/Saml2AssertionAuthentication.html),
- [Saml2Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/saml2/provider/service/authentication/Saml2Authentication.html),
- [Saml2AuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/saml2/provider/service/authentication/Saml2AuthenticationToken.html),
- [TestingAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/TestingAuthenticationToken.html),
- [User](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/User.html),
- [UsernamePasswordAuthenticationToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/UsernamePasswordAuthenticationToken.html),
- [WebAuthnAuthentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/webauthn/authentication/WebAuthnAuthentication.html),
- [WebAuthnAuthenticationRequestToken](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/webauthn/authentication/WebAuthnAuthenticationRequestToken.html)

```java
  public interface CredentialsContainer
```

Указывает, что реализующий объект содержит конфиденциальные данные, которые можно удалить с помощью метода
eraseCredentials. Ожидается, что реализации будут вызывать метод для любых внутренних объектов, которые также могут
реализовывать этот интерфейс.

Только для внутреннего использования. Пользователи, которые пишут свои собственные реализации [AuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationProvider.html), должны создать и вернуть соответствующий объект [Authentication](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/package-summary.html) без каких-либо конфиденциальных данных, а не использовать этот интерфейс.

---
#### Методы

- `void eraseCredentials()`

---
**См. доп.:**
- [CredentialsContainer](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/credentials-container.html)
- [CredentialsContainer.java](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/core/CredentialsContainer.java)
- [How to Implement Basic Access Authentication in Spring Boot](https://dev.to/antozanini/how-to-implement-basic-access-authentication-in-spring-boot-3hnl)
- [Building a Login System with Spring Boot and Spring Security](https://medium.com/@AlexanderObregon/building-a-login-system-with-spring-boot-and-spring-security-2ef6f110a9cb)
- [Understanding Spring Security](https://medium.com/@vuntt1412/spring-security-custom-authentication-cf781d08c4fc)
