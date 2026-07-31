### Свойства в Spring

[SpringProperties (обн.)](https://docs.spring.io/spring-framework/reference/appendix.html) – это статический контейнер для свойств, которые управляют определенными низкоуровневыми
аспектами Spring Framework. Пользователи могут конфигурировать эти свойства через системные свойства JVM
или программно с помощью метода SpringProperties.setProperty(String key, String value). Последнее может
быть необходимо, если окружением развертывания запрещены кастомные системные свойства JVM. Как вариант,
эти свойства могут быть сконфигурированы в файле spring.properties в корне classpath – например, развернуты
в JAR-файле приложения.

В следующем списке перечислены все поддерживаемые в настоящее время свойства Spring:
- **spring.beaninfo.ignore** - Дает Spring команду использовать режим Introspector.IGNORE_ALL_BEANININFO
                           при вызове Introspector из JavaBeans.

- **spring.expression.compiler.mode** - Режим, используемый при компиляции выражений для языка выражений Spring.

- **spring.getenv.ignore** - Дает Spring команду игнорировать переменные окружения операционной системы, если
                         свойство Environment из Spring – например, плейсхолдер в конфигурационной
                         строке – нельзя разрешить иначе.

- **spring.index.ignore** - Дает Spring команду игнорировать индекс компонентов, расположенный в
                        META-INF/spring.components.

- **spring.jdbc.getParameterType.ignore** -	Дает Spring команду полностью игнорировать
                                        java.sql.ParameterMetaData.getParameterType.

- **spring.jndi.ignore** - Дает Spring команду игнорировать стандартное JNDI-окружение в качестве оптимизации
                       в тех случаях, если ничего не будет найдено по таким альтернативным поисковым запросам
                       через JNDI, чтобы избежать повторного выделения ресурсов на JNDI-поиск.

- **spring.objenesis.ignore** - Дает Spring команду игнорировать Objenesis, даже не пытаясь его использовать.

- **spring.test.constructor.autowire.mode** - Режим автоматического обнаружения и связывания тестового конструктора
                                          по умолчанию, который будет использоваться, если аннотация
                                          @TestConstructor отсутствует в тестовом классе.

- **spring.test.context.cache.maxSize** - Максимальный размер кэша контекста в фреймворке Spring TestContext.

- **spring.test.enclosing.configuration** - Режим наследования вложенной конфигурации по умолчанию, используемый,
                                        если тестовый класс не помечен аннотацией @NestedTestConfiguration.
