package spring.oldboy.database.entity;

/* Общий для всех Entity сущностей интерфейс */

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {

    T getId();

    void setId(T id);
}
