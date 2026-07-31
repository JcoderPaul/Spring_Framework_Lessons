- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/User.html)

---
### Class User

**Пакет:** [org.springframework.security.core.userdetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html)

```
java.lang.Object
    org.springframework.security.core.userdetails.User
```

**Все реализованные интерфейсы:**
- [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html),
- [CredentialsContainer](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/CredentialsContainer.html),
- [UserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html)

```
public class User
        extends Object
            implements UserDetails, CredentialsContainer
```

Моделирует основную информацию о пользователе, полученную с помощью [UserDetailsService](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.html).

Разработчики могут использовать этот класс напрямую, создавать его подклассы или писать собственную реализацию [UserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html) с нуля.

Реализации равенства и хэш-кода основаны только на свойстве имени пользователя, поскольку цель состоит в том, чтобы
поиск одного и того же объекта-участника пользователя (например, в реестре пользователей) соответствовал тому, где
объекты представляют одного и того же пользователя, а не только тогда, когда все свойства ( полномочия, пароль например)
одинаковы.

Обратите внимание, что эта реализация не является неизменяемой. Он реализует интерфейс CredentialsContainer, позволяющий
стереть пароль после аутентификации. Это может вызвать побочные эффекты, если вы сохраняете экземпляры в памяти и
повторно используете их. Если да, убедитесь, что вы возвращаете копию из своего [UserDetailsService](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.html) каждый раз, когда он
вызывается.

---
**Вложенный класс**

- `static final class User.UserBuilder` - Создает пользователя, которого нужно добавить.

---
**Конструктор**

`User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)` - Создайте объект User с деталями, требуемыми [DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html).

Где, параметры: 
- `username` - имя пользователя, представленное [DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html);
- `password` - пароль, который необходимо предъявить [DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/dao/DaoAuthenticationProvider.html);
- `enabled` - установлено, true если пользователь включен;
- `accountNonExpired` - устанавливается, true если срок действия учетной записи не истек;
- `credentialsNonExpired` - устанавливается, true если срок действия учетных данных не истек;
- `accountNonLocked` - устанавливается, true если учетная запись не заблокирована;
- `authorities` - полномочия, которые должны быть предоставлены вызывающему абоненту, если он предоставил правильное имя пользователя и пароль и пользователь включен. Не null.

**Исключения:** [IllegalArgumentException](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/IllegalArgumentException.html) - если null значение было передано как параметр или как элемент [GrantedAuthority](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/GrantedAuthority.html) коллекции.

`User(String username, String password, Collection<? extends GrantedAuthority> authorities)` - Вызывает более сложный конструктор со всеми логическими аргументами, установленными в true.

---
#### Методы

- `static User.UserBuilder builder()` - Создает UserBuilder
- `boolean equals(Object obj)` - Возвращает значение true, если предоставленный объект является User экземпляром с тем же username значением.
- `Collection<GrantedAuthority> getAuthorities()` - Возвращает полномочия, предоставленные пользователю.
- `String getPassword()` - Возвращает пароль, используемый для аутентификации пользователя.
- `String getUsername()` - Возвращает имя пользователя, используемое для аутентификации пользователя.
- `int hashCode()` - Возвращает хеш-код файла username.
- `boolean isAccountNonExpired()` - Указывает, истек ли срок действия учетной записи пользователя.
- `boolean isAccountNonLocked()` - Указывает, заблокирован или разблокирован пользователь.
- `boolean isCredentialsNonExpired()` - Указывает, истек ли срок действия учетных данных пользователя (пароля).
- `boolean isEnabled()` - Указывает, включен или отключен пользователь.
- `static User.UserBuilder withDefaultPasswordEncoder()` - Устарело. Использование этого метода не считается безопасным для производства, но приемлемо для демонстраций и начала работы.
- `static User.UserBuilder withUserDetails(UserDetails userDetails)`
- `String toString()`
- `void eraseCredentials()`
- `static User.UserBuilder withUsername(String username)` - Создает UserBuilder с указанным именем пользователя.

---
Методы, унаследованные от класса [Object](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html): clone, finalize, getClass, notify, notifyAll, wait

---
**Доп. материалы:**
- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot)
- [Implementing User Authentication Using Spring Security and Spring Data](https://medium.com/@barbieri.santiago/implementing-user-authentication-in-java-apis-using-spring-boot-spring-security-and-spring-data-cb9eac2361f6)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web)
- [Implementing Database Authentication and Authorization with Spring Security 6](https://www.geeksforgeeks.org/advance-java/implementing-database-authentication-and-authorization-with-spring-security-6/)
- [GitHub spring-security-examples](https://github.com/gurkanucar/spring-security-examples)
