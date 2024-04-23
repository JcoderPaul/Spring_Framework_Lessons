package spring.oldboy.mapper.listener;

/*
Наш слушатель событий.

И снова про паттерн 'слушатель':
- нам нужен Event (у нас это EntityEvent.java), некий генератор событий;
- нам нужен Listener (у нас это EntityListener.java), слушатель и обработчик событий;
*/

import org.springframework.context.event.EventListener;
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
    что позволяет писать или конфигурировать строго определенные слушатели, например
    только для реакции на событие чтения (записи, удаления и т.д.).

    @EventListener(condition = "#root.args[0].accessType.name() == 'READ'")
    @Order(10)
    public void acceptEntityRead(EntityEvent entityEvent) {
        System.out.println("Entity: " + entityEvent);
    }

    */
}
