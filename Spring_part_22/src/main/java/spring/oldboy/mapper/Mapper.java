package spring.oldboy.mapper;

/* Обычно F - From (из этого), T - To (в этот) */

public interface Mapper<F, T> {

    T map(F object);
    /*
    Чуть перегрузим метод, фактически он понадобится в тот момент когда разработчику
    реально понадобится преобразовать объект F в объект T именно таким образом, например
    методом копирования, реализация метода произойдет именно в том классе, где это нужно.
    У нас это UserCreateEditMapper.java
    */
    default T map(F fromObject, T toObject) {
        return toObject;
    }

}
