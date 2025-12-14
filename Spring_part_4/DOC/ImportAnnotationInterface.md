### Annotation Interface Import

@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Import

Указывает один или несколько классов компонентов для импорта — обычно аннотированных @Configuration
классов.

Предоставляет функциональность, эквивалентную элементу <import/> в Spring XML. Позволяет импортировать
@Configuration классы ImportSelector и ImportBeanDefinitionRegistrar реализации, а также классы обычных
компонентов аналогично AnnotationConfigApplicationContext.register(java.lang.Class<?>...)
(начиная с версии 4.2).

@Bean определениям, объявленным в импортированных @Configuration классах, следует обращаться с помощью
@Autowired внедрения. Либо сам компонент может быть автоматически подключен, либо экземпляр класса
конфигурации, объявляющий компонент, может быть автоматически подключен. Последний подход позволяет
осуществлять явную, удобную для IDE навигацию между @Configuration методами класса.

Может быть объявлен на уровне класса или в виде мета-аннотации.

Если необходимо импортировать XML или другие non-@Configuration bean definition ресурсы, не относящиеся
к компонентам, лучше использовать @ImportResource вместо этой аннотации.

---
См. также:

- Configuration - https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Configuration.html
- ImportSelector - https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ImportSelector.html
- ImportBeanDefinitionRegistrar - https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ImportBeanDefinitionRegistrar.html
- ImportResource - https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/ImportResource.html

---
Исходный текст: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/annotation/Import.html
