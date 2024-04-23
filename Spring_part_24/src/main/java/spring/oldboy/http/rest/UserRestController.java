package spring.oldboy.http.rest;

/* Lesson 95-96: Первый REST контроллер и его методы */

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import spring.oldboy.dto.PagePaginationResponse;
import spring.oldboy.dto.UserCreateEditDto;
import spring.oldboy.dto.UserFilterDto;
import spring.oldboy.dto.UserReadDto;
import spring.oldboy.service.UserService;
import spring.oldboy.validation.group.CreateAction;
import spring.oldboy.validation.group.UpdateAction;

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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    /*
    Вернем в браузере обычную строку, запрос:
    http://localhost:8080/api/v1/users/string
    */
    @GetMapping("/string")
    /* Возвращаем данные 'как есть' */
    @ResponseBody
    public String simpleStringResponse() {
        return "String response";
    }
    /*
    Параметры аннотации позволяют управлять:
    - consumes - данными получаемыми (или их type);
    - produces - данными возвращаемыми (или Content-type);

    В нашем случае, выбранный параметр установлен 'by default'
    и мы можем его не указывать явно, как это сделали, результат
    будет тем же.
    */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagePaginationResponse<UserReadDto> findAll(UserFilterDto filter, Pageable pageable) {
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        return PagePaginationResponse.of(page);
    }

    /*
    Метод возвращает одну запись из БД в 'формате' - UserReadDto,
    если по-указанному ID нет ничего, то возвращается 404 статус.
    */
    @GetMapping("/{id}")
    public UserReadDto findById(@PathVariable("id") Long id) {
        return userService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /* Данные в метод приходят не с внешней формы, а в виде JSON запроса */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    /* Возвращаемый статус в случае успеха */
    @ResponseStatus(HttpStatus.CREATED)
    /*
    Наш параметр user получаем аннотацию @RequestBody,
    т.е. данные для создания записи в БД придут в теле
    запроса в JSON формате.
    */
    public UserReadDto create(@Validated({Default.class, CreateAction.class})
                              @RequestBody
                              UserCreateEditDto user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserReadDto update(@PathVariable("id") Long id,
                              @Validated({Default.class, UpdateAction.class})
                              @RequestBody
                              UserCreateEditDto user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
