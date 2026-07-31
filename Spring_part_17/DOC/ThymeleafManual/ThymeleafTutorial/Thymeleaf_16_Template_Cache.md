- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#more-on-configuration)
- [Thymeleaf 3.1.5.RELEASE API](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/index.html)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Подробнее о конфигурации

Thymeleaf работает благодаря набору парсеров для разметки и текста — который анализирует шаблоны в последовательности
(открытый тег, текст, тег закрытия, комментарий и т. д.). И ряд процессоров по одному для каждого типа поведения,
которые применяются и изменяют последовательность событий синтаксического анализа шаблона, чтобы создать ожидаемые
результаты, объединив исходный шаблон с нашими данными.

Он также включает в себя — по умолчанию — кеш, в котором хранятся проанализированные шаблоны; последовательность
событий, возникающих в результате чтения и анализа файлов шаблонов перед их обработкой. Это особенно полезно при
работе в веб-приложении и основывается на следующих концепциях:
- Ввод / вывод почти всегда является самой медленной частью любого приложения;
- Клонирование существующей последовательности событий в памяти всегда намного быстрее, чем чтение файла шаблона, его
  разбор и создание для него новой последовательности событий;
- Веб-приложения обычно имеют всего несколько десятков шаблонов;
- Файлы шаблонов имеют малый и средний размер, и они не изменяются во время работы приложения;

Все это приводит к идее о том, что кэширование наиболее используемых шаблонов в веб-приложении возможно без потери
больших объемов памяти, а также, что это сэкономит много времени, которое будет потрачено на операции ввода-вывода
на небольшом наборе файлов которые, по сути, никогда не меняются.

**И как мы можем контролировать этот кеш?**
- **Во-первых**, мы узнали, что можем включить или отключить кэш в Resolver Template, даже действуя только на определенных шаблонах:

```java
/* По умолчанию true */
templateResolver.setCacheable(false);
templateResolver.getCacheablePatternSpec().addPattern("/users/*");
```

- **Во-вторых**, мы могли бы изменить конфигурацию, установив собственный [объект Cache Manager](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/cache/ICacheManager.html), который может быть экземпляром стандартной реализации [CacheManager](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/cache/StandardCacheManager.html) по умолчанию:

```java
/* По умолчанию 200 */
StandardCacheManager cacheManager = new StandardCacheManager();
cacheManager.setTemplateCacheMaxSize(100);
...
templateEngine.setCacheManager(cacheManager);
```

Обратитесь к javadoc API [StandardCacheManager](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/org/thymeleaf/cache/StandardCacheManager.html) за дополнительной информацией о настройке кэшей.

Записи могут быть удалены вручную из кэша шаблонов:

```java
/* Полностью очистить кеш */
templateEngine.clearTemplateCache();

/* Очистить определенный шаблон из кеша */
templateEngine.clearTemplateCacheFor("/users/userList");
```

[См. далее →](../ThymeleafTutorial/Thymeleaf_17_Separated_Template_Logic.md)
