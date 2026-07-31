### Использование JdbcTemplate

Классы **JdbcTemplate и NamedParameterJdbcTemplate из Spring являются автоконфигурируемыми**, и вы можете **привязать
их через аннотацию @Autowire непосредственно к своим собственным bean-ам**, как показано в следующем примере:

on Java:

```Java
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.stereotype.Component;
    
    @Component
    public class MyBean {

        private final JdbcTemplate jdbcTemplate;

        public MyBean(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        public void doSomething() {
            this.jdbcTemplate ...
        }
    }
```

on Kotlin:
```Kotlin
    import org.springframework.jdbc.core.JdbcTemplate
    import org.springframework.stereotype.Component
    
    @Component
    class MyBean(private val jdbcTemplate: JdbcTemplate) {
        fun doSomething() {
            jdbcTemplate.execute("delete from customer")
        }
    }
```

Вы можете **настроить некоторые свойства шаблона с помощью свойств** `spring.jdbc.template.*`, как показано в следующем
примере:

in Properties:
```
    spring.jdbc.template.max-rows=500
```

in Yaml:
```YAML
spring:
  jdbc:
    template:
      max-rows: 500
```

**NamedParameterJdbcTemplate повторно использует "за кулисами" один и тот же экземпляр JdbcTemplate**. Если определено
более одного JdbcTemplate, а основного кандидата нет, NamedParameterJdbcTemplate не будет автоматически
сконфигурирован.
