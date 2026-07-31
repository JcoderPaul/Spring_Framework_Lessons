### Enum LockModeType

**Библиотека (см. док.):** 
- [javax.persistence (on Oracle)](https://docs.oracle.com/javaee/7/api/javax/persistence/package-summary.html);
- [javax.persistence (on Hibernate)](https://docs.hibernate.org/jpa/2.1/api/javax/persistence/package-summary.html);

---
    java.lang.Object
        java.lang.Enum<LockModeType>
            javax.persistence.LockModeType

---
**Реализующие интерфейсы:** Serializable, Comparable<LockModeType>

---
    public enum LockModeType extends Enum<LockModeType>

---
Режимы блокировки можно указать путем передачи LockModeType аргумента одному из EntityManager методов, принимающих
блокировки (lock, find или refresh), или методу Query.setLockMode() или TypedQuery.setLockMode().

Режимы блокировки можно использовать для задания оптимистических или пессимистических блокировок.

Оптимистические блокировки задаются с помощью `LockModeType.OPTIMISTIC` и `LockModeType.OPTIMISTIC_FORCE_INCREMENT`. 
Значения типа режима блокировки `LockModeType.READ` (**устаревший**) и `LockModeType.WRITE` (**устаревший**) являются 
синонимами OPTIMISTIC и OPTIMISTIC_FORCE_INCREMENT соответственно. Последним следует отдавать предпочтение для новых 
приложений.

**Семантика запроса блокировок типа LockModeType.OPTIMISTIC и LockModeType.OPTIMISTIC_FORCE_INCREMENT следующая.**

Если транзакция T1 требует блокировки типа LockModeType.OPTIMISTIC версионного объекта, менеджер объектов должен
гарантировать, что не произойдет ни одно из следующих явлений:

- P1 (грязное чтение): транзакция T1 изменяет строку. Затем другая транзакция T2 считывает эту строку и получает
                       измененное значение до того, как T1 зафиксирует или откатит ее. Транзакция T2 в конечном
                       итоге успешно фиксируется; не имеет значения, фиксирует ли T1 или откатывает его и делает
                       ли он это до или после фиксации T2.
- P2 (неповторяемое чтение): транзакция T1 считывает строку. Другая транзакция T2 затем изменяет или удаляет эту
                             строку до того, как T1 зафиксирует ее. Обе транзакции в конечном итоге успешно
                             фиксируются.

*Режимы блокировки всегда должны предотвращать явления P1 и P2.*

Кроме того, вызов блокировки типа LockModeType.OPTIMISTIC_FORCE_INCREMENT версионного объекта также приведет к
принудительному обновлению (приращению) столбца версии объекта.

Реализация персистентности не требуется для поддержки использования режимов оптимистической блокировки для неверсионных
объектов. Если он не может поддерживать такой вызов блокировки, он должен выдать метод PersistenceException.

*Режимы блокировки LockModeType.PESSIMISTIC_READ, LockModeType.PESSIMISTIC_WRITE и LockModeType.PESSIMISTIC_FORCE_INCREMENT
используются для немедленного получения долгосрочных блокировок базы данных.*

**Семантика запроса блокировок типа LockModeType.PESSIMISTIC_READ, LockModeType.PESSIMISTIC_WRITE и
LockModeType.PESSIMISTIC_FORCE_INCREMENT следующая.**

Если транзакция T1 требует блокировки типа LockModeType.PESSIMISTIC_READ или LockModeType.PESSIMISTIC_WRITE объекта,
менеджер объекта должен гарантировать, что не может произойти ни одно из следующих явлений:

- P1 (грязное чтение): транзакция T1 изменяет строку. Затем другая транзакция T2 считывает эту строку и получает
                       измененное значение до того, как T1 зафиксирует или откатит ее.
- P2 (неповторяемое чтение): транзакция T1 считывает строку. Другая транзакция T2 затем изменяет или удаляет эту
                             строку до того, как T1 зафиксирует или откатит ее.

---
- Блокировка LockModeType.PESSIMISTIC_WRITE на экземпляре объекта дает возможность получить блокировку, чтобы
  принудительно выполнить сериализацию между транзакциями, пытающимися обновить данные объекта.
- Блокировку LockModeType.PESSIMISTIC_READ можно использовать для запроса данных с использованием семантики повторяемого
  чтения без необходимости пересчитывать данные в конце транзакции для получения блокировки и без блокировки других
  транзакций, читающих данные.
- Блокировку LockModeType.PESSIMISTIC_WRITE можно использовать при запросе данных, и существует высокая вероятность
  взаимоблокировки или сбоя обновления среди одновременных транзакций обновления.

---
Реализация персистентности должна поддерживать использование блокировок типа LockModeType.PESSIMISTIC_READ и
LockModeType.PESSIMISTIC_WRITE как для неверсионного объекта, так и для версионного объекта.

Если блокировку невозможно получить, а сбой блокировки базы данных приводит к откату на уровне транзакции, поставщик
должен выдать PessimisticLockException и убедиться, что транзакция JTA или EntityTransaction была помечена для отката.

Если блокировку невозможно получить, а ошибка блокировки базы данных приводит только к откату на уровне инструкции,
поставщик должен выдать LockTimeoutException (и не должен помечать транзакцию для отката).

---
#### Константы ENUM

- NONE - Нет замка (no lock).
- OPTIMISTIC - Оптимистичный замок. Пример применения: [VersionOptimisticLock.java](https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_8/src/main/java/oldboy/lesson_29/VersionOptimisticLock.java)
  и [Worker.java](https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_8/src/main/java/oldboy/entity/Worker.java ).
- OPTIMISTIC_FORCE_INCREMENT - Оптимистическая блокировка с обновлением версии.
- PESSIMISTIC_FORCE_INCREMENT - Пессимистическая блокировка записи с обновлением версии.
- PESSIMISTIC_READ - Пессимистическая блокировка чтения.
- PESSIMISTIC_WRITE - Пессимистическая блокировка записи.
- *READ (старый)* - Синоним с `OPTIMISTIC`.
- *WRITE (старый)* - Синоним с `OPTIMISTIC_FORCE_INCREMENT`.

---
#### Методы

- static LockModeType `valueOf(String name)` - Возвращает константу перечисления этого типа с указанным именем.
- static LockModeType[] `values()` - Возвращает массив, содержащий константы этого типа перечисления, в том порядке, в котором они объявлены.

---
- Методы, унаследованные от класса java.lang.Enum: clone, compareTo, equals, finalize, getDeclaringClass, hashCode, name, ordinal, toString, valueOf;
- Методы, унаследованные от класса java.lang.Object: getClass, notify, notifyAll, wait;

---
- См. оригинал (ENG) - [LockModeType](https://docs.oracle.com/javaee/7/api/javax/persistence/LockModeType.html);
- [Применительно к Spring (ENG)](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/);
