Док. Spring (ENG):
- [annotation-activeprofiles.html](https://docs.spring.io/spring-framework/reference/testing/annotations/integration-spring/annotation-activeprofiles.html)
- [ActiveProfiles.html](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ActiveProfiles.html)

---
### Annotation Interface ActiveProfiles

Пакет: **org.springframework.test.context**

```Java
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
public @interface ActiveProfiles
```

ActiveProfiles — это аннотация уровня класса, которая используется для объявления того, какие bean-ы определенные
конкретным профилем следует использовать при загрузке ApplicationContext для тестовых классов.

Данную аннотацию можно использовать в качестве метааннотации для создания пользовательских аннотаций.

Начиная с Spring Framework 5.3, эта аннотация по умолчанию будет унаследована от включающего тестового класса.

Смотрите также:

- SmartContextLoader;
- MergedContextConfiguration;
- ContextConfiguration;
- ActiveProfilesResolver;
- ApplicationContext Profile;

Список дополнительных элементов:

---
boolean inheritProfiles - Должны ли быть унаследованы определенные профилями bean-компонентов
                          из суперклассов и включающих классов. По умолчанию - true.

Определяет, должны ли быть унаследованы профили bean-компонентов из суперклассов и включающих классов.

Значение по умолчанию — true, это означает, что тестовый класс будет наследовать профили компонента,
определенные тестовым суперклассом или включающим классом. В частности, профили компонента для тестового
класса будут добавлены к списку профилей компонента, определенных тестовым суперклассом или охватывающим
классом.

Таким образом, подклассы и вложенные классы имеют возможность расширения списка профилей компонентов.

Если для inheritProfiles установлено значение false, профили компонента для тестового класса будут
дублировать и эффективно заменять любые профили компонента, определенные суперклассом или включающим
классом.

В следующем примере ApplicationContext для BaseTest будет загружен с использованием только «базового»
профиля компонента; Поэтому bean-компоненты, определенные в «расширенном» профиле, не будут загружены.
Напротив, ApplicationContext для ExtendedTest будет загружен с использованием «базового» и «расширенного»
профилей компонента:

```Java
 @ActiveProfiles("base")
 @ContextConfiguration
 public class BaseTest {
     // ...
 }

 @ActiveProfiles("extended")
 @ContextConfiguration
 public class ExtendedTest extends BaseTest {
     // ...
 }
```

---
**!!! Примечание !!!** 

@ActiveProfiles можно использовать при загрузке ApplicationContext из расположений ресурсов на основе пути или аннотированных классов.

---
Смотрите также:

- ContextConfiguration.locations();
- ContextConfiguration.classes();
- ContextConfiguration.inheritLocations();

из

https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/context/ContextConfiguration.html

---
String[] profiles - Профили, которые необходимо активировать.

Где, @AliasFor("value")
     String[] profiles

     Профили компонента, которые необходимо активировать.
     Этот атрибут нельзя использовать вместе с value(), но его можно использовать вместо value().
     По умолчанию: {}

---
Class<? extends ActiveProfilesResolver> resolver - Тип ActiveProfilesResolver, используемый для программного
                                                   разрешения профилей определения активного компонента.

     По умолчанию: org.springframework.test.context.ActiveProfilesResolver.class

---
String[] value - Псевдоним для profiles().

Где, @AliasFor("profiles")
     String[] value

     Этот атрибут нельзя использовать вместе с Profiles(), но его можно использовать вместо Profiles().
     По умолчанию: {}
