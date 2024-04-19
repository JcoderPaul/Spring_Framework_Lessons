package spring.oldboy.dto;

import java.time.LocalDate;

/*
Lesson 88:
Каждый учитель городит свои заборы в уме студента! Если с фильтрацией User-ов
было все более или менее понятно, то с постраничным выводом возникли вопросы,
которые показали, что и с фильтрацией не все так гладко выглядит. Иногда
название класса все поправляет. Например, текущее название этого record-a,
намекает на то, что параметры оного - есть критерии фильтрации и как это не
странно они же критерии поиска. Мы же ищем записи в БД удовлетворяющие неким
условиям - "критериям". Отсюда название класса было бы уместнее задать как -
UserSearchCriteria.
*/
public record UserFilterDto(String firstname,
                            String lastname,
                            LocalDate birthDate) {
}