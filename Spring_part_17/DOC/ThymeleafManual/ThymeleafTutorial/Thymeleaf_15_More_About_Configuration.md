- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#more-on-configuration)
- [Thymeleaf 3.1.5.RELEASE API](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/index.html)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Подробнее о конфигурации

---
#### 15.1 Resolver шаблонов

Для нашего виртуального бакалейного магазина Thymes мы выбрали реализацию [ITemplateResolver](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/templateresolver/ITemplateResolver.html) под названием
[ServletContextTemplateResolver](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.0.BETA03/org/thymeleaf/templateresolver/ServletContextTemplateResolver.html), которая позволила получить шаблоны в качестве ресурсов из контекста сервлета.

Помимо предоставления возможности создавать собственный шаблонный резольвер, реализуя ITemplateResolver, Thymeleaf включает в себя четыре реализации из коробки:
- [ClassLoaderTemplateResolver](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/templateresolver/ClassLoaderTemplateResolver.html), который находит шаблоны как ресурсы загрузчика классов,
  например:

```java
return Thread.currentThread().getContextClassLoader().getResourceAsStream(template);
```

- [FileTemplateResolver](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/templateresolver/FileTemplateResolver.html  ), который находит шаблоны в виде файлов из файловой системы,
  например:

```java
return new FileInputStream(new File(template));
```

- [UrlTemplateResolver](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/templateresolver/UrlTemplateResolver.html), который находит шаблоны как URL-адреса (даже нелокальные), например:

```java
return (new URL(template)).openStream();
```

- [StringTemplateResolver](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/templateresolver/StringTemplateResolver.html), который находит шаблоны напрямую, поскольку String указывается
  как шаблон (или имя шаблона, которое в этом случае, очевидно, намного больше, чем простое имя):

```java
return new StringReader(templateName);
```

Все предустановленные реализации [ITemplateResolver](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/templateresolver/ITemplateResolver.html) позволяют использовать тот же набор параметров конфигурации, которые включают:

- Префикс и суффикс (как уже было видно):

```java
templateResolver.setPrefix("/WEB-INF/templates/");
templateResolver.setSuffix(".html");
```

- Шаблонные псевдонимы, позволяющие использовать имена шаблонов, которые не соответствуют именам файлов. Если существуют суффикс/префикс и псевдоним, псевдоним будет применяться до префикса/суффикса:

```java
templateResolver.addTemplateAlias("adminHome","profiles/admin/home");
templateResolver.setTemplateAliases(aliasesMap);
```

- Кодировка, применяемая при чтении шаблонов:

```java
templateResolver.setEncoding("UTF-8");
```

- Используемый режим шаблона:

```java
// Default is HTML
templateResolver.setTemplateMode("XML");
```

- Режим по умолчанию для кэша шаблонов и pattern-ов для определения того, являются ли конкретные шаблоны кэшируемыми или нет:

```java
// Default is true
templateResolver.setCacheable(false);
templateResolver.getCacheablePatternSpec().addPattern("/users/*");
```

- TTL в миллисекундах для пропарсенных записей кэша шаблонов, созданных в этом resolver шаблона. Если не задано, единственный способ удалить запись из кеша будет LRU (максимальный размер кеша превышен, и запись будет самой
  старой):

```java
// По умолчанию нет TTL (только LRU удаляет записи)
templateResolver.setCacheTTLMs(60000L);
```

Пакеты интеграции Thymeleaf + Spring предлагают реализацию [SpringResourceTemplateResolver](https://www.thymeleaf.org/apidocs/thymeleaf-spring5/3.0.3.M1/org/thymeleaf/spring5/templateresolver/SpringResourceTemplateResolver.html), которая использует всю
инфраструктуру Spring для доступа и чтения ресурсов в приложениях и которая является рекомендуемой реализацией в
приложениях с поддержкой Spring.

---
**Цепочка resolvers шаблонов**

Кроме того, механизм шаблонов может указывать несколько искателей шаблонов, и в этом случае порядок может быть
установлен между ними для поиска шаблона, так что, если первый не может найти шаблон, запрашивается второй и
т. д.:

```java
ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
classLoaderTemplateResolver.setOrder(Integer.valueOf(1));

ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver(servletContext);
servletContextTemplateResolver.setOrder(Integer.valueOf(2));

templateEngine.addTemplateResolver(classLoaderTemplateResolver);
templateEngine.addTemplateResolver(servletContextTemplateResolver);
```

Когда применяется несколько шаблонов-резольверов, рекомендуется указывать pattern-ы для каждого распознавателя шаблонов,
чтобы Thymeleaf мог быстро отменить эти шаблонные resolver-ы, которые не предназначены для поиска шаблона, повышая
производительность. Это не требование, а рекомендация:

```java
ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
classLoaderTemplateResolver.setOrder(Integer.valueOf(1));

/* У этого загрузчика классов даже не будут запрошены классы, не соответствующие этим паттернам */
classLoaderTemplateResolver.getResolvablePatternSpec().addPattern("/layout/*.html");
classLoaderTemplateResolver.getResolvablePatternSpec().addPattern("/menu/*.html");

ServletContextTemplateResolver servletContextTemplateResolver = new ServletContextTemplateResolver(servletContext);
servletContextTemplateResolver.setOrder(Integer.valueOf(2));
```

Если эти resolvable шаблоны не указаны, мы будем полагаться на конкретные возможности каждой из созданных нами
ITemplateResolver реализаций. Обратите внимание, что не все реализации могут определять существование шаблона перед
его resolving и поэтому всегда могут рассматривать шаблон как resolvable и прерывать цепочку поиска (не позволяя
другим resolvers проверять один и тот же шаблон).

Все реализации ITemplateResolver, входящие в состав ядра Thymeleaf, включают механизм, который позволит нам заставить
resolvers действительно проверить, существует ли ресурс, прежде чем считать его найденным. Это флаг checkExistence,
который работает как:

```java
ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
classLoaderTemplateResolver.setOrder(Integer.valueOf(1));
classLoaderTempalteResolver.setCheckExistence(true);
```

Этот флаг checkExistence заставляет resolver выполнять реальную проверку существования ресурса во время фазы поиска
(и пусть следующий поиск осуществится в цепочке, если текущая проверка вернет false). Хотя это может показаться
хорошим решением, в большинстве случаев это будет означать двойной доступ к самому ресурсу. Один раз для проверки
существования, второй раз для его чтения. Это может быть проблемой производительности в некоторых сценариях, например,
«удаленные» ресурсы шаблонов на основе URL-адресов — потенциальная проблема с производительностью, которая в любом
случае может быть в значительной степени смягчена использованием кеша шаблона (в этом случае шаблоны будут искаться
только при первом доступе к ним).

---
#### 15.2 Resolvers сообщений

Мы явно не указали реализацию Message Resolver для нашего приложения Grocery, и, как было объяснено ранее, это означало,
что используемой реализацией был объект [StandardMessageResolver](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/messageresolver/StandardMessageResolver.html).

StandardMessageResolver — это стандартная реализация интерфейса [IMessageResolver](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/messageresolver/IMessageResolver.html), но мы могли бы создать наш собственный, если бы захотели, адаптированный к конкретным потребностям нашего приложения. Пакеты интеграции Thymeleaf + Spring предлагают по умолчанию реализацию IMessageResolver, которая использует стандартный метод Spring для получения внешних сообщений, используя компоненты MessageSource, объявленные в контексте Spring Application Context.

---
**Стандартный Resolver сообщений**

Итак, как выглядит [StandardMessageResolver](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/messageresolver/StandardMessageResolver.html) сообщений, запрашиваемых по определенному шаблону?

Если имя шаблона «home», и оно находится в [/WEB-INF/templates/home.html](https://github.com/thymeleaf/thymeleafexamples-gtvg/blob/3.1-master-jakarta/src/main/webapp/WEB-INF/templates/home.html), а запрошенный язык — *gl_ES*, тогда этот распознаватель будет искать сообщения [в следующих файлах в следующем порядке](https://github.com/thymeleaf/thymeleafexamples-gtvg/tree/3.1-master-jakarta/src/main/webapp/WEB-INF/templates):

```
    /WEB-INF/templates/home_gl_ES.properties
    /WEB-INF/templates/home_gl.properties
    /WEB-INF/templates/home.properties
```

Обратитесь [к документации JavaDoc класса StandardMessageResolver](https://javadoc.io/doc/org.thymeleaf/thymeleaf/3.1.0.M2/org/thymeleaf/messageresolver/StandardMessageResolver.html) для более подробной информации о том, как работает полный механизм поиска сообщений.

---
**Настройка искателя сообщений**

Что делать, если мы хотим добавить средство преобразования сообщений (или больше) в шаблонный движок?

Это легко:

```java
/* Для настройки одного */
templateEngine.setMessageResolver(messageResolver);

/* Для настройки более одного */
templateEngine.addMessageResolver(messageResolver);
```

Почему мы хотим иметь более одного искателя сообщений?

По той же причине, что и искатель шаблонов: упорядочиватели сообщений упорядочены, и если первый не может найти
конкретное сообщение, будет запрошен второй, затем третий и т. д.

---
#### 15.3 Услуги по преобразованию

Служба преобразования, которая позволяет нам выполнять операции преобразования и форматирования данных с помощью
синтаксиса с двойной скобкой `${{...}}`, на самом деле является элементом стандартного диалекта, а не самого механизма
шаблонов Thymeleaf.

Таким образом, способ его настройки заключается в настройке пользовательской реализации интерфейса [IStandardConversionService](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/standard/expression/IStandardConversionService.html) 
непосредственно в экземпляре [StandardDialect](https://javadoc.io/static/org.thymeleaf/thymeleaf/3.1.5.RELEASE/org/thymeleaf/standard/StandardDialect.html), который настраивается в механизм шаблонов.

Подробнее:

```java
IStandardConversionService customConversionService = ...

StandardDialect dialect = new StandardDialect();
dialect.setConversionService(customConversionService);

templateEngine.setDialect(dialect);
```

Обратите внимание, что пакеты thymeleaf-spring3 и thymeleaf-spring4 содержат SpringStandardDialect, и этот диалект уже
предварительно настроен с внедрением IStandardConversionService, который интегрирует собственную инфраструктуру службы
преобразования Spring в Thymeleaf.

---
#### 15.4 Logging/Логирование

Thymeleaf уделяет большое внимание регистрации событий и всегда пытается предоставить максимальный объем полезной
информации через свой интерфейс ведения журнала. Используемая [библиотека протоколирования — slf4j](https://www.slf4j.org/), которая на самом
деле выступает в качестве моста для любой реализации протоколирования, которую мы могли бы использовать в нашем
приложении (например, log4j).

В классах Thymeleaf будут записываться данные TRACE, DEBUG и INFO, в зависимости от уровня детализации, который мы
желаем, и, кроме общего ведения журнала, он будет использовать три специальных регистратора, связанных с классом
TemplateEngine, которые мы можем настроить отдельно для разных целей:

- org.thymeleaf.TemplateEngine.CONFIG - выведет подробную конфигурацию библиотеки во время инициализации;
- org.thymeleaf.TemplateEngine.TIMER - выведет информацию о времени, затраченном на обработку каждого шаблона (полезно для бенчмаркинга!)
- org.thymeleaf.TemplateEngine.cache - является префиксом для набора регистраторов, которые выводят конкретную информацию о кэшах. Хотя имена кэш-регистраторов настраиваются пользователем и, следовательно, могут меняться, по умолчанию они:

```java
org.thymeleaf.TemplateEngine.cache.TEMPLATE_CACHE
        org.thymeleaf.TemplateEngine.cache.EXPRESSION_CACHE
```

Пример конфигурации для инфраструктуры регистрации Thymeleaf, использующей log4j, может быть:

```
log4j.logger.org.thymeleaf=DEBUG
log4j.logger.org.thymeleaf.TemplateEngine.CONFIG=TRACE
log4j.logger.org.thymeleaf.TemplateEngine.TIMER=TRACE
log4j.logger.org.thymeleaf.TemplateEngine.cache.TEMPLATE_CACHE=TRACE
```

[См. далее →](../ThymeleafTutorial/Thymeleaf_16_Template_Cache.md)
