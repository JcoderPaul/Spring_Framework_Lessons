- [См. исходник (ENG)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetails.html)

---
### Interface UserDetails

**Пакет:** [org.springframework.security.core.userdetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/package-summary.html)

**Все супер-интерфейсы:** [Serializable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/Serializable.html)

**Все под-интерфейсы:** [LdapUserDetails](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/LdapUserDetails.html)

**Реализующие классы:** 
- [InetOrgPerson](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/InetOrgPerson.html),
- [LdapUserDetailsImpl](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/LdapUserDetailsImpl.html),
- [Person](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/ldap/userdetails/Person.html),
- [User](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/User.html)

```
  public interface UserDetails extends Serializable
```

Предоставляет основную информацию о пользователе.

Реализации не используются непосредственно Spring Security в целях безопасности. Они просто хранят информацию о
пользователе, которая позже инкапсулируется в объекты аутентификации. Это позволяет хранить информацию пользователя,
не связанную с безопасностью (например, адреса электронной почты, номера телефонов и т. д.), в удобном месте.

Конкретные реализации должны уделять особое внимание обеспечению соблюдения ненулевого контракта, подробно описанного
для каждого метода. См. «User» для ознакомления с эталонной реализацией (которую вы, возможно, захотите расширить или
использовать в своем коде).

- [Class User](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/User.html)

---
**См. так же пример:** 
- [Serialized Form of User](https://docs.spring.io/spring-security/site/docs/current/api/serialized-form.html#org.springframework.security.core.userdetails.User)

---
#### Методы

- `Collection<? extends GrantedAuthority> getAuthorities()` - Возвращает полномочия, предоставленные пользователю.
- `String getPassword()` - Возвращает пароль, используемый для аутентификации пользователя.
- `String getUsername()` - Возвращает имя пользователя, используемое для аутентификации пользователя.
- `boolean isAccountNonExpired()` - Указывает, истек ли срок действия учетной записи пользователя.
- `boolean isAccountNonLocked()` - Указывает, заблокирован или разблокирован пользователь.
- `boolean isCredentialsNonExpired()` - Указывает, истек ли срок действия учетных данных пользователя (пароля).
- `boolean isEnabled()` - Указывает, включен или отключен пользователь.

---
**Доп. материал:**
- [UserDetails](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details.html)
- [Spring Security - UserDetailsService and UserDetails with Example](https://www.geeksforgeeks.org/advance-java/spring-security-userdetailsservice-and-userdetails-with-example/)
- [UserDetailsService in Spring Security](https://livebook.manning.com/wiki/categories/java/userdetailsservice)
- [Java Examples for org.springframework.security.core.userdetails.UserDetailsService](https://www.javatips.net/api/org.springframework.security.core.userdetails.userdetailsservice)
- [Spring Security UserDetailsService](https://www.concretepage.com/spring-5/spring-security-userdetailsservice)
- [Spring Security: Configuring UserDetailsService (Part 1)](https://dev.to/ramachandrapetla/spring-security-authentication-authorization-part-1-1n48)
- [Spring Custom UserDetailsService Example](https://javapointers.com/spring/spring-security/spring-custom-userdetailsservice-example/)
- [Spring Security: UserDetailsService](https://www.javaguides.net/2024/04/spring-security-userdetailsservice.html)
