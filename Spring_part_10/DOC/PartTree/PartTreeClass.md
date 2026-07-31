### Class PartTree

Класс предназначен для парсинга String дерева или элементов, состоящих по очереди PartTree.OrPart из простых
экземпляров Part. Также принимает класс предметной области, чтобы проверить, что каждый из Parts ссылается на
свойство класса предметной области. Затем его PartTree можно использовать для построения запросов на основе
его API вместо анализа имени метода для каждого выполнения запроса.

---
**Пакет:** [org.springframework.data.repository.query.parser](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/parser/package-summary.html)

---
    java.lang.Object
        org.springframework.data.repository.query.parser.PartTree

**Реализующие интерфейсы:** 
- [Iterable](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Iterable.html)<[PartTree.OrPart](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/parser/PartTree.OrPart.html)>,
- [Supplier](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Supplier.html)<[Stream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Stream.html)<PartTree.OrPart>>,
- [Streamable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/util/Streamable.html)<PartTree.OrPart>

---
    public class PartTree extends Object implements Streamable<PartTree.OrPart>

---
### Вложенный класс

- static class [PartTree.OrPart](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/parser/PartTree.OrPart.html) - Часть анализируемого источника, полученная в результате разделения ресурса по ключевым словам Or.

---
### Конструктор

- PartTree(String source, Class<?> domainClass) - Создает новый PartTree путем анализа данного файла String.

---
### Методы

- Integer getMaxResults() - Возвращает количество максимальных результатов для возврата или значение NULL, если оно не
                            ограничено.

- Streamable<Part> getParts() - Возвращает одну Iterable из всех частей, содержащихся в файле PartTree.

- Streamable<Part> getParts(Part.Type type) - Возвращает все Part из PartTree заданных Part.Type.

- Sort getSort() - Возвращает Sort спецификацию, проанализированную из источника.

- boolean hasPredicate() - Возвращает, PartTree содержит ли предикат Parts.

- boolean isCountProjection() - Возвращает, следует ли применять проекцию подсчета.

- boolean isDelete() - Вернет true, если созданное PartTree предназначено для использования для операции удаления.

- boolean isDistinct() - Возвращает, указываем ли мы отдельный поиск сущностей.

- boolean isExistsProjection() - Возвращает, следует ли применять существующую проекцию.

- boolean isLimiting() - Возвращайте true, если создание PartTree предназначено для запроса с ограниченным максимальным
                         результатом.

- Iterator<PartTree.OrPart> iterator()

- String toString()

---
- Методы, унаследованные от класса java.lang.Object: clone, equals, finalize, getClass, hashCode, notify, notifyAll, wait, wait, wait
- Методы, унаследованные от интерфейса java.lang.Iterable: forEach, spliterator
- Методы, унаследованные от интерфейса org.springframework.data.util.Streamable: and, and, and, and, filter, flatMap, get, isEmpty, map, stream, toList, toSet

---
Оригинал (ENG): 
- [doc PartTree](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/query/parser/PartTree.html)
- [code PartTree](https://github.com/spring-projects/spring-data-commons/blob/main/src/main/java/org/springframework/data/repository/query/parser/PartTree.java)

---
- [Spring on GitHub](https://github.com/spring-projects)
