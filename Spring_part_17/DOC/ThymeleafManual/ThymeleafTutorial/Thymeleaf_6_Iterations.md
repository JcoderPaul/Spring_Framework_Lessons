- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#iteration)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Перебор элементов

На текущий момент мы обсудили и создали домашнюю страницу, страницу профиля пользователя, а также страницу, позволяющую
пользователям подписаться на нашу рассылку. Но, как насчет наших продуктов? Для этого нам понадобится способ перебора
элементов в коллекции.

---
#### 6.1 Основы итерации

Для отображения продуктов на нашей странице [list.html](https://github.com/thymeleaf/thymeleafexamples-gtvg/blob/3.1-master-jakarta/src/main/webapp/WEB-INF/templates/product/list.html) мы используем таблицу. Каждый из продуктов отображается в строке (элемент `<td>`), поэтому создаем такую строку и инструктируем Thymeleaf повторить ее по одному разу для каждого продукта.

Стандартный диалект предлагает специальный атрибут: `th:each`.

---
**Использование `th:each`**

Для страницы списка продуктов понадобится метод контроллера, который извлекает список продуктов из уровня сервиса и
добавляет его в контекст шаблона:

```java
  public void process(final HttpServletRequest request,
                      final HttpServletResponse response,
                      final ServletContext servletContext,
                      final ITemplateEngine templateEngine) throws Exception {
  
      ProductService productService = new ProductService();
      List<Product> allProducts = productService.findAll();
  
      WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
  
      ctx.setVariable("prods", allProducts);
  
      templateEngine.process("product/list", ctx, response.getWriter());
  }
```

И тогда мы будем использовать `th:each` в нашем шаблоне для итерации по списку продуктов:

```java
  <!DOCTYPE html>
  <html xmlns:th="http://www.thymeleaf.org">
    <head>
      <title>Good Thymes Virtual Grocery</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
      <link rel="stylesheet" type="text/css" media="all"
            href="../../../css/gtvg.css" th:href="@{/css/gtvg.css}" />
    </head>
    <body>
      <h1>Product list</h1>
      <table>
        <tr>
          <th>NAME</th>
          <th>PRICE</th>
          <th>IN STOCK</th>
        </tr>
        <tr th:each="prod : ${prods}">
          <td th:text="${prod.name}">Onions</td>
          <td th:text="${prod.price}">2.41</td>
          <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
        </tr>
      </table>
      <p>
        <a href="../home.html" th:href="@{/}">Return to home</a>
      </p>
    </body>
  </html>
```

Атрибут `prod - ${prods}`, которое вы видите выше, означает - «для каждого элемента результат выполнения `${prods}`,
повторите этот фрагмент шаблона, используя текущий элемент переменной под названием prod». Давайте дадим имя каждой
из вещей, которые мы видим:
- Мы будем называть `${prods}` итерированное выражение или итерированную переменную;
- Мы будем называть `prod` переменной итерации или просто переменной `iter`;
- Обратите внимание, что переменная `prod` привязана к элементу `<td>`, что означает, что она доступна для внутренних тегов, таких как `<td>`;

---
**Итерируемые значения**

Класс реализующий [List](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/List.html) не является единственным, который может использоваться для итерации в Thymeleaf. 
Существует довольно большой набор объектов, которые подходят для `th:each` атрибута:
- Любой объект, реализующий [Iterable](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html);
- Любой объект, реализующий [Enumeration](https://docs.oracle.com/javase/8/docs/api/java/util/Enumeration.html);
- Любой объект, реализующий [Iterator](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html), значения которого будут использоваться, поскольку они возвращаются
  итератором, без необходимости кэшировать все значения в памяти;
- Любой объект, реализующий [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html). При итерации карт переменные iter будут иметь класс [java.util.Map.Entry](https://docs.oracle.com/javase/8/docs/api/java/util/Map.Entry.html);
- Любой массив;
- Любой другой объект будет обрабатываться так, как если бы это был однозначный список, содержащий сам объект;

---
#### 6.2 Сохранение статуса итерации

При использовании `th:each`, Thymeleaf предлагает механизм, полезный для отслеживания состояния вашей итерации: *переменная состояния*. 

Переменные состояния определяются в пределах атрибута `th:each` и содержат следующие данные:
- Текущий индекс итерации, начиная с 0. Это свойство *index*;
- Текущий индекс итерации, начиная с 1. Это свойство *count*;
- Общее количество элементов в итерированной переменной. Это свойство *size*;
- Переменная *iter* для каждой итерации. Это текущее свойство;
- Будет ли текущая итерация четной или нечетной. Это *even/odd boolean* свойства;
- Является ли текущая итерация первой. Это *first boolean* свойство;
- Является ли текущая итерация последней. Это *last boolean* свойство;

Посмотрим, как мы можем использовать итерационные свойства:

```html
  <table>
    <tr>
      <th>NAME</th>
      <th>PRICE</th>
      <th>IN STOCK</th>
    </tr>
    <tr th:each="prod,iterStat : ${prods}" th:class="${iterStat.odd}? 'odd'">
      <td th:text="${prod.name}">Onions</td>
      <td th:text="${prod.price}">2.41</td>
      <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
    </tr>
  </table>
```

Переменная состояния (`iterStat` в этом примере) определяется в атрибуте `th:each`, записывая свое имя после самой
переменной `iter`, разделенной запятой. Так же, как итеративная переменная, переменная статуса привязана к фрагменту
кода, определяемому тегом, содержащим `th:each` атрибут.

Давайте посмотрим на результат обработки нашего шаблона:

```html
  <!DOCTYPE html>
  <html>
    <head>
      <title>Good Thymes Virtual Grocery</title>
      <meta content="text/html; charset=UTF-8" http-equiv="Content-Type"/>
      <link rel="stylesheet" type="text/css" media="all" href="/gtvg/css/gtvg.css" />
    </head>
    <body>
      <h1>Product list</h1>
        <table>
        <tr>
          <th>NAME</th>
          <th>PRICE</th>
          <th>IN STOCK</th>
        </tr>
        <tr class="odd">
          <td>Fresh Sweet Basil</td>
          <td>4.99</td>
          <td>yes</td>
        </tr>
        <tr>
          <td>Italian Tomato</td>
          <td>1.25</td>
          <td>no</td>
        </tr>
        <tr class="odd">
          <td>Yellow Bell Pepper</td>
          <td>2.50</td>
          <td>yes</td>
        </tr>
        <tr>
          <td>Old Cheddar</td>
          <td>18.75</td>
          <td>yes</td>
        </tr>
      </table>
      <p>
        <a href="/gtvg/" shape="rect">Return to home</a>
      </p>
    </body>
  </html>
```

Обратите внимание, что наша переменная `status` итерации отлично работает, устанавливая нечетный класс CSS только для нечетных строк.

Если вы явно не задаете переменную статуса, Thymeleaf всегда будет создавать ее для вас путем суффикса Stat к имени переменной итерации:

```html
  <table>
    <tr>
      <th>NAME</th>
      <th>PRICE</th>
      <th>IN STOCK</th>
    </tr>
    <tr th:each="prod : ${prods}" th:class="${prodStat.odd}? 'odd'">
      <td th:text="${prod.name}">Onions</td>
      <td th:text="${prod.price}">2.41</td>
      <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
    </tr>
  </table>
```

---
### 6.3 Оптимизация путем ленивого извлечения данных

Иногда нам может понадобиться оптимизировать обход коллекций данных (например, из базы данных), чтобы эти коллекции
извлекались только в том случае, если они действительно будут использоваться.

Фактически, это то, что может быть применено к любой части данных, но с учетом размера, который может иметь коллекция
в памяти, извлечение коллекций, предназначенных для повторения, является наиболее распространенным случаем для этого
сценария.

Чтобы поддержать этот механизм, Thymeleaf предлагает инструментарий для ленивой загрузки переменных контекста.
Контекстные переменные, реализующие интерфейс [ILazyContextVariable](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.14.RELEASE/org/thymeleaf/context/ILazyContextVariable.html), скорее всего, расширяя его реализацию по умолчанию [LazyContextVariable](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.14.RELEASE/org/thymeleaf/context/LazyContextVariable.html), будут разрешены в момент выполнения. 

Например:

```java
  context.setVariable("users", new LazyContextVariable<List<User>>()
      {
           @Override
           protected List<User> loadValue() {
               return databaseRepository.findAllUsers();
           }
      });
```

Эта переменная может использоваться без знания, ленивая ли она, например:

```html
  <ul>
    <li th:each="u : ${users}" th:text="${u.name}">user name</li>
  </ul>
```

Но, в то же время, никогда не будет инициализироваться (метод [loadValue()](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.14.RELEASE/org/thymeleaf/context/LazyContextVariable.html#loadValue()) никогда не будет вызываться), если условие оценивается как `false` в коде, например:

```html
  <ul th:if="${condition}">
    <li th:each="u : ${users}" th:text="${u.name}">user name</li>
  </ul>
```

[Продолжим в следующей части →](../ThymeleafTutorial/Thymeleaf_7_Conditional_Statements.md)
