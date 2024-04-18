package spring.oldboy.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.oldboy.database.entity.Role;
import spring.oldboy.dto.PagePaginationResponse;
import spring.oldboy.dto.UserCreateEditDto;
import spring.oldboy.dto.UserFilterDto;
import spring.oldboy.dto.UserReadDto;
import spring.oldboy.service.CompanyService;
import spring.oldboy.service.UserService;

/*
Наш класс это контроллер и мы его аннотируем соответственно,
он будет обрабатывать запросы к нашему приложению на работу
с БД, а точнее с записями user-ов.
*/
@Controller
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
    private final UserService userService;
    private final CompanyService companyService;

    /*
    Определили какой HTTP метод запроса будет использоваться. Данный
    метод будет активирован запросом: http://localhost:8080/users
    */
    @GetMapping
    /* Методы самого Spring приложения, как и раньше мы именуем отглагольными названиями */
    public String findAll(Model model) {
        /* Используя метод UserService мы возвращаем всех запрошенных пользователей */
        model.addAttribute("users", userService.findAll());
        /*
        Возвращаем view соответствующий полученному запросу,
        у нас это - resources/templates/user/users.html
        */
        return "user/users";
    }

    /*
    Определили какой HTTP метод запроса будет использоваться. Данный
    метод будет активирован запросом: http://localhost:8080/users/filter
    */
    @GetMapping("/filter")
    /* Методы самого Spring приложения, как и раньше мы именуем отглагольными названиями */
    public String findAll(Model model, UserFilterDto filter) {
        /* Используя метод UserService мы возвращаем всех запрошенных пользователей */
        model.addAttribute("users", userService.findAll(filter));
        /*
        Возвращаем view соответствующий полученному запросу, у
        нас это - resources/templates/user/users_with_filter.html
        */
        return "user/users_with_filter";
    }

    /*
    Определили какой HTTP метод запроса будет использоваться. Данный
    метод будет активирован запросом: http://localhost:8080/users/pagination
    */
    @GetMapping("/pagination")
    public String findAll(Model model, UserFilterDto filter, Pageable pageable) {
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        model.addAttribute("users", PagePaginationResponse.of(page));
        model.addAttribute("filter", filter);
        return "user/users_with_pagination";
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        /*
        В данной ситуации мы должны вернуть страницу с данными по одному User-у,
        либо страницу с сообщением о том, что User с таким ID не найден.
        */
        return userService.findById(id)
                .map(user -> {
                    /*
                    Добавляем атрибуты во View и возвращаем его. Теперь у нас есть доступ
                    к конкретной записи user в БД, к списку всех roles и списку всех companies.
                    На нашей странице отображения мы выведем role в виде радио-кнопок, а
                    список companies в виде выпадающего списка, чтобы можно было только выбирать
                    из существующих в БД.
                    */
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("companies", companyService.findAll());
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
    Основная идея такова, что мы вносим данные в БД, и если произошла ошибка валидации, мы не
    вбиваем заново все данные, а используем уже введенные, и исправляем ошибку во введенных
    данных перед следующим сохранением. Что бы реализовать этот функционал мы в модель добавляем
    атрибут user через model.addAttribute("user", user);

    Еще раз повторим: После запуска приложения SpringAppRunner.java мы можем обратиться к нему,
    а точнее к данному методу *.registration() через браузер по адресу: 'http://localhost:8080/'
    (адрес сервера) + '/users' (общий RequestMapping для методов данного класса контроллера) +
    '/registration' (маппинг текущего метода) т.е. мы вбиваем:
    http://localhost:8080/users/registration и попадаем на форму регистрации напрямую, а не
    через кнопку Registration формы login.html (на которой GET запросом мы делаем то же самое).
    */
    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("companies", companyService.findAll());
        /*
        Снова повтор: Именно отсюда resources/templates/user/registration.html будет возвращена
        страница на запрос пользователя, только в return "..." она указана без префикса и суффикса.
        В общих чертах, так это работает со всеми методами в данном (и др.) классе.
        1 - запускается приложение (сервер TomCat);
        2 - мы обращаемся к нему с HTTP запросом через браузер (или другим способом);
        3 - согласно маппингу (карте методов) приложение обрабатывает запрос;
        4 - при наличии правильного запроса (верной карты, адреса) пользователь получает ответ;
        5 - ответ может быть 'условно ожидаемым' если запрос верный (имеет соответствие в карте)
            или будет возвращен один из вариантов ошибки 4** или 5**
        */
        return "user/registration";
    }

    /*
    Помним, что поиск, создание, обновление и удаление мы регулируем через
    тип HTTP запроса, это может быть, как POST, так или PUT вариант запроса.
    */
    @PostMapping
    public String create(@ModelAttribute UserCreateEditDto user,
                                         RedirectAttributes redirectAttributes) {

        /*
        Вариант с регистрацией:
        if (true) {
           redirectAttributes.addAttribute("username", user.getUsername());
           redirectAttributes.addAttribute("firstname", user.getFirstname());
           redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/users/registration";
        }
        */

        /*
        Перенаправление на метод *.findById(), который возвращает заполненную
        стр. user.html, а та в свою очередь позволяет редактировать данные по
        выбранной записи user-a и вызвать метод *.update()
        */
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
