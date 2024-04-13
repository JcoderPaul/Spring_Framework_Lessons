package spring.oldboy.integration.database.repository.lesson_53;

/* Lesson 53 - Тесты на методы Pageable и Slice */

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import spring.oldboy.database.entity.User;
import spring.oldboy.database.repository.user_repository.FourthUserRepository;
import spring.oldboy.integration.annotation.IT;

/* Это интеграционный тест и мы используем нашу аннотацию */
@IT
/*
@RequiredArgsConstructor - позволит получить конструктор с параметром для каждого поля,
но эти параметры потребуют специальной обработки.  Все неинициализированные final поля
получают параметр, также как все остальные поля, помеченные @NonNull, которые не
инициализированные при объявлении.
*/
@RequiredArgsConstructor
class ForthUserRepositoryTest {

    private final FourthUserRepository fourthUserRepository;

    /*
    Lesson 53:

    Тестируем, скорее даже ДЕМОНСТРИРУЕМ результат работы метода при простой замене
    List<User> на Slice<User> в методе *.findAllUserBy(Pageable pageable) класса
    FourthUserRepository.java
    */

    @Test
    void checkSliceTest() {
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
        Особенность Slice-a в том, что мы можем проитерироваться по всему набору Slice-ов
        и получить их данные используя классический *.hasNext(), а если обратится к
        документации DOC/SliceInterface.txt, то можно заметить много методов подобных
        классическому Stream-у, да и сам Slice легко преобразовать в Stream.
        */
        Slice<User> myFirstSlice = fourthUserRepository.findAllUserBy(myPageable);

        /*
        Особенность перебора тут таков, что мы уже задали параметры slice-a в myPageable,
        в том числе и размер страницы, который в данной ситуации не меняется и остается
        равным в нашем случае 2-ум (и его мы можем регулировать в зависимости от наших нужд),
        а номер страницы перебирается один за другим.
        */
        while (myFirstSlice.hasNext()) {
            myFirstSlice.forEach(user -> System.out.println(user.getId()));
            myFirstSlice = fourthUserRepository.findAllUserBy(myFirstSlice.nextPageable());
            myFirstSlice.forEach(user -> System.out.println(user.getId()));
        }

    }

    @Test
    void checkPaginationTest() {
        /* В этот раз начнем запрос с 0-ой страницы */
        PageRequest myPageable = PageRequest.of(0, 2, Sort.by("id"));
        /* Для данного теста структурно, практически ничего не изменилось в сравнении с предыдущим */
        Page<User> myFirstPage = fourthUserRepository.findAllUserPagesBy(myPageable);
        /* Явное отличие мы сможем заметить в консоли при формировании запросов к БД */
        myFirstPage.forEach(user -> System.out.println(user.getId()));

        while (myFirstPage.hasNext()) {
            myFirstPage = fourthUserRepository.findAllUserPagesBy(myFirstPage.nextPageable());
            myFirstPage.forEach(user -> System.out.println(user.getId()));
        }
    }


    @Test
    void checkPaginationWithQueryCountTest() {
        /* В этот раз начнем запрос с 0-ой страницы */
        PageRequest myPageable = PageRequest.of(0, 2, Sort.by("id"));
        /* Для данного теста структурно, практически ничего не изменилось в сравнении с предыдущим */
        Page<User> myFirstPage = fourthUserRepository.findAllUserPagesWithCountBy(myPageable);
        /* Явное отличие мы сможем заметить в консоли при формировании запросов к БД */
        myFirstPage.forEach(user -> System.out.println(user.getId()));

        while (myFirstPage.hasNext()) {
            myFirstPage = fourthUserRepository.findAllUserPagesWithCountBy(myFirstPage.nextPageable());
            myFirstPage.forEach(user -> System.out.println(user.getId()));
        }
    }
}