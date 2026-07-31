- [См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html)

---
### Annotation Interface RequestParam

**Пакет:** [org.springframework.web.bind.annotation](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html)

```
  @Target(PARAMETER)
  @Retention(RUNTIME)
  @Documented
  public @interface RequestParam
```

Аннотация, указывающая, что параметр метода должен быть привязан к параметру веб-запроса.

Поддерживается для аннотированных методов-обработчиков в Spring MVC и Spring WebFlux следующим образом:

- В Spring MVC «параметры запроса» сопоставляются с параметрами запроса, данными формы и частями составных запросов.
  Это связано с тем, что API сервлетов объединяет параметры запроса и данные формы в единую Map, называемую
  «параметрами», и включает автоматический анализ тела запроса.

- В Spring WebFlux «параметры запроса» сопоставляются только с параметрами запроса. Чтобы работать со всеми тремя
  данными, данными запроса, формы и составными данными, вы можете использовать привязку данных к объекту команды,
  помеченному ModelAttribute.

Если тип параметра метода — Map и указано имя параметра запроса, то значение параметра запроса преобразуется в Map при
условии, что доступна соответствующая стратегия преобразования.

Если параметр метода имеет значение Map<String, String> или MultiValueMap<String, String> и имя параметра не указано,
то параметр Map заполняется всеми именами и значениями параметров запроса.

---
### Дополнительные элементы (но необязательные)

- `String defaultValue` - Значение по умолчанию, которое будет использоваться в качестве резервного варианта, если параметр
                        запроса не указан или имеет пустое значение. Предоставление значения **по умолчанию неявно
                        устанавливает required() значение - false**;
- `String name` - Имя параметра запроса, к которому осуществляется привязка;
- `boolean required` - Требуется ли параметр. **По умолчанию — true**, что приводит к выдаче исключения (ошибка 4xx), если
                     параметр отсутствует в запросе. **Переключите это значение, false если вы предпочитаете null значение,
                     если параметр отсутствует в запросе**.

  В качестве альтернативы укажите defaultValue(), который неявно устанавливает этот флаг в false;

- `String value` - Псевдоним для name();

---
- [См. исходную инф. (ENG)](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html)
- [Spring @RequestParam Annotation](https://www.baeldung.com/spring-request-param)
- [@RequestParam](https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/requestparam.html)
- [Spring MVC - @RequestParam Annotation](https://www.geeksforgeeks.org/springboot/spring-mvc-requestparam-annotation/)
- [Understanding @RequestParam and @PathVariable in Spring MVC](https://medium.com/nerd-for-tech/understanding-requestparam-and-pathvariable-in-spring-mvc-908e93abe88e)
- [What is difference between @RequestBody and @RequestParam?](https://stackoverflow.com/questions/28039709/what-is-difference-between-requestbody-and-requestparam)
