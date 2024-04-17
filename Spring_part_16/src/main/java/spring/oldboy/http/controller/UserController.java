package spring.oldboy.http.controller;

/* Part 16: Lesson 77 - Создаем каркас нашего CRUD API */

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import spring.oldboy.dto.UserCreateEditDto;
import spring.oldboy.service.UserService;

/*
Наш класс это контроллер и мы его аннотируем соответственно,
он будет обрабатывать запросы к нашему приложению на работу
с БД, а точнее с записями user-ов.
*/
@Controller
/*
Мы определились, что мы будем работать с user-ами, а согласно статье
DOC/API_Design/API_Design_Best_Practices.txt мы применяем только
лучшие из существующих практик, поэтому для всех наших методов будет
общий адресный префикс.
*/
@RequestMapping("/users")
/*
@RequiredArgsConstructor - генерирует конструктор с 1 параметром для каждого поля,
которое требует специальной обработки. Все неинициализированные final поля получают
параметр, также как все остальные поля, помеченные @NonNull, которые не иницилизированы
при объявлении. Для этих случаев также генерируется явная проверка на null. Конструктор
бросает исключение NullPointerException, если какой-либо из параметров, предназначенный
для полей, помеченных @NonNull содержит null. Порядок этих параметров совпадает с
порядком появления полей в классе.

Добавляем аннотацию, чтобы иметь возможность внедрять соответствующие зависимости.
*/
@RequiredArgsConstructor
public class UserController {

    /*
    На уровне сервисов нам нужен класс реализующий наши
    методы для обращения на уровень репозиториев
    */
    private final UserService userService;

    /* Определили какой HTTP метод запроса будет использоваться */
    @GetMapping
    /* Методы самого Spring приложения, как и раньше мы именуем отглагольными названиями */
    public String findAll(Model model) {
        /* Используя метод UserService мы возвращаем всех запрошенных пользователей */
        model.addAttribute("users", userService.findAll());
        /* Возвращаем view соответствующий полученному запросу */
        return "user/users";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        /*
        В данной ситуации мы должны вернуть страницу с данными по одному User-у,
        либо страницу с сообщением о том, что User с таким ID не найден.
        */
        return userService.findById(id)
                .map(user -> {
                    /* Добавляем атрибуты во View и возвращаем его */
                    model.addAttribute("user", user);
                    return "user/user";
                })
                /*
                Если User не найден возвращаем страницу со статусом "не найдена", а если точнее,
                то такой страницы не существует. Т.к. ID в нашем случае часть URL страницы, нет
                User-a с ID - нет и страницы, а нет страницы - ошибка 404 или Not Found -
                стандартный код ответа HTTP о том, что клиент был в состоянии общаться с сервером,
                но сервер не может найти данные.

                И тут мы пробрасываем исключение с требуемым статусом.
                */
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /*
    Помним, что поиск, создание, обновление и удаление мы регулируем через
    тип HTTP запроса, это может быть, как POST, так или PUT вариант запроса.
    */
    @PostMapping
    /*
    После создания чего-либо на нашем сервере (нового User-a) мы должны вернуть 201
    HTTP статус - HTTP 201 Created - код ответа об успешном статусе указывает, что
    запрос выполнен успешно и привёл к созданию ресурса. Новый ресурс эффективно
    создаётся до отправки этого ответа. И новый ресурс возвращается в теле сообщения,
    его местоположение представляет собой либо URL-адрес запроса, либо содержимое
    заголовка Location.

    Для этого используем аннотацию @ResponseStatus с параметром статуса CREATED.
    */
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute UserCreateEditDto user) {
        return "redirect:/users/" + userService.create(user).getId();
    }

    /*
    Для обновления мы можем использовать PUT и PATCH запросы, тут все
    зависит от полноты обновления - всю или часть записи мы обновляем.
    Однако тут есть небольшая загвоздка - мы не используем JavaScript
    при реализации наших форм в *.jsp, то можем использовать пока,
    только POST и GET запросы, поэтому нарушим принципы 'best practice'
    при разработке нашего API и не будем использовать PUT запрос.

    Данный недочет мы исправим когда возьмемся за RESTful API.
    */
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id, @ModelAttribute UserCreateEditDto user) {
        return userService.update(id, user)
                          .map(it -> "redirect:/users/{id}")
                          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /*
    Тут мы должны были использовать HTTP метод DELETE, но,
    как и ранее в методе *.update() мы используем POST.
    */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        /*
        Метод *.delete() на уровне сервисов возвращает булево значение. Мы реализовали его так,
        чтобы иметь возможность на уровне контроллеров вернуть view удаленного пользователя или
        вернуть страницу со статусом 'Не найден' см. код ниже.
        */
        if (!userService.delete(id)) {
            /*
            Поскольку ID участвует в генерации URL страницы с результатами, то при отсутствии
            user-a с требуемым ID и такой страницы существовать не может, т.е. снова статус
            404 - NOT FOUND и генерация исключения, как в методе *.update() в схожей ситуации.
            */
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
