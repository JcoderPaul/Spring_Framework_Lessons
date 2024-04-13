package spring.oldboy.integration.database.repository.lesson_52;

/* Lesson 52 */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.SecondUserRepository;
import spring.oldboy.integration.annotation.IT;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/* Это интеграционный тест и мы используем нашу аннотацию */
@IT
/*
@RequiredArgsConstructor - позволит получить конструктор с параметром для каждого поля,
но эти параметры потребуют специальной обработки.  Все неинициализированные final поля
получают параметр, также как все остальные поля, помеченные @NonNull, которые не
инициализированные при объявлении.
*/
@RequiredArgsConstructor
class UserRepositoryThirdTest {

    private final SecondUserRepository secondUserRepository;

    /*
    Lesson 52:

    Тестируем ограничивающие параметры запроса. Поскольку в ходе выполнения
    запроса мы можем получить пустой результат, посему используется Optional.
    */
    @Test
    void checkFirstTopTest() {
        /*
        Top (или First) сортируем по убыванию, значит сверху у нас будет
        User с ID = 5 из нашей БД, проверяем есть ли он, и проверяем
        эквивалентность наших предположений, т.е. ID полученный из теста
        должен совпадать с ID предложенным нами.
        */
        Optional<User> topUser = secondUserRepository.findTopByOrderByIdDesc();
        assertTrue(topUser.isPresent());
        topUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }

    @Test
    void checkTopThreeUserBirthDateTest() {
        /*
        В данном случае мы указали ограничивающий параметр 3 и далее ввели ограничения и сортировку в
        название метода, а так же сортировку. В сам метод мы передали тот самый параметр который будет
        отсекать ненужные нам даты рождения сущностей User. Однако результат может быть, как менее 3-х,
        так и равным ему, но не более.

        Поскольку мы знаем, что в наше БД 5-ть User-ов, и даты их рождения раньше заданного ограничения,
        мы смело приводим утверждение ожидающее 3 на выходе.
        */
        List<User> topThreeUser = secondUserRepository.findTop3ByBirthDateBeforeOrderByBirthDateDesc(LocalDate.now());
        assertThat(topThreeUser).hasSize(3);
    }

    @Test
    void checkDynamicSortTest() {
        /*
        В наш метод *.findTop2ByBirthDateBefore(LocalDate birthDate, Sort sort),
        в качестве параметра 'sort' мы можем передать 'sortById', в котором, как
        вагоны мы сцепляем методы класса Sort для формирования нужного нам
        результата: сортируем по ID, затем по имени и уже затем по фамилии.

        Но даже тут есть маленький недостаток, мы передаем в виде строки поля нашей
        сущности, а нам бы хотелось сделать еще лучше и решение есть см. ниже.
        */
        Sort sortById = Sort.by("id").and(Sort.by("firstname").and(Sort.by("lastname")));
        /*
        Тут мы получаем и используем вложенный класс TypedSort и оперируем сущностью User и ее
        get-методами. Мы получаем типобезопасный объект TypedSort через который можем делать
        нужную нам сортировку используя методы подкласса Sort.

        Типобезопасность - свойство языка программирования, характеризующее безопасность и
        надёжность в применении его системы типов. Система типов называется безопасной или
        надёжной, если в программах, прошедших проверку согласования типов, исключена возможность
        возникновения ошибок согласования типов во время выполнения.
        */
        Sort.TypedSort<User> sortBy = Sort.sort(User.class);
        /*
        Тут мы обращаемся к гетерам сущности User
        */
        Sort sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname));

        List<User> allUsers = secondUserRepository.findTop2ByBirthDateBefore(LocalDate.now(), sort);
        assertThat(allUsers).hasSize(2);
    }

    @Test
    void checkPageableTest() {
        /*
        Класс PageRequest реализует интерфейс Pageable. В данном случае метод *.of() создает
        новый PageRequest с примененными параметрами сортировки.
        Где параметры:
        - pageNumber – номер страницы, начинающийся с нуля, не должен быть отрицательным;
        - pageSize – размер возвращаемой страницы должен быть больше 0;
        - sort – не может быть нулевым, вместо этого используйте Sort.unsorted();
        */
        PageRequest myPageable = PageRequest.of(1, 2, Sort.by("id"));
        /*
        Мы получаем одну страницу, отсчет, как всегда, идет с 0 и значит мы задали -
        получение 2-ой страницы размером в 2-е записи, отсортированной по ID сущности User.
        */
        List<User> result = secondUserRepository.findAllUserBy(myPageable);
        assertThat(result).hasSize(2);
    }
}