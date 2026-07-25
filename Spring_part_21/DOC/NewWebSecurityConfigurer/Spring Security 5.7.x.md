- [См. отредактированный исходник (ENG)](https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter)

---
### Spring Security: обновление устаревшего адаптера WebSecurityConfigurerAdapter

При создании собственной конфигурации системы безопасности, а вернее, при попытке перенаправить пользователя на нашу
собственную страницу Login (не default от Spring), мы столкнулись с тем, что интересующий нас класс является Deprecated,
а именно - [WebSecurityConfigurerAdapter](https://docs.spring.io/spring-security/site/docs/5.7.0-M2/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.html). 
Сие неудовольствие начинается с версии Spring-a - 5.7 и выше. См. статью - ["Spring Security without the WebSecurityConfigurerAdapter"](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)

**!!! Класс устарел !!! Используйте компонент SecurityFilterChain для настройки HttpSecurity или компонент WebSecurityCustomizer для настройки WebSecurity.**

Ранее WebSecurityConfigurerAdapter предоставлял удобный базовый класс для создания экземпляра WebSecurityConfigurer.
Реализация позволяла настраивать путем переопределения методов. Автоматически применяла результат поиска
AbstractHttpConfigurer из SpringFactoriesLoader, чтобы позволить разработчикам расширить значения по умолчанию.
Для этого вам необходимо было создать класс, расширяющий AbstractHttpConfigurer, а затем создать в пути к классам
файл «META-INF/spring.factories», который выглядит примерно так:

```
  org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer = sample.MyClassThatExtendsAbstractHttpConfigurer
```

Если у вас есть несколько классов, которые необходимо добавить, вы могли использовать «,» для разделения значений.
Например:

```
  org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer =
                        sample.MyClassThatExtendsAbstractHttpConfigurer,
                        sample.OtherThatExtendsAbstractHttpConfigurer
```

И т.д. все методы класса естественно тоже устарели.

И так, ранее Spring Security позволял настраивать HTTP-безопасность для таких функций, как авторизация конечных точек
или конфигурация менеджера аутентификации, путем расширения класса WebSecurityConfigurerAdapter. Однако в последних
версиях Spring отвергает этот подход и поощряет настройку безопасности на основе компонентов.

Рассмотрим, как заменить устаревший вариант настройки приложения Spring Boot.

---
### Spring Security без адаптера WebSecurityConfigurerAdapter

Обычно мы видим классы конфигурации безопасности HTTP Spring, которые расширяют [класс WebSecurityConfigureAdapter](https://www.baeldung.com/java-config-spring-security)

Однако, начиная со Spring 5.7.0-M2, происходит отказ от WebSecurityConfigureAdapter и предлагается создавать
конфигурации без него см. ["Spring Security without the WebSecurityConfigurerAdapter"](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
Давайте создадим пример приложения Spring Boot, использующего аутентификацию в памяти, чтобы продемонстрировать этот
новый тип конфигурации.

Сначала мы определим наш класс конфигурации:

```java
  @Configuration
  @EnableWebSecurity
  @EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
  public class SecurityConfig {
  
      /* config */
  
  }
```

Мы добавим аннотации безопасности метода см. ["Introduction to Spring Method Security"](https://www.baeldung.com/spring-security-method-security), 
чтобы включить обработку на основе разных ролей.

---
### Настроить аутентификацию

Ранее с помощью WebSecurityConfigureAdapter мы использовали AuthenticationManagerBuilder для установки контекста
аутентификации. Теперь, чтобы не использовать устаревший класс, мы можем определить компонент UserDetailsManager
или UserDetailsService:

```java
  @Bean
  public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
      InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
      manager.createUser(User.withUsername("user")
        .password(bCryptPasswordEncoder.encode("userPass"))
        .roles("USER")
        .build());
      manager.createUser(User.withUsername("admin")
        .password(bCryptPasswordEncoder.encode("adminPass"))
        .roles("USER", "ADMIN")
        .build());
      return manager;
  }
```

Или, учитывая наш UserDetailService, мы можем даже установить AuthenticationManager:

```java
  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http,
                                                     BCryptPasswordEncoder bCryptPasswordEncoder,
                                                     UserDetailService userDetailService)
    throws Exception {
      return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(bCryptPasswordEncoder)
        .and()
        .build();
  }
```

Аналогично это будет работать, если мы будем использовать аутентификацию JDBC или LDAP.

---
### Настройка HTTP-безопасности (HTTP Security)

Что еще более важно, если мы хотим избежать прекращения поддержки HTTP-безопасности, мы можем (должны) создать компонент
SecurityFilterChain.

Например, предположим, что мы хотим защитить конечные точки в зависимости от ролей и оставить анонимную точку входа
только для входа в систему. Мы также ограничим любой запрос на удаление ролью администратора. Мы будем использовать
базовую аутентификацию см. ["Spring Security Basic Authentication"](https://www.baeldung.com/spring-security-basic-authentication):

```java
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                  authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.DELETE)
                        .hasRole("ADMIN")
                        .requestMatchers("/admin/**")
                        .hasAnyRole("ADMIN")
                        .requestMatchers("/user/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/login/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                  httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
  
      return http.build();
  }
```

Служба безопасности HTTP создаст объект DefaultSecurityFilterChain для загрузки средств сопоставления запросов и фильтров.

---
### Настройка веб-безопасности

Для веб-безопасности теперь мы можем использовать интерфейс обратного вызова WebSecurityCustomizer.

Мы добавим уровень отладки и проигнорируем некоторые пути, например изображения или скрипты:

```java
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
      return web -> web.debug(securityDebug).ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
  }
```

---
### Контроллер конечных точек

Теперь мы определим простой класс контроллера REST для нашего приложения:

```java
  @RestController
  public class ResourceController {
      @GetMapping("/login")
      public String loginEndpoint() {
          return "Login!";
      }
  
      @GetMapping("/admin")
      public String adminEndpoint() {
          return "Admin!";
      }
  
      @GetMapping("/user")
      public String userEndpoint() {
          return "User!";
      }
  
      @GetMapping("/all")
      public String allRolesEndpoint() {
          return "All Roles!";
      }
  
      @DeleteMapping("/delete")
      public String deleteEndpoint(@RequestBody String s) {
          return "I am deleting " + s;
      }
  }
```

Как мы упоминали ранее при определении безопасности HTTP, мы добавим общую конечную точку `/login`, доступную любому,
определенные конечные точки для администратора и пользователя, а также конечную точку `/all`, не защищенную ролью, но все
же требующую аутентификации.

---
### Тестирование конечных точек

Давайте добавим нашу новую конфигурацию в [Spring Boot Test](https://www.baeldung.com/spring-boot-testing), используя макет MVC для проверки наших конечных точек.

---
#### Тестирование анонимных пользователей

Анонимные пользователи могут получить доступ к конечной точке `/login`. Если они попытаются получить доступ к чему-то еще,
они будут неавторизованы (ответ 401):

```java
  @Test
  @WithAnonymousUser
  public void whenAnonymousAccessLogin_thenOk() throws Exception {
      mvc.perform(get("/login"))
        .andExpect(status().isOk());
  }
  
  @Test
  @WithAnonymousUser
  public void whenAnonymousAccessRestrictedEndpoint_thenIsUnauthorized() throws Exception {
      mvc.perform(get("/all"))
        .andExpect(status().isUnauthorized());
  }
```

Более того, для всех конечных точек, кроме `/login`, нам всегда требуется аутентификация, как и для конечной точки `/all`.

---
#### Тестовая роль пользователя

Роль пользователя может получить доступ к общим конечным точкам и всем другим путям, которые мы предоставили для этой роли:

```java
  @Test
  @WithUserDetails()
  public void whenUserAccessUserSecuredEndpoint_thenOk() throws Exception {
      mvc.perform(get("/user"))
        .andExpect(status().isOk());
  }
  
  @Test
  @WithUserDetails()
  public void whenUserAccessRestrictedEndpoint_thenOk() throws Exception {
      mvc.perform(get("/all"))
        .andExpect(status().isOk());
  }
  
  @Test
  @WithUserDetails()
  public void whenUserAccessAdminSecuredEndpoint_thenIsForbidden() throws Exception {
      mvc.perform(get("/admin"))
        .andExpect(status().isForbidden());
  }
  
  @Test
  @WithUserDetails()
  public void whenUserAccessDeleteSecuredEndpoint_thenIsForbidden() throws Exception {
      mvc.perform(delete("/delete"))
        .andExpect(status().isForbidden());
  }
```

Стоит отметить, что если роль пользователя пытается получить доступ к конечной точке, защищенной администратором,
пользователь получает «запрещенную» (ответ 403) ошибку.

И наоборот, кто-то без учетных данных, например аноним в предыдущем примере, получит «несанкционированную» ошибку
(ответ 401).

---
#### Тестовая роль администратора

Как мы видим, кто-то с ролью администратора может получить доступ к любой конечной точке:

```java
  @Test
  @WithUserDetails(value = "admin")
  public void whenAdminAccessUserEndpoint_thenOk() throws Exception {
      mvc.perform(get("/user"))
        .andExpect(status().isOk());
  }
  
  @Test
  @WithUserDetails(value = "admin")
  public void whenAdminAccessAdminSecuredEndpoint_thenIsOk() throws Exception {
      mvc.perform(get("/admin"))
        .andExpect(status().isOk());
  }
  
  @Test
  @WithUserDetails(value = "admin")
  public void whenAdminAccessDeleteSecuredEndpoint_thenIsOk() throws Exception {
      mvc.perform(delete("/delete").content("{}"))
        .andExpect(status().isOk());
  }
```

---
### Итог:

Мы коротко рассмотрели, как создать конфигурацию Spring Security без использования WebSecurityConfigureAdapter и
заменить ее при создании компонентов для аутентификации, HTTP-безопасности и веб-безопасности.

[Примеры рабочего кода можно найти на GitHub](https://github.com/eugenp/tutorials/tree/master/spring-security-modules/spring-security-web-boot-4)

---
**См. доп. материалы:**

- [Spring Security without the WebSecurityConfigurerAdapter](https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter)
- [Introduction to Java Config for Spring Security](https://www.baeldung.com/java-config-spring-security)
- [Introduction to Spring Method Security](https://www.baeldung.com/spring-security-method-security)
- [Spring Security Basic Authentication](https://www.baeldung.com/spring-security-basic-authentication)
- [Testing in Spring Boot](https://www.baeldung.com/spring-boot-testing)
- [Class HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html)
- [Class HttpSecurity (ver. 5.2.x)](https://javadoc.io/doc/org.springframework.security/spring-security-config/5.2.0.RELEASE/org/springframework/security/config/annotation/web/builders/HttpSecurity.html)
- [Class WebSecurityConfigurerAdapter](https://docs.spring.io/spring-security/site/docs/5.7.0-M2/api/org/springframework/security/config/annotation/web/configuration/WebSecurityConfigurerAdapter.html)
