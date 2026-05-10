- [См. исходник (ENG)](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#attribute-precedence)
- [Good Thymes Virtual Grocery on GitHub](https://github.com/thymeleaf/thymeleafexamples-gtvg)

---
### Приоритет атрибутов / Attribute Precedence

Что происходит, когда вы пишете в одном теге более одного атрибута `th:*` ? 

Например, так:

```html
  <ul>
    <li th:each="item : ${items}" th:text="${item.description}">Item description here...</li>
  </ul>
```

Мы ожидаем, что `th:each` атрибут будет выполняться перед `th:text` так, чтобы мы получили желаемые результаты, но учитывая
тот факт, что стандарты HTML/XML не имеют никакого значения для порядка атрибута в теге, в самих атрибутах должен быть
установлен механизм приоритета, чтобы быть уверенным, что это будет работать так, как ожидалось.

Таким образом, все атрибуты Thymeleaf определяют числовое значение, которое устанавливает порядок, в котором они выполняются в теге.

Этот порядок таков:

| Порядок | Функциональность                   | Аттрибуты                                    |
|---------|------------------------------------|----------------------------------------------|
| 1       | Включение фрагментов               | `th:insert`, `th:replace`                    |
| 2       | Итерация фрагментов                | `th:each`                                    |
| 3       | Условное выполнение                | `th:if`, `th:unless`, `th:switch`, `th:case` |
| 4       | Определение локальной переменной   | `th:object`, `th:with`                       |
| 5       | Основная модификация атрибута      | `th:attr`, `th:attrprepend`, `th:attrappend` |
| 6       | Специфическая модификация атрибута | `th:value`, `th:href`, `th:src`, ...         |
| 7       | Текст (tag body модификация)       | `th:text`, `th:utext`                        |
| 8       | Определение фрагмента              | `th:fragment`                                |
| 9       | Удаление фрагмента                 | `th:remove`                                  |

Этот механизм приоритета означает, что указанный выше фрагмент итерации даст точно такие же результаты, если позиция
атрибута инвертирована (хотя она будет немного менее читаемой):

```html
  <ul>
    <li th:text="${item.description}" th:each="item : ${items}">Item description here...</li>
  </ul>
```

[См. следующий раздел →](../ThymeleafManual/ThymeleafTutorial/Thymeleaf_11_Comments_And_Blocks.txt)
