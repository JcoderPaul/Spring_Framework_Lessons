- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#standard-expression-syntax)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Стандарт синтаксиса Выражений/Standard Expression Syntax

Мы сделаем небольшой перерыв в развитии нашего виртуального магазина бакалейных товаров, чтобы узнать об одной из
наиболее важных частей ['Стандартного диалекта' Thymeleaf - 'Стандарт синтаксиса выражений' Thymeleaf](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html).

Мы уже видели два типа допустимых значений атрибутов, выраженные в этом синтаксисе - это сообщения и переменные:

```html
    <p th:utext="#{home.welcome}">Welcome to our grocery store!</p>

    <p>Today is: <span th:text="${today}">13 february 2011</span></p>
```

Но существует значительно больше выражений.

---
#### Сделаем краткий обзор Standard Expression:

- **Простые выражения:**
    - Переменная: `${...}`
    - Выбранная переменная: `*{...}`
    - Сообщение: `#{...}`
    - Ссылка URL: `@{...}`
    - Фрагмент: `~{...}`

- **Литералы/Literals:**
    - Текст: `'one text', 'Another one!',...`
    - Число: `0, 34, 3.0, 12.3,...`
    - Boolean: `true`, `false`
    - Null: `null`
    - Токены: `one, sometext, main,...`

- **Текст:**
    - Соединение строк: `+`
    - Подстроки: `|The name is ${name}|`

- **Арифметика:**
    - Binary: `+, -, *, /, %`
    - Минус (unary operator): `-`

- **Boolean:**
    - Binary: `and`, `or`
    - Boolean отрицание (unary operator): `!`, `not`

- **Сравнение и равенство:**
    - Сравнение: `>, <, >=, <= (gt, lt, ge, le)`
    - Равенство: `==, != (eq, ne)`

- **Условные:**
    - If-then: `(if)? (then)`
    - If-then-else: `(if)? (then): (else)`

- **Default:** `(value) ?: (defaultvalue)`

- **Специальные токены:**
    - No-Operation: `_`

Выражения могут комбинироваться и вкладываться:

```html
    'User is of type ' + (${user.isAdmin()} ? 'Administrator' : (${user.type} ?: 'Unknown'))
```

---
### 4.1 Сообщения/Messages

Как мы знаем, выражение сообщения `#{...}` позволяет нам связывать:

```html
    <p th:utext="#{home.welcome}">Welcome to our grocery store!</p>
```

и :

```html
    home.welcome=¡Bienvenido a nuestra tienda de comestibles!
```

Но есть один аспект, о котором мы еще не подумали. Что произойдет, если текст сообщения не будет полностью статическим?
Что, если, например, наше приложение знает, какой пользователь посещает сайт, и мы хотели бы приветствовать пользователя
по имени? 

Например, так:

```html
    <p>¡Bienvenido a nuestra tienda de comestibles, John Apricot!</p>
```

Это означает, что нам необходимо добавить параметр в наше сообщение. Вот так:

```html
    home.welcome=¡Bienvenido a nuestra tienda de comestibles, {0}!
```

Параметр определяется относительно стандартного синтаксиса [java.text.MessageFormat](https://docs.oracle.com/javase/8/docs/api/java/text/MessageFormat.html), это означает, что вы можете
форматировать числа и даты.

Чтобы указать значение для нашего параметра из атрибутов HTTP сеанса, называемого пользователем, мы напишем:

```html
    <p th:utext="#{home.welcome(${session.user.name})}">
      Welcome to our grocery store, Sebastian Pepper!
    </p>
```

Несколько параметров можно определить, разделив запятыми. Так же сами ключи сообщения могут прийти из переменной:

```html
    <p th:utext="#{${welcomeMsgKey}(${session.user.name})}">
      Welcome to our grocery store, Sebastian Pepper!
    </p>
```

---
### 4.2 Переменные/Variables

Мы уже упоминали, что выражения `${...}` на самом деле являются выражениями [OGNL](https://en.wikipedia.org/wiki/OGNL) ([Object-Graph Navigation Language](https://ognl.orphan.software/language-guide.html#syntax)), выполненными на Map переменных, содержащихся в контексте. Для получения подробной информации о синтаксисе и функциях
OGNL нужно прочитать [«Руководство по языку OGNL»](https://commons.apache.org/dormant/commons-ognl/language-guide.html)

В Spring приложениях с поддержкой MVC OGNL будет заменен на [SpringEL](https://docs.spring.io/spring-framework/docs/4.3.10.RELEASE/spring-framework-reference/html/expressions.html), но его синтаксис очень похож на синтаксис OGNL (фактически, точно такой же для большинства распространенных случаев).

Из синтаксиса OGNL мы знаем, что выражение:

```html
    <p>Today is: <span th:text="${today}">13 february 2011</span>.</p>
```

эквивалентно:

```java
    ctx.getVariable("today");
```

Но OGNL позволяет создавать более мощные выражения, как это:

```html
    <p th:utext="#{home.welcome(${session.user.name})}">
      Welcome to our grocery store, Sebastian Pepper!
    </p>
```

… получаем имя пользователя:

```java
    ((User) ctx.getVariable("session").get("user")).getName();
```

Но getter — это лишь одна из особенностей OGNL. 

Посмотрим еще:

```
    /* Доступ к свойствам с использованием точки (.). Эквивалент вызывающим getter свойств */
    ${person.father.name}
    
    /*
    Доступ к свойству может быть так же через скобки ([]) и написание
    имени свойства как переменной или между простыми кавычками.
    */
    ${person['father']['name']}
    
    /* Если объект является Map, оба синтаксиса точки и скобки будет эквивалентен выполнению метода get(...) */
    ${countriesByCode.ES}
    ${personsByName['Stephen Zucchini'].age}
    
    /* Доступ к массиву или коллекции так же выполняется через скобки, с написанием индекса без скобок */
    ${personsArray[0].name}
    
    /* Могут быть вызваны методы даже с аргументами */
    ${person.createCompleteName()}
    ${person.createCompleteNameWithSeparator('-')}
```

---
#### Expression Basic Objects

При выполнении выражения OGNL с переменными контекста некоторые объекты становятся доступными для большей гибкости.
На эти объекты можно ссылаться (по стандарту OGNL), начиная с символа #:

- `#ctx`: контекст.
- `#vars`: переменные контекста.
- `#locale`: локаль контекста.
- `#request`: (только в Web Contexts) объект HttpServletRequest.
- `#response`: (только в Web Contexts) объект HttpServletResponse.
- `#session`: (только в Web Contexts) объект HttpSession.
- `#servletContext`: (только в Web Contexts) объект ServletContext.

Так же мы можем делать следующее:

```html
    <span th:text="${#locale.country}">US</span>
```

Полные ссылки на эти объекты перечислены [в Прил. A.](../ThymeleafTutorial/Thymeleaf_18_Appendix_A.md)

---
#### Утилиты выражений

Помимо этих основных объектов, Thymeleaf предлагает нам набор полезных объектов, которые помогут нам выполнять общие
задачи в наших выражениях.

- `#execInfo`: информация о текущем шаблоне.
- `#messages`: методы для получения сообщений внутри выражений, так же, как они будут получены с использованием синтаксиса #{...}.
- `#uris`: методы для экранирования частей URL/URI.
- `#conversions`: методы для выполнения настроек службы преобразования (если присутствуют).
- `#dates`: методы для java.util.Date объектов: форматирование, извлечение компонентов и тп.
- `#calendars`: аналогично #dates, но для java.util.Calendar объектов.
- `#numbers`: методы для форматирования числовых объектов.
- `#strings`: методы для String объекто: contains, startsWith, prepending/appending и тп.
- `#objects`: общие методы для объектов.
- `#bools`: методы для булевых преобразований.
- `#arrays`: методы для массивов.
- `#lists`: методы для List.
- `#sets`: методы для Sets.
- `#maps`: методы для Maps.
- `#aggregates`: методы для аггрегирования массовов или коллекций.
- `#ids`: методы для обработки идентификаторов id, которые могут быть повторены (например, в результате итерации).

См. более подробно [в Приложении B.](../ThymeleafTutorial/Thymeleaf_19_Appendix_B.md)

---
#### Переформатирование даты на странице Home

Теперь мы знаем об этих утилитах объектов и можем использовать их, чтобы изменить способ отображения даты на нашей
домашней странице.

Вместо этого в нашем HomeController:

```java
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
    Calendar cal = Calendar.getInstance();
    
    WebContext ctx = new WebContext(request, servletContext, request.getLocale());
    ctx.setVariable("today", dateFormat.format(cal.getTime()));
    
    templateEngine.process("home", ctx, response.getWriter());
```

… мы можем сделать следующее:

```java
    WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
    ctx.setVariable("today", Calendar.getInstance());
    
    templateEngine.process("home", ctx, response.getWriter());
```

… и затем выполнить форматирование даты в самом слое представления:

```html
    <p>
      Today is: <span th:text="${#calendars.format(today,'dd MMMM yyyy')}">13 May 2011</span>
    </p>
```

---
### 4.3 Выражения выбора/selections (синтаксис звездочки)

Выражения с переменными могут быть записаны не только через `${...}`, но и как `*{...}`.

Между вариантами существует разница: синтаксис со звездочкой преобразует выражение над выбранным объектом нежели над
всем контекстом. Это значит, что если нет выбранного объекта, доллар и звездочка делают одно и то же.

**Что такое выбранный объект?**

Результат выражения с использованием атрибута - `th:object`. Используем его на странице профиля [userprofile.html](https://github.com/thymeleaf/thymeleafexamples-gtvg/blob/3.1-master-jakarta/src/main/webapp/WEB-INF/templates/userprofile.html):

```html
    <div th:object="${session.user}">
      <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
      <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
      <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
    </div>
```

Что эквивалентно:

```html
    <div>
      <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p>
      <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
      <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p>
    </div>
```

Конечно, доллар и звездочка могут смешиваться:

```html
    <div th:object="${session.user}">
      <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
      <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
      <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
    </div>
```

Когда выделенный объект находится на месте, выбранный объект будет так же доступен с долларом, как и `#object` переменная:

```html
    <div th:object="${session.user}">
      <p>Name: <span th:text="${#object.firstName}">Sebastian</span>.</p>
      <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p>
      <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
    </div>
```

Как сказано выше, если нет выбранного объекта, доллар и звездочка эквиваленты.

```java
    <div>
      <p>Name: <span th:text="*{session.user.name}">Sebastian</span>.</p>
      <p>Surname: <span th:text="*{session.user.surname}">Pepper</span>.</p>
      <p>Nationality: <span th:text="*{session.user.nationality}">Saturn</span>.</p>
    </div>
```

---
### 4.4 Link URL

Из-за своей важности URL-адреса являются первоклассными «гражданами» в шаблонах веб-приложений, а Стандартный диалект
Thymeleaf имеет для них специальный знак - `@` синтаксис: `@{...}`

Существуют разные типы URL:

- Абсолютные URLs: `www.thymeleaf.org`
- Относительные URLs:
    - Относительные на странице: `user/login.html`
    - Относительные контекста: `/itemdetails?id=3` (контекстное имя будет добавлено на сервере)
    - Относительные сервера: `~/billing/processInvoice` (позволяют вызывать URLs в другом контексте (= application) на том же сервере)
    - Относительные протокола: `//code.jquery.com/jquery-2.0.3.min.js`

Реальная обработка этих выражений и их преобразование в URL-адреса, которые будут выводиться, выполняются с помощью
реализации интерфейса [ILinkBuilder](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.9.RELEASE/org/thymeleaf/linkbuilder/ILinkBuilder.html), кои регистрируются в используемом объекте [ITemplateEngine](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.0.BETA03/org/thymeleaf/ITemplateEngine.html).

По умолчанию одна реализация этого интерфейса представлена классом [StandardLinkBuilder](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.9.RELEASE/org/thymeleaf/linkbuilder/StandardLinkBuilder.html),
которого достаточно, как для автономных (не веб-сайтов), так и для веб-сценариев на основе API-интерфейса Servlet.
Другие сценарии (например, интеграция с веб-фреймворками, не относящимися к ServletAPI), возможно, потребуют
конкретных реализаций интерфейса компоновщика ссылок (link builder interface).

Давайте используем новый синтаксис. Встречайте атрибут - `th:href`:

```html
    <!-- Создаем 'http://localhost:8080/gtvg/order/details?orderId=3' (плюс реврайт) -->
    <a href="details.html"
       th:href="@{http://localhost:8080/gtvg/order/details(orderId=${o.id})}">view</a>
    
    <!-- Создаем '/gtvg/order/details?orderId=3' (плюс реврайт) -->
    <a href="details.html" th:href="@{/order/details(orderId=${o.id})}">view</a>
    
    <!-- Создаем '/gtvg/order/3/details' (плюс реврайт) -->
    <a href="details.html" th:href="@{/order/{orderId}/details(orderId=${o.id})}">view</a>
```

**Несколько заметок:**

- `th:href` - это модифицирующий атрибут: однажды выполнившись, он вычислит URL ссылку и установит значение атрибуту «href» тега <a>.
- Нам разрешено использовать выражения для параметров URL, как вы можете видеть в `orderId=${o.id}`. Также будут автоматически выполняться требуемые операции кодирования URL-параметров.
- Если требуется несколько параметров, они будут разделены запятыми: `@{/order/process(execId=${execId},execType='FAST')}`
- Переменные шаблоны также допускаются в URL-адресах: `@{/order/{orderId}/details(orderId=${orderId})}`
- Относительные URL-адреса, начинающиеся с `/` (например: `/order/details`), будут с автоматически префиксами в виде имени контекста приложения.
- Если файлы cookie не включены или это еще не известно, суффикс `;jsessionid=...` может быть добавлен в относительные URL-адреса, чтобы session была сохранена. Это называется URL Rewriting и Thymeleaf позволяет подключать собственные фильтры перезаписи, используя механизм `response.encodeURL(...)` из API сервлета для каждого URL-адреса.
- Атрибут `th:href` позволяет нам (необязательно) иметь действующий статический атрибут `href` в нашем шаблоне, чтобы наши ссылки на шаблоны оставались навигационными для браузера при открытии напрямую для целей прототипирования.

Как и в случае синтаксиса сообщения `(#{...})`, URL также могут быть результатом выполнения другого выражения:

```html
    <a th:href="@{${url}(orderId=${o.id})}">view</a>
    <a th:href="@{'/details/'+${user.login}(orderId=${o.id})}">view</a>
```

---
#### Меню для нашей домашней страницы

Сейчас мы знаем, как создать ссылки URLs, что насчет добавления небольшого навигационного меню на нашу Home страницу?

```html
    <p>Please select an option</p>
    <ol>
      <li><a href="product/list.html" th:href="@{/product/list}">Product List</a></li>
      <li><a href="order/list.html" th:href="@{/order/list}">Order List</a></li>
      <li><a href="subscribe.html" th:href="@{/subscribe}">Subscribe to our Newsletter</a></li>
      <li><a href="userprofile.html" th:href="@{/userprofile}">See User Profile</a></li>
    </ol>
```

Ссылки URLs относительно корневой директории сервера.

Дополнительный синтаксис может использоваться для создания URL-адресов, основанных на корневом каталоге (вместо
контекстно-зависимых) URL-адресов, чтобы ссылаться на разные контексты на одном сервере. Эти URL-адреса будут
указаны как `@{~/path/to/something}`

---
### 4.5 Фрагменты/Fragments

Фрагментные выражения — это простой способ представить фрагменты разметки и перемещать их между шаблонами. Это позволяет
нам копировать, передавать их другим шаблонам в качестве аргументов и тп.

Наиболее распространенное использование — для вставки фрагмента с использованием `th:insert` или `th:replace` (подробнее об этом в следующем разделе):

```html
    <div th:insert="~{commons :: main}">...
```

Но они могут использоваться везде, как и любая другая переменная:

```html
    <div th:with="frag=~{footer :: #main/text()}">
      <p th:insert="${frag}">
    </div>
```

Позже в этом учебном курсе есть целый раздел, посвященный Template Layout, включая более глубокое объяснение фрагментных выражений.

---
### 4.6 Литералы/Literals

#### Текстовые литералы

Текстовые литералы — это только символьные строки, заданные между одинарными кавычками. Они могут включать любой символ,
но вы должны избегать каких-либо одиночных кавычек внутри них, используя `\'`.

```html
    <p>
      Now you are looking at a <span th:text="'working web application'">template file</span>.
    </p>
```

#### Числовые литералы

Числовые литералы — это просто числа.

```html
    <p>The year is <span th:text="2013">1492</span>.</p>
    <p>In two years, it will be <span th:text="2013 + 2">1494</span>.</p>
```

#### Булевые/Boolean литералы

Булевые литералы — это `true` и `false`.

```html
    <div th:if="${user.isAdmin()} == false"> ...
```

В этом примере `== false` написаны **вне скобок, то о выражении заботится Thymeleaf**. Если бы равенство содержалось **внутри скобок - о нем бы заботился OGNL/SpringEL движок**:

```html
    <div th:if="${user.isAdmin() == false}"> ...
```

#### Литерал null

Литерал null так же может быть использован:

```html
    <div th:if="${variable.something} == null"> ...
```

#### Литеральные токены

Числовые, булевые и null литералы являются частным случаем литеральных токенов.

Эти токены позволяют немного упростить Standard Expressions. Они работают точно так же, как текстовые литералы `'...'`,
но допускают только буквы `A-Z` и `a-z`, цифры `0-9`, скобки `[` и `]`, точки `.`, дефисы `-` и подчеркивания `_`. Никаких
пробелов, никаких запятых и т.п.

Токены не нуждаются в кавычках, окружающих их. Поэтому мы можем сделать:

```html
    <div th:class="content">...</div>
```

вместо:

```html
    <div th:class="'content'">...</div>
```

---
### 4.7 Appending texts

Тексты, независимо от того, являются ли они литералами или результатом обработки переменных или сообщений, могут быть
легко добавлены с помощью оператора `+`:

```html
    <span th:text="'The name of the user is ' + ${user.name}">
```

---
### 4.8 Литералы замены

Литеральные замены позволяют легко форматировать строки, содержащие значения из переменных, без необходимости добавлять
литералы с помощью `'...' + '...'`.

Эти замены должны быть окружены вертикальными черточками `|`, например:

```html
    <span th:text="|Welcome to our application, ${user.name}!|">
```

Что эквивалентно:

```html
    <span th:text="'Welcome to our application, ' + ${user.name} + '!'">
```

Литеральные замены могут быть объединены с другими типами выражений:

```html
    <span th:text="${onevar} + ' ' + |${twovar}, ${threevar}|">
```

Только выражения переменной `/` сообщения `${...}`, `*{...}`, `#{...}` разрешены внутри `|...|` литеральных замен.
Никакие другие литералы `'...'`, boolean/numeric токены, условные выражения и т.п.

---
### 4.9 Арифметические операции

Некоторые арифметические операции так же доступны: `+`, `-`, `*`, `/`, `%`.

```html
    <div th:with="isEven=(${prodStat.count} % 2 == 0)">
```

Обратите внимание, что эти операторы также могут быть применены непосредственно в самих выражениях OGNL (и в этом случае
будет выполняться OGNL вместо механизма Thymeleaf Standard Expression):

```html
    <div th:with="isEven=${prodStat.count % 2 == 0}">
```

Обратите внимание, что для некоторых из этих операторов существуют текстовые псевдонимы: `div` → `/`, `mod` → `%`.

---
### 4.10 Сравнения и равенство

Значения в выражениях можно сравнить с символами `>`, `<`, `>=` и `<=`, `==`, `!=` операторы используются для проверки равенства
(или его отсутствия). Обратите внимание, что XML устанавливает `<` и `>` символы не должны использоваться в значениях
атрибутов, и поэтому они должны быть заменены на `lt` и `gt`

```html
    <div th:if="${prodStat.count} > 1">
    
    <span th:text="'Execution mode is ' + ( (${execMode} == 'dev')? 'Development' : 'Production')">
```

Более простая альтернатива может заключаться в использовании текстовых псевдонимов, существующих для некоторых из этих
операторов: `gt` → `>`, `lt` → `<`, `ge` → `>=`, `le` → `<=`, `not` → `!`, `eq` → `==`, `neq/ne` → `!=`.

---
### 4.11 Условные выражения

Условные выражения предназначены для выполнения только одного из двух выражений в зависимости от результата оценки
условия (которое само является другим выражением).

Давайте посмотрим на фрагмент примера (введение другого модификатора атрибута, `th:class`):

```html
    <tr th:class="${row.even}? 'even' : 'odd'">
      ...
    </tr>
```

Все три части условного выражения `condition`, `then` и `else` сами являются выражениями, что означает, что они могут быть
переменными `${...}`, `*{...}`, messages `#{...}`, URLs `@{...}` или литералами `'...'`.

Условные выражения также могут быть вложены с помощью круглых скобок:

```html
    <tr th:class="${row.even}? (${row.first}? 'first' : 'even') : 'odd'">
      ...
    </tr>
```

Другие выражения также могут быть опущены, и в этом случае возвращается null значение, если условие ложно:

```html
    <tr th:class="${row.even}? 'alt'">
      ...
    </tr>
```

---
### 4.12 По умолчанию/Default выражение (Elvis оператор)

Выражение по умолчанию — это особый вид условного значения без какой-либо части. Это эквивалентно оператору Elvis,
присутствующему на некоторых языках, например Groovy, что позволяет указать два выражения: первый используется, если
он не null, но если это так, то используется второй.

Посмотрим, как это работает на странице нашего профиля:

```html
    <div th:object="${session.user}">
      ...
      <p>Age: <span th:text="*{age}?: '(no age specified)'">27</span>.</p>
    </div>
```

Как вы можете видеть, оператором является `?:`, и мы используем его здесь, чтобы указать значение по умолчанию для имени
(буквальное значение в этом случае), только если результат вычисления `*{age}` равен `null`. Поэтому это эквивалентно:

```html
    <p>Age: <span th:text="*{age != null}? *{age} : '(no age specified)'">27</span>.</p>
```

Как и условные значения, они могут содержать вложенные выражения между круглыми скобками:

```html
    <p>
      Name:
      <span th:text="*{firstName}?: (*{admin}? 'Admin' : #{default.username})">Sebastian</span>
    </p>
```

---
### 4.13 No-Operation токен

Токен No-Operation представлен символом `_`.

Идея этого токена состоит в том, чтобы указать, что желаемый результат для выражения - ничего не делать: делать точно
так, как если бы обработанный атрибут (например, `th:text`) вообще отсутствовал.

Среди других возможностей это позволяет разработчикам использовать текст прототипов в качестве значений по умолчанию.

Например, вместо:

```html
    <span th:text="${user.name} ?: 'no user authenticated'">...</span>
```

… мы можем напрямую использовать `no user authenticated` в качестве текста прототипирования, что приводит к тому, что
код является более кратким и универсальным с точки зрения дизайна:

```html
    <span th:text="${user.name} ?: _">no user authenticated</span>
```

---
### 4.14 Преобразование данных / Форматирование

Thymeleaf определяет синтаксис двойных скобок для переменных `${...}` и выделенных `*{...}` выражений, что позволяет
нам применять преобразование данных с использованием конфигурирования сервиса преобразований.

Базово это выглядит так:

```html
    <td th:text="${{user.lastAccessDate}}">...</td>
```

Вы заметили двойную скобку в `${{...}}`, она инструктирует Thymeleaf передать результат выражения `user.lastAccessDate` в
службу преобразования и попросит выполнить операцию форматирования (преобразование в String) перед возвращением
результата. Предполагая, что `user.lastAccessDate` имеет тип [Calendar](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html), если служба преобразования (реализация
[IStandardConversionService](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.7.RELEASE/org/thymeleaf/standard/expression/IStandardConversionService.html)) была зарегистрирована и содержит действительное преобразование для Calendar -> String, оно будет применяться.

По умолчанию реализация [IStandardConversionService](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.7.RELEASE/org/thymeleaf/standard/expression/IStandardConversionService.html) (класс [StandardConversionService](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.7.RELEASE/org/thymeleaf/standard/expression/StandardConversionService.html)) просто выполняет `.toString()` для
любого объекта. Дополнительные сведения о том, как зарегистрировать пользовательскую реализацию службы преобразования, можно найти в разделе [«Дополнительные сведения о конфигурации»](https://www.thymeleaf.org/doc/tutorials/3.1/extendingthymeleaf.html) и ["More on Configuration"](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#more-on-configuration)

Официальные пакеты интеграции [thymeleaf-spring3](https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring3) и [thymeleaf-spring4](https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf-spring4) прозрачно интегрируют механизм обслуживания преобразования Thymeleaf с собственной инфраструктурой Conversion Service Spring, так что сервисы конвертации и
форматы, объявленные в конфигурации Spring, будут автоматически доступны для выражений `${{...}}` и `*{{...}}`.

---
### 4.15 Препроцессинг

В дополнение ко всем этим функциям для обработки выражений Thymeleaf имеет функцию препроцессорных выражений.

Предварительная обработка — это выполнение выражений, сделанных до выполнения стандартных выражений, что позволяет
модифицировать выражение, которое в конечном итоге будет выполнено.

Предварительно обработанные выражения точно аналогичны нормальным, но появляются в окружении двойного символа
подчеркивания (например, `__${выражение}__`).

Предположим, у нас есть запись i18n Messages_fr.properties, содержащая выражение OGNL, вызывающее статический метод,
специфичный для языка, например:

```
    article.text=@myapp.translator.Translator@translateToFrench({0})
```

… и эквивалент Messages_es.properties:

```
    article.text=@myapp.translator.Translator@translateToSpanish({0})
```

Мы можем создать фрагмент разметки, который выполняет одно выражение или другое в зависимости от локали. Для этого мы
сначала выберем выражение (путем предварительной обработки), а затем запустим Thymeleaf:

```
    <p th:text="${__#{article.text('textVar')}__}">Some text here...</p>
```

Обратите внимание, что шаг предварительной обработки для французского языка будет создавать следующий эквивалент:

```html
    <p th:text="${@myapp.translator.Translator@translateToFrench(textVar)}">Some text here...</p>
```

Строка препроцессора `__` может быть экранирована в атрибутах с помощью `\_\_`.

[Продолжим в следующем разделе →](../ThymeleafTutorial/Thymeleaf_5_Setting_Attr_Val.md) 
