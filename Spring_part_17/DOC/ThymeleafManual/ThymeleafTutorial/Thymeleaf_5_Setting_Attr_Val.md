- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#setting-attribute-values)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Установка значений атрибутов

В этой главе мы объясним, как можем установить (или изменить) значения атрибутов в разметке.

---
#### 5.1 Установка значения любого атрибута

Скажем, наш сайт публикует информационный бюллетень, и мы хотим, чтобы пользователи могли подписаться на него, поэтому
создаем шаблон [/WEB-INF/templates/subscribe.html](https://github.com/thymeleaf/thymeleafexamples-gtvg/blob/3.1-master-jakarta/src/main/webapp/WEB-INF/templates/subscribe.html) с формой:

```html
<form action="subscribe.html">
  <fieldset>
    <input type="text" name="email" />
    <input type="submit" value="Subscribe!" />
  </fieldset>
</form>
```

Как и в случае с Thymeleaf, этот шаблон начинается, скорее, как статический прототип, чем шаблон для веб-приложения:
- Во-первых, атрибут действия в нашей форме статически связывается с самим файлом шаблона, так что нет места для полезной перезаписи URL.
- Во-вторых, атрибут value в кнопке submit позволяет отображать текст на английском языке, но мы хотели бы, чтобы он был интернационализирован.

Тогда используем атрибут `th:attr`, способный изменять значение атрибутов тегов, в которых он установлен:

```html
<form action="subscribe.html" th:attr="action=@{/subscribe}">
  <fieldset>
    <input type="text" name="email" />
    <input type="submit" value="Subscribe!" th:attr="value=#{subscribe.submit}"/>
  </fieldset>
</form>
```

Концепция довольно проста - `th:attr` - принимает выражение, которое выполняет и присваивает атрибуту полученное значение.
Создав соответствующие файлы контроллеров и сообщений, результатом обработки файла будет:

```html
<form action="/gtvg/subscribe">
  <fieldset>
    <input type="text" name="email" />
    <input type="submit" value="¡Suscríbe!"/>
  </fieldset>
</form>
```

Помимо новых значений атрибутов вы также можете увидеть, что имя контекста приложения автоматически было добавлено в
базу URL в `/gtvg/subscribe`, как описано в предыдущей главе - ["Стандарт синтаксиса Выражений/Standard Expression Syntax"](../ThymeleafTutorial/Thymeleaf_4_Standard_Expression_Syntax.md)

Но что, если бы мы хотели установить более одного атрибута за раз?

XML-правила не позволяют вам устанавливать атрибут дважды в теге, поэтому `th:attr` возьмет список назначений, разделенных
запятыми, например:

```html
<img src="../../images/gtvglogo.png"
     th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
```

Учитывая необходимые файлы сообщений, это будет выводить:

```html
<img src="/gtgv/images/gtvglogo.png" title="Logo de Good Thymes" alt="Logo de Good Thymes" />
```

---
#### 5.2 Установка значения для определенных атрибутов

К настоящему моменту вы могли бы подумать, что что-то вроде:

```html
<input type="submit" value="Subscribe!" th:attr="value=#{subscribe.submit}"/>
```

… довольно некрасивый кусок разметки. Указание назначения внутри значения атрибута может быть очень практичным, но это
не самый элегантный способ создания шаблонов, если вы должны делать это все время.

Thymeleaf соглашается с вами, и именно поэтому `th:attr` вряд ли используется в шаблонах. Обычно вы будете использовать
другие атрибуты `th:*`, задача которых заключается в настройке определенных атрибутов тега (а не только любого атрибута,
такого как `th:attr`).

Например, чтобы установить атрибут `value`, используйте значение `th:value`:

```html
<input type="submit" value="Subscribe!" th:value="#{subscribe.submit}"/>
```

Это выглядит намного лучше! Давайте попробуем сделать то же самое с атрибутом `action` в теге `form`:

```html
<form action="subscribe.html" th:action="@{/subscribe}">
```

Помните `th:href`, которые мы помещали в наш [home.html](https://github.com/thymeleaf/thymeleafexamples-gtvg/blob/3.1-master-jakarta/src/main/webapp/WEB-INF/templates/home.html) раньше? 
Это точно такие же атрибуты:

```html
<li><a href="product/list.html" th:href="@{/product/list}">Product List</a></li>
```

Таких атрибутов достаточно много, каждый из которых нацелен на определенный атрибут HTML5:

| | | |  
| --------------------- | ------------------  | ----------------- |
| th:abbr               | th:accept           | th:accept-charset |
| th:accesskey          | th:action           | th:align          |
| th:alt	              | th:archive          | th:audio          |
| th:autocomplete       | th:axis             | th:background     |
| th:bgcolor            | th:border           | th:cellpadding    |
| th:cellspacing        | th:challenge        | th:charset        |
| th:cite               | th:class            | th:classid        |
| th:codebase           | th:codetype	        | th:cols           |
| th:colspan	          | th:compact          | th:content        |
| th:contenteditable    | th:contextmenu      | th:data           |
| th:datetime           | th:dir	            | th:draggable      |
| th:dropzone	          | th:enctype          | th:for            |
| th:form               | th:formaction       | th:formenctype    |
| th:formmethod	        | th:formtarget       | th:fragment       |
| th:frame              | th:frameborder	    | th:headers        |
| th:height	            | th:high	            | th:href           |
| th:hreflang           | th:hspace	          | th:http-equiv     |
| th:icon               | th:id               | th:inline         |
| th:keytype            | th:kind             | th:label          |
| th:lang               | th:list	            | th:longdesc       |
| th:low                | th:manifest	        | th:marginheight   |
| th:marginwidth        | th:max              | th:maxlength      |
| th:media              | th:method	          | th:min            |
| th:name	              | th:onabort	        | th:onafterprint   |
| th:onbeforeprint	    | th:onbeforeunload   | th:onblur         |
| th:oncanplay	        | th:oncanplaythrough | th:onchange       |
| th:onclick	          | th:oncontextmenu	  | th:ondblclick     |
| th:ondrag             | th:ondragend	      | th:ondragenter    |
| th:ondragleave	      | th:ondragover       | th:ondragstart    |
| th:ondrop	            | th:ondurationchange | th:onemptied      |
| th:onended            | th:onerror          | th:onfocus        |
| th:onformchange       | th:onforminput      | th:onhashchange   |
| th:oninput            | th:oninvalid	      | th:onkeydown      |
| th:onkeypress         | th:onkeyup	        | th:onload         |
| th:onmessage	        | th:onmousedown	    | th:onmousemove    |
| th:onmouseout	        | th:onmouseover      | th:onmouseup      |
| th:onmousewheel       | th:onoffline	      | th:ononline       |
| th:onpause            | th:onplay	          | th:onplaying      |
| th:onpopstate         | th:onprogress	      | th:onratechange   |
| th:onreadystatechange | th:onredo	          | th:onreset        |
| th:onresize	          | th:onscroll	        | th:onseeked       |
| th:onseeking	        | th:onselect	        | th:onshow         |
| th:onstalled	        | th:onstorage	      | th:onsubmit       |
| th:onsuspend	        | th:ontimeupdate	    | th:onundo         |
| th:onunload	          | th:onvolumechange	  | th:onwaiting      |
| th:optimum	          | th:pattern	        | th:placeholder    |
| th:poster	            | th:preload	        | th:radiogroup     |
| th:rel	              | th:rev	            | th:rows           |
| th:rowspan	          | th:rules	          | th:sandbox        |
| th:scheme	            | th:scope	          | th:scrolling      |
| th:spellcheck	        | th:src	            | th:srclang        |
| th:standby	          | th:start	          | th:step           |
| th:style	            | th:summary	        | th:tabindex       |
| th:target	            | th:title	          | th:type           |
| th:usemap	            | th:value	          | th:valuetype      |
| th:vspace	            | th:width	          | th:wrap           |
| th:xmlbase	          | th:xmllang	        | th:xmlspace       |

---
#### 5.3 Установка нескольких значений за раз

Есть два довольно специальных атрибута, называемых `th:alt-title` и `th:lang-xmllang`, которые могут использоваться для
одновременного задания двух атрибутов в одно и то же время.

**В частности:**
- `th:alt-title` - будет задано значение alt и title;
- `th:lang-xmllang` - будет устанавливать lang и xml:lang;

Для нашей домашней страницы GTVG это позволит нам заменить это:

```html
<img src="../../images/gtvglogo.png"
     th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
``

… или это, что эквивалентно:

```html
<img src="../../images/gtvglogo.png"
     th:src="@{/images/gtvglogo.png}" th:title="#{logo}" th:alt="#{logo}" />
```

… на это:

```html
<img src="../../images/gtvglogo.png"
     th:src="@{/images/gtvglogo.png}" th:alt-title="#{logo}" />
```

---
#### 5.4 Добавить до- и -после

Thymeleaf также предлагает атрибуты `th:attrappend` и `th:attrprepend`, которые добавляют (суффикс) или добавляют (префикс)
результат к существующим значениям атрибутов.

Например, вы можете захотеть сохранить имя добавляемого класса CSS для одной из ваших кнопок в переменной контекста,
поскольку конкретный класс CSS, который будет использоваться, будет зависеть от того, что пользователь сделал до того:

```html
<input type="button" value="Do it!" class="btn" th:attrappend="class=${' ' + cssStyle}" />
```

Если вы обрабатываете этот шаблон с переменной cssStyle, установленной в «warning», вы получите:

```html
<input type="button" value="Do it!" class="btn warning" />
```

В Standard Dialect есть также два специальных атрибута добавления, атрибуты `th:classappend` и `th:styleappend`, которые
используются для добавления класса CSS или фрагмента стиля к элементу без перезаписывания существующих:

```html
<tr th:each="prod : ${prods}" class="row" th:classappend="${prodStat.odd}? 'odd'">
```

(Не беспокойтесь о значении `th:each`. Это атрибут итерации, и мы поговорим об этом позже).

---
#### 5.5 Булевые аттрибуты с фиксированным значением

HTML имеет концепцию логических атрибутов, которые активируются только, если их значение «true». В XHTML эти атрибуты
принимают только одно значение, которым и являются.

Например, проверьте:

```html
<input type="checkbox" name="option2" checked /> <!-- HTML -->
<input type="checkbox" name="option1" checked="checked" /> <!-- XHTML -->
```

Standard Dialect включает в себя атрибуты, которые позволяют вам устанавливать эти атрибуты по условию, так что если его
значение равно «true», атрибут будет установлен в его фиксированное значение, а если он будет равен «false», атрибут не
будет установлен:

```html
<input type="checkbox" name="active" th:checked="${user.active}" />
```

В Standard Dialect существуют следующие логические атрибуты с фиксированным значением:
| | | |
|:------------------|:-------------|:--------------|
| th:async          | th:autofocus | th:autoplay   |
| th:checked        | th:controls  | th:declare    |
| th:default        | th:defer     | th:disabled   |
| th:formnovalidate | th:hidden    | th:ismap      |
| th:loop           | th:multiple  | th:novalidate |
| th:nowrap         | th:open      | th:pubdate    |
| th:readonly       | th:required  | th:reversed   |
| th:scoped         | th:seamless  | th:selected   |

---
#### 5.6 Установка значения любого атрибута (процессор атрибутов по умолчанию)

Thymeleaf предлагает процессор атрибутов по умолчанию, который позволяет нам устанавливать значение любого атрибута,
даже если для него не определен `th:*` конкретный процессор в Standard Dialect.

Что-то вроде этого:

```html
<span th:whatever="${user.name}">...</span>
```

Результатом будет:

```html
<span whatever="John Apricot">...</span>
```

---
#### 5.7 Поддержка аттрибутов, дружественных HTML5 и имен элементов

Также можно использовать совершенно другой синтаксис для применения процессоров к вашим шаблонам более удобным для HTML5 способом.

```html
<table>
    <tr data-th-each="user : ${users}">
        <td data-th-text="${user.login}">...</td>
        <td data-th-text="${user.name}">...</td>
    </tr>
</table>
```

Синтаксис data-{prefix}-{name} является стандартным способом записи пользовательских атрибутов в HTML5, не требуя от
разработчиков использования namespaced, таких как `th:*`. Thymeleaf делает этот синтаксис автоматически доступным для
всех ваших диалектов (не только для Standard).

Существует также синтаксис для указания пользовательских тегов: `{prefix}-{name}`, который следует спецификации
W3C Custom Elements (часть большой спецификации W3C Web Components). Это можно использовать, например, для элемента
`th:block` (или `th-block`), который [будет объяснен в следующем разделе](../ThymeleafTutorial/Thymeleaf_6_Iterations.md).

**!!! 
Важно: этот синтаксис является дополнением к namespaced `th:*`, он не заменяет его. Нет никакого намерения вообще
отказаться от namespaced синтаксиса в будущем 
!!!**
