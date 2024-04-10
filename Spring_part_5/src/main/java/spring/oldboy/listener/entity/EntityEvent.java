package spring.oldboy.listener.entity;

/*
Создадим сущность, которая будет прослушиваться. События или изменения, которой
будут фиксироваться слушателем. Слушатель же сможет подписаться или отписаться от
прослушивания событий этой сущности.

Еще раз:
- есть некий объект подверженный изменениям, и он транслирует эти изменения
во вне (некий 'транслирующий канал');
- есть другой объект - слушатель - который прослушивает 'эфир'. Но не весь, а
только 'те каналы' на которые 'подписан'. И от которых он может отписаться и
не прослушивать при определенных условиях.
*/
import java.util.EventObject;

/*
Для того чтобы наш Event подключился к системе слушателей
Spring-а он должен наследовать от EventObject, либо от
класса ApplicationEvent.
*/
public class EntityEvent extends EventObject {

    /* Наш Event обладает полями и методами */
    private final AccessType accessType;

    public EntityEvent(Object entity, AccessType accessType) {
        super(entity);
        this.accessType = accessType;
    }

    public AccessType getAccessType() {
        return accessType;
    }
}
