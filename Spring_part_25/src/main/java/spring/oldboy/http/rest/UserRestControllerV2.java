package spring.oldboy.http.rest;

/* Lesson 101: Использование ResponseEntity */


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.oldboy.dto.PagePaginationResponse;
import spring.oldboy.dto.UserFilterDto;
import spring.oldboy.dto.UserReadDto;
import spring.oldboy.service.UserService;


/*
Первые две аннотации говорят о том, что класс реализует принципы REST API.
Аннотация @RestController объединяет в себе аннотации важные для нас:
- @Controller
- @ResponseBody
*/
@RestController
/*
Взаимодействие происходит с нашим API, с первой его версией,
такой вариант версионности рассмотрен в статьях:
- DOC/ImportantAspectsOfRESTfulAPI.txt
- DOC/REST_API/6_Rest_API_Best_Practices.txt

'/users' - работаем с записями таблицы БД - Users.
*/
@RequestMapping("/api/v2/users")
@RequiredArgsConstructor
public class UserRestControllerV2 {

    private final UserService userService;

    /*
    REST с пагинацией, можно сделать запрос на адрес, например (стр. = 1, кол. записей = 3):
    http://localhost:8080/api/v2/users?firstname=&lastname=&birthDate=&page=1&size=3
    И получить JSON с данными, например, с первой страницы списка разбитого по 3-и user-a на
    страницу (как в запросе).
    */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagePaginationResponse<UserReadDto>> findAll(UserFilterDto filter,
                                                                       Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(PagePaginationResponse.of(userService.findAll(filter, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return userService.findById(id)
                          .map(content -> ResponseEntity.ok()
                                                        .body(content))
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /*
    При загрузке Swagger-a мы увидим, что у нас появился еще один REST контроллер, будут
    описаны его методы, данный доступен по адресу: http://localhost:8080/api/v2/users/{id}.

    Поскольку это Delete метод, простое обращение к нему через браузер, как мы делали в
    Post или Get запросах, вернет:

    *****************************************************************************************
    Whitelabel Error Page
    This application has no explicit mapping for /error, so you are seeing this as a fallback.

    Fri Mar 01 21:59:04 MSK 2024
    There was an unexpected error (type=Method Not Allowed, status=405).
    *****************************************************************************************

    И естественно из БД не будет удалена указанная запись. Ну и метод возвращает void, а так же
    может вернуть, как минимум два статуса, поэтому зададим возврат методом ResponseEntity<?>
    */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return userService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    /*
       Обратимся в браузере к адресу: http://localhost:8080/api/v2/users/{id}/avatar ,
       вместо ID ставим номер интересующей нас записи из БД (с аватаркой и без нее).
       После запроса, при наличии аватарки, она будет скачана (либо от браузера придет
       запрос на действие).
    */
    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                /*
                Передаем в ResponseEntity, сообщение об удачном получении данных, и формируем
                заголовки (headers), метод ok() - создание построителя со статусом ОК. Продолжаем
                работать с постороителем (builder-ом). В хедерах задаем типа заголовка и значение
                медиа типа заголовка, см. DOC/ResponseEntity/ResponseEntity.HeadersBuilder.txt.
                Далее устанавливаем contentLength - длину тела в байтах, указанную в заголовке
                Content-Length (т.е. определяем размер картинки). И наконец устанавливаем тело
                объекта ответа и возвращаем его, в противном случае возвращаем статус 404, если
                картинки-аватарки нет у конкретной записи user-a.
                */
                .map(content -> ResponseEntity.ok()
                                              .header(HttpHeaders.CONTENT_TYPE,
                                                      MediaType.APPLICATION_OCTET_STREAM_VALUE)
                                              .contentLength(content.length)
                                              .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
