package spring.oldboy.mapper.listener;

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

import lombok.Getter;

import java.util.EventObject;

/*
Для того чтобы наш Event подключился к системе слушателей
Spring-а он должен наследовать от EventObject, либо от
класса ApplicationEvent.
*/
public class EntityEvent extends EventObject {
    /*
    Наш Event обладает полями и методами, удалим явно прописанный метод
    *.getAccessTyp() и поставим соответствующую аннотацию над полем, для
    которого нужен геттер. Естественно если мы хотим получить геттеры для
    всех полей, аннотируем сразу класс.
    */
    @Getter
    private final AccessType accessType;

    public EntityEvent(Object entity, AccessType accessType) {
        super(entity);
        this.accessType = accessType;
    }
}
