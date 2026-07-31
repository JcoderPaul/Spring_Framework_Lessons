- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html)

---
### Class HttpSecurity

**Пакет:** [org.springframework.security.config.annotation.web.builders](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/package-summary.html)

```
java.lang.Object
    org.springframework.security.config.annotation.AbstractSecurityBuilder<O>
        org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder<DefaultSecurityFilterChain, HttpSecurity>
            org.springframework.security.config.annotation.web.builders.HttpSecurity
```

**Все реализованные интерфейсы:** 
- [SecurityBuilder<DefaultSecurityFilterChain>](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/SecurityBuilder.html),
- [HttpSecurityBuilder<HttpSecurity>](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/HttpSecurityBuilder.html)

```java
public final class HttpSecurity
    extends AbstractConfiguredSecurityBuilder<DefaultSecurityFilterChain,HttpSecurity>
        implements SecurityBuilder<DefaultSecurityFilterChain>,
                   HttpSecurityBuilder<HttpSecurity>
```

[HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html) 
аналогичен элементу XML <http> Spring Security в конфигурации пространства имен. Он позволяет настраивать веб-безопасность для определенных HTTP-запросов. По умолчанию он будет применяться ко всем запросам, но его можно ограничить с помощью [#requestMatcher(RequestMatcher)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html#authorizeHttpRequests(org.springframework.security.config.Customizer)) или других подобных методов.

Пример использования:

Самую базовую конфигурацию на основе формы можно увидеть ниже. Конфигурация потребует, чтобы для любого запрошенного URL-адреса требовался пользователь с ролью «ROLE_USER». 
Он также определяет схему аутентификации в памяти с пользователем, имеющим имя пользователя «user», пароль «password» и роль «ROLE_USER». Дополнительные примеры см. в
документации Java по отдельным методам [HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html).

```java
     @Configuration
     @EnableWebSecurity
     public class FormLoginSecurityConfig {
    
            @Bean
            public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                    http.authorizeHttpRequests().requestMatchers("/**").hasRole("USER").and().formLogin();
                    return http.build();
            }
    
            @Bean
            public UserDetailsService userDetailsService() {
                    UserDetails user = User.withDefaultPasswordEncoder()
                            .username("user")
                            .password("password")
                            .roles("USER")
                            .build();
                    return new InMemoryUserDetailsManager(user);
            }
     }
```

См. так же: [EnableWebSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/EnableWebSecurity.html)

---
#### Вложенный класс

- `class HttpSecurity.RequestMatcherConfigurer` - Позволяет сопоставлять HTTP-запросы, HttpSecurity для которых это будет использоваться.

---
#### Конструктор

- `HttpSecurity(ObjectPostProcessor<Object> objectPostProcessor, AuthenticationManagerBuilder authenticationBuilder, Map<Class<?>,Object> sharedObjects)` - Создает новый экземпляр.

Где, параметры: 
- `objectPostProcessor` - [ObjectPostProcessor](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/ObjectPostProcessor.html), который следует использовать;
- `authenticationBuilder` - [AuthenticationManagerBuilder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/authentication/builders/AuthenticationManagerBuilder.html) использовать для дополнительных обновлений;
- `sharedObjects` - общие объекты для инициализации [HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html) с помощью;

**Смотрите также:** [WebSecurityConfiguration](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfiguration.html)

---
#### Методы

- `HttpSecurity addFilter(jakarta.servlet.Filter filter)` - Добавляет объект Filter, который должен быть экземпляром или расширением одного из фильтров, предоставляемых в рамках платформы безопасности.
- `HttpSecurity addFilterAfter(jakarta.servlet.Filter filter, Class<? extends jakarta.servlet.Filter> afterFilter)` - Позволяет добавить Filter после одного из известных Filter классов.
- `HttpSecurity addFilterAt(jakarta.servlet.Filter filter, Class<? extends jakarta.servlet.Filter> atFilter)` - Добавляет фильтр в расположение указанного класса фильтра.
- `HttpSecurity addFilterBefore(jakarta.servlet.Filter filter, Class<? extends jakarta.servlet.Filter> beforeFilter)` - Позволяет добавить Filter перед одним из известных Filter классов.
- `HttpSecurity anonymous(Customizer<AnonymousConfigurer<HttpSecurity>> anonymousCustomizer)` - Позволяет настроить представление анонимного пользователя.
- `HttpSecurity authenticationManager(AuthenticationManager authenticationManager)` - Настройте значение по умолчанию [AuthenticationManager](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html).
- `HttpSecurity authenticationProvider(AuthenticationProvider authenticationProvider)` - Позволяет добавить дополнительный [AuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationProvider.html) для использования.
- `HttpSecurity authorizeHttpRequests(Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequestsCustomizer)` - Позволяет ограничить доступ на основе HttpServletRequest используемых [RequestMatcher](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/util/matcher/RequestMatcher.html) реализаций.
- `protected void beforeConfigure()` - Вызывается перед вызовом каждого [SecurityConfigurer.configure(SecurityBuilder)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/SecurityConfigurer.html#configure(B)) метода.
- `HttpSecurity cors(Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer)` - Добавляет элемент CorsFilter для использования.
- `HttpSecurity csrf(Customizer<CsrfConfigurer<HttpSecurity>> csrfCustomizer)` - Включает защиту CSRF.
- `HttpSecurity exceptionHandling(Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingCustomizer)` - Позволяет настроить обработку исключений.
- `HttpSecurity formLogin(Customizer<FormLoginConfigurer<HttpSecurity>> formLoginCustomizer)` - Указывает на поддержку аутентификации на основе форм.
- `HttpSecurity headers(Customizer<HeadersConfigurer<HttpSecurity>> headersCustomizer)` - Добавляет заголовки безопасности в ответ.
- `HttpSecurity httpBasic(Customizer<HttpBasicConfigurer<HttpSecurity>> httpBasicCustomizer)` - Настраивает базовую аутентификацию HTTP.
- `HttpSecurity jee(Customizer<JeeConfigurer<HttpSecurity>> jeeCustomizer)` - Настраивает предварительную аутентификацию на основе контейнера.
- `HttpSecurity logout(Customizer<LogoutConfigurer<HttpSecurity>> logoutCustomizer)` - Обеспечивает поддержку выхода из системы.
- `HttpSecurity oauth2Client(Customizer<OAuth2ClientConfigurer<HttpSecurity>> oauth2ClientCustomizer)` - Настраивает поддержку клиента OAuth 2.0.
- `HttpSecurity oauth2Login(Customizer<OAuth2LoginConfigurer<HttpSecurity>> oauth2LoginCustomizer)` - Настраивает поддержку аутентификации с использованием поставщика OAuth 2.0 и/или OpenID Connect 1.0.
- `HttpSecurity oauth2ResourceServer(Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>> oauth2ResourceServerCustomizer)` - Настраивает поддержку сервера ресурсов OAuth 2.0.
- `OidcLogoutConfigurer<HttpSecurity> oidcLogout()`
- `HttpSecurity oidcLogout(Customizer<OidcLogoutConfigurer<HttpSecurity>> oidcLogoutCustomizer)`
- `HttpSecurity passwordManagement(Customizer<PasswordManagementConfigurer<HttpSecurity>> passwordManagementCustomizer)` - Добавляет поддержку управления паролями.
- `protected DefaultSecurityFilterChain performBuild()` - Подклассы должны реализовать этот метод для построения возвращаемого объекта.
- `HttpSecurity portMapper(Customizer<PortMapperConfigurer<HttpSecurity>> portMapperCustomizer)` - Позволяет настроить файл [PortMapper](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/PortMapper.html), доступный из [AbstractConfiguredSecurityBuilder.getSharedObject(Class)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/AbstractConfiguredSecurityBuilder.html#getSharedObject(java.lang.Class)).
- `HttpSecurity rememberMe(Customizer<RememberMeConfigurer<HttpSecurity>> rememberMeCustomizer)` - Позволяет настроить аутентификацию «Запомнить меня».
- `HttpSecurity requestCache(Customizer<RequestCacheConfigurer<HttpSecurity>> requestCacheCustomizer)` - Позволяет настроить кэш запросов.
- `HttpSecurity requiresChannel(Customizer<ChannelSecurityConfigurer<HttpSecurity>.ChannelRequestMatcherRegistry> requiresChannelCustomizer)` - Настраивает безопасность канала.
- `HttpSecurity saml2Login(Customizer<Saml2LoginConfigurer<HttpSecurity>> saml2LoginCustomizer)` - Настраивает поддержку аутентификации с помощью поставщика услуг SAML 2.0.
- `HttpSecurity saml2Logout(Customizer<Saml2LogoutConfigurer<HttpSecurity>> saml2LogoutCustomizer)` - Настраивает поддержку выхода из системы для проверяющей стороны SAML 2.0.
- `HttpSecurity saml2Metadata(Customizer<Saml2MetadataConfigurer<HttpSecurity>> saml2MetadataConfigurer)` - Настраивает конечную точку метаданных SAML 2.0, которая представляет конфигурации проверяющей стороны в `<md:EntityDescriptor>` полезных данных.
- `HttpSecurity securityContext(Customizer<SecurityContextConfigurer<HttpSecurity>> securityContextCustomizer)` - Настраивает управление [SecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html) на [SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContextHolder.html) промежутках HttpServletRequest.
- `HttpSecurity securityMatcher(String... patterns)` - Позволяет настроить [HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html) вызов только при совпадении с предоставленным шаблоном.
- `HttpSecurity securityMatcher(RequestMatcher requestMatcher)` - Позволяет настроить [HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html) вызов только при совпадении с предоставленным файлом [RequestMatcher](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/web/util/matcher/RequestMatcher.html).
- `HttpSecurity securityMatchers(Customizer<HttpSecurity.RequestMatcherConfigurer> requestMatcherCustomizer)` - Позволяет указать, для каких HttpServletRequest экземпляров это [HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html) будет вызываться.
- `HttpSecurity servletApi(Customizer<ServletApiConfigurer<HttpSecurity>> servletApiCustomizer)` - Интегрирует HttpServletRequest методы со значениями, найденными в файле [SecurityContext](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/context/SecurityContext.html).
- `HttpSecurity sessionManagement(Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementCustomizer)` - Позволяет настроить управление сеансами.
- `<C> void setSharedObject(Class<C> sharedType,C object)` - Устанавливает объект, который используется несколькими [SecurityConfigurer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/SecurityConfigurer.html).
- `HttpSecurity userDetailsService(UserDetailsService userDetailsService)` - Позволяет добавить дополнительный [UserDetailsService](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.html) для использования.
- `HttpSecurity x509(Customizer<X509Configurer<HttpSecurity>> x509Customizer)` - Настраивает предварительную аутентификацию на основе X509.

---
### Устаревшие методы

- `AnonymousConfigurer<HttpSecurity> anonymous()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorizeHttpRequests()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `CorsConfigurer<HttpSecurity> cors()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `CsrfConfigurer<HttpSecurity> csrf()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `ExceptionHandlingConfigurer<HttpSecurity> exceptionHandling()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `FormLoginConfigurer<HttpSecurity> formLogin()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `HeadersConfigurer<HttpSecurity> headers()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `HttpBasicConfigurer<HttpSecurity> httpBasic()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `JeeConfigurer<HttpSecurity> jee()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `LogoutConfigurer<HttpSecurity> logout()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `OAuth2ClientConfigurer<HttpSecurity> oauth2Client()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `OAuth2LoginConfigurer<HttpSecurity> oauth2Login()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `OAuth2ResourceServerConfigurer<HttpSecurity> oauth2ResourceServer()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `PortMapperConfigurer<HttpSecurity> portMapper()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `RememberMeConfigurer<HttpSecurity> rememberMe()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `RequestCacheConfigurer<HttpSecurity> requestCache()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `ChannelSecurityConfigurer<HttpSecurity>.ChannelRequestMatcherRegistry requiresChannel()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `Saml2LoginConfigurer<HttpSecurity> saml2Login()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `SecurityContextConfigurer<HttpSecurity> securityContext()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `Saml2MetadataConfigurer<HttpSecurity> saml2Metadata()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `Saml2LogoutConfigurer<HttpSecurity> saml2Logout()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `ServletApiConfigurer<HttpSecurity> servletApi()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `HttpSecurity.RequestMatcherConfigurer securityMatchers()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests()` - Устарело. Для удаления в 7.0.
- `HttpSecurity authorizeRequests(Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry> authorizeRequestsCustomizer)` - Устарело. Для удаления в 7.0.
- `SessionManagementConfigurer<HttpSecurity> sessionManagement()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.
- `X509Configurer<HttpSecurity> x509()` - Устарело, подлежит удалению: этот элемент API может быть удален в будущей версии. Для удаления в 7.0.

---
- Методы, унаследованные от класса [AbstractConfiguredSecurityBuilder](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/AbstractConfiguredSecurityBuilder.html): apply, beforeInit, doBuild, getConfigurer, getConfigurers, getOrBuild, getSharedObject, getSharedObjects, objectPostProcessor, postProcess, removeConfigurer, removeConfigurers, with
- Методы, унаследованные от класса [AbstractSecurityBuilder](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/AbstractSecurityBuilder.html): build, getObject
- Методы, унаследованные от класса [Object](https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html): clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait
- Методы, унаследованные от интерфейса [HttpSecurityBuilder](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/web/HttpSecurityBuilder.html): getConfigurer, getSharedObject, removeConfigurer
- Методы, унаследованные от интерфейса [SecurityBuilder](https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/config/annotation/SecurityBuilder.html): build

---
**Доп. материал:**
- [GitHub HttpSecurity](https://github.com/spring-projects/spring-security/blob/main/config/src/main/java/org/springframework/security/config/annotation/web/builders/HttpSecurity.java)
- [Securing a Web Application (from Spring doc)](https://spring.io/guides/gs/securing-web)
- [Java Configuration (from Spring doc)](https://docs.spring.io/spring-security/reference/servlet/configuration/java.html)
- [A Custom Spring SecurityConfigurer](https://www.baeldung.com/spring-security-custom-configurer)
- [Inside HttpSecurity: How Spring Security Builds the Security Filter Chain](https://medium.com/@praveengaddam319/inside-httpsecurity-how-spring-security-builds-the-security-filter-chain-e92f90c8b943)
- [Spring Security for Beginners — The Easiest Guide You’ll Ever Read](https://dev.to/jps27cse/spring-security-for-beginners-the-easiest-guide-youll-ever-read-2cib)
- [Spring Security permit all requests - allow all end points](https://www.codejava.net/frameworks/spring-boot/spring-security-permit-all-requests)
- [HttpSecurity vs. WebSecurity in Spring Security](https://www.baeldung.com/spring-security-httpsecurity-vs-websecurity)
- [Advanced Spring Security - How to create multiple Spring Security Configurations](https://www.danvega.dev/blog/multiple-spring-security-configs)
