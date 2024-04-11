package spring.oldboy.listener;
/*
Наш слушатель событий.

И снова про паттерн 'слушатель':
- нам нужен Event (у нас это EntityEvent.java), некий генератор событий;
- нам нужен Listener (у нас это EntityListener.java), слушатель и обработчик событий;
*/
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
Поскольку он Sprig сущность (компонент, bean) аннотируем его,
для того чтобы он находился в Spring контексте и мог прослушивать
требуемые события
*/
@Component
public class EntityListener {

    /*
    Метод доступа к прослушиваемой Entity, куда она передается
    в качестве параметра. Аннотируем наш метод, придавая ему
    функциональность слушателя.

    Spring неявно на основании каждой аннотации @EventListener
    создаст объект типа ApplicationListener.
    */
    @EventListener
    public void acceptEntity(EntityEvent entityEvent) {
        /*
        Тут реализуется необходимая логика: аудит сущности,
        денормализация БД... т.е. наша бизнес логика
        */
        System.out.println("Entity to Listener: " + entityEvent);
    }

    /*
    Интересная опция или поле в аннотации @EventListener это 'condition' - условия,
    что позволяет, используя SpEL, писать или конфигурировать строго определенные
    слушатели, например как в данном примере, только для реакции на 'УДАЛЕНИЕ' -
    'DELETE' в аргументах метода (т.е. мы задаем некое ограничение на что реагировать
    слушателю).

    Выражение Spring Expression Language (SpEL), используемое для условной обработки
    событий. Событие будет обработано, если выражение оценивается как логическое
    значение true или одна из следующих строк: «true», «on», «yes» или «1».

    Выражение по умолчанию — «», что означает, что событие обрабатывается всегда.

    Выражение SpEL будет оцениваться в соответствии с выделенным контекстом, который
    предоставляет следующие метаданные:
    - #root.event или event для ссылок на ApplicationEvent;
    - #root.args или args-аргументы переданные в метод, для ссылок на массив аргументов
      метода;
    Доступ к аргументам метода можно получить по индексу. Например, доступ к первому
    аргументу можно получить через #root.args[0], args[0], #a0 или #p0.
    Доступ к аргументам метода можно получить по имени (с предшествующим хеш-тегом), если
    имена параметров доступны в скомпилированном байт-коде.

    Так же используя аннотацию @Order мы можем управлять порядком слушателей если это
    необходимо.
    */
    @EventListener(condition = "#root.args[0].accessType.name() == 'DELETE'")
    @Order(10)
    public void acceptEntityOnlyDelete(EntityEvent entityEvent) {
        /*
        При выбранном аргументе DELETE данный метод не сработает,
        естественно, если заменить его на 'READ', то и в консоли
        и в DEBUG-е мы увидим обращение к данному методу.
        */
        System.out.println("OnlyDeleteListener reaction to Entity: " + entityEvent);
    }
}
