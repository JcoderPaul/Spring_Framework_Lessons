package spring.oldboy.dto;
/*
Lesson 88 - На запрос с разбивкой по страницам нам
нужен ответ, который будет отображаться пользователю
*/
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PagePaginationResponse <T> {
    /*
    Поле параметризованное Т, в нашем конкретном случае
    этот класс будет использоваться для класса user, хотя
    может использоваться и для company и т.д.
    */
    List<T> content;
    /*
    Поле в котором будет храниться мета-информация:
    - page - номер страницы;
    - size - количество записей на одной странице;
    - totalElements - количество страниц записями из БД;
    см. сам класс ниже.
    */
    Metadata metadata;

    public static <T> PagePaginationResponse<T> of(Page<T> page) {
        Metadata metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
        return new PagePaginationResponse<>(page.getContent(), metadata);
    }

    @Value
    public static class Metadata {
        int page;
        int size;
        long totalElements;
    }
}