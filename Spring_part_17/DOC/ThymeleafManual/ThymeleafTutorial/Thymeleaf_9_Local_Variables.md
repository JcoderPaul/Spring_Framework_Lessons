- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#more-on-configuration)
- [Thymeleaf 3.1.5.RELEASE API](https://javadoc.io/doc/org.thymeleaf/thymeleaf/latest/index.html)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Локальные переменные / Local variables

В Thymeleaf называют локальными переменными те переменные, которые определены для определенного фрагмента шаблона и
доступны только для выполнения внутри этого фрагмента.

Пример, который мы уже видели, это переменная 'prod' на нашей странице списка продуктов:

```html
    <tr th:each="prod : ${prods}">
        ...
    </tr>
```

Эта переменная 'prod' будет доступна только в пределах тега `<tr>`. 

В частности:
- Она будет доступна для любых других атрибутов `th:*`, выполняющихся в этом теге, с меньшим приоритетом, чем `th:each` (это означает, что они будут выполняться после `th:each`).
- Она будет доступна для любого дочернего элемента тега `<tr>`, такого как любые элементы `<td>`.

Thymeleaf предлагает вам способ объявить локальные переменные без итерации, используя атрибут `th:with`, а его
синтаксис — как указание значений атрибутов:

```html
    <div th:with="firstPer=${persons[0]}">
      <p>
        The name of the first person is <span th:text="${firstPer.name}">Julius Caesar</span>.
      </p>
    </div>
```

Когда `th:with` обработается, эта переменная 'firstPer' создается как локальная переменная и добавляется к списку
переменных, исходя из контекста, так что она доступна для выполнения вместе с любыми другими переменными,
объявленными в контексте, но только в пределах содержащего тега `<div>`.

Вы можете определить несколько переменных одновременно, используя обычный синтаксис множественного присваивания:

```html
    <div th:with="firstPer=${persons[0]},secondPer=${persons[1]}">
      <p>
        The name of the first person is <span th:text="${firstPer.name}">Julius Caesar</span>.
      </p>
      <p>
        But the name of the second person is
        <span th:text="${secondPer.name}">Marcus Antonius</span>.
      </p>
    </div>
```

Атрибут `th:with` позволяет повторно использовать переменные, определенные в одном и том же атрибуте:

```html
    <div th:with="company=${user.company + ' Co.'},account=${accounts[company]}">...</div>
```

Давайте использовать это на нашей домашней странице Grocery! Помните код, который мы написали для вывода форматированной даты?

```html
    <p>
      Today is:
      <span th:text="${#calendars.format(today,'dd MMMM yyyy')}">13 february 2011</span>
    </p>
```

Ну, а что, если мы хотим, чтобы `@dd MMMM yyyy@` зависела от локали?

Например, мы могли бы добавить следующее сообщение в наш [home_en.properties](https://github.com/thymeleaf/thymeleafexamples-gtvg/blob/3.1-master-jakarta/src/main/webapp/WEB-INF/templates/home_en.properties):

```html
    date.format=MMMM dd'','' yyyy
```

… и эквивалент [home_es.properties](https://github.com/thymeleaf/thymeleafexamples-gtvg/blob/3.1-master-jakarta/src/main/webapp/WEB-INF/templates/home_es.properties)

```html
    date.format=dd ''de'' MMMM'','' yyyy
```

Теперь давайте использовать `th:with`, чтобы получить локализованный формат даты в переменной, а затем использовать его в нашем `th:text` выражении:

```html
    <p th:with="df=#{date.format}">
      Today is: <span th:text="${#calendars.format(today,df)}">13 February 2011</span>
    </p>
```

Это было чисто и легко. Фактически, учитывая тот факт, что `th:with` имеет **более высокий приоритет**, чем `th:text`, мы могли
бы решить все это в теге span:

```html
    <p>
      Today is:
      <span th:with="df=#{date.format}"
            th:text="${#calendars.format(today,df)}">13 February 2011</span>
    </p>
```

**Вы могли бы подумать: Приоритет?** Мы еще не говорили об этом! 

[Об этом речь в следующей главе →](../ThymeleafTutorial/Thymeleaf_10_Attribute_Priority.md)
