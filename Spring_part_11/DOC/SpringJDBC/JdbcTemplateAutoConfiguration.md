### Class [JdbcTemplateAutoConfiguration](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/jdbc/autoconfigure/JdbcTemplateAutoConfiguration.html)

Auto-configuration для JdbcTemplate и NamedParameterJdbcTemplate.

---
Пакет: [org.springframework.boot.autoconfigure.jdbc](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/jdbc/package-summary.html)

---
         java.lang.Object
                  org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration

---
@AutoConfiguration(after=DataSourceAutoConfiguration.class)
@ConditionalOnClass({javax.sql.DataSource.class,org.springframework.jdbc.core.JdbcTemplate.class})
@ConditionalOnSingleCandidate(javax.sql.DataSource.class)
@EnableConfigurationProperties(JdbcProperties.class)
@Import({DatabaseInitializationDependencyConfigurer.class,
         org.springframework.boot.autoconfigure.jdbc.JdbcTemplateConfiguration.class,
         org.springframework.boot.autoconfigure.jdbc.NamedParameterJdbcTemplateConfiguration.class})
public class JdbcTemplateAutoConfiguration extends Object

---
[См. оф. док (ENG)](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/jdbc/autoconfigure/JdbcTemplateAutoConfiguration.html)
