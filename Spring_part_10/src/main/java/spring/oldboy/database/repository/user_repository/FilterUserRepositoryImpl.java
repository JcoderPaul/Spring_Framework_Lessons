package spring.oldboy.database.repository.user_repository;

/* Lesson 57 - создаем класс реализующий наш кастомный интерфейс FilterUserRepository.java */

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import spring.oldboy.database.entity.User;
import spring.oldboy.dto.UserFilterDto;

import java.util.ArrayList;
import java.util.List;

/*
Название класса должно совпадать с названием реализуемого интерфейса с
постфиксом Impl - это очень важно, только так его нужно именовать
(либо лезть в настройки Spring-a и курочить его под свои нужды).
*/
@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository {
    /* Нам нужен EntityManager и он есть в SpringContext */
    private final EntityManager entityManager;
    /* Наш метод должен вернуть список User, подпадающих под фильтр */
    @Override
    public List<User> findAllByFilter(UserFilterDto filter) {
        /*
        Все что связано с созданием запросов с Criteria API можно посмотреть в
        https://github.com/JcoderPaul/Hibernate_Lessons/tree/master/Hibernate_part_5
        */
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);

        Root<User> user = criteria.from(User.class);
        criteria.select(user);

        /* Нам нужен Predicate из *.persistence.criteria */
        List<Predicate> predicates = new ArrayList<>();
        /*
        Создаем фильтры применив классический like и lessThan см. пример с комментариями к методам и логике запроса:
        https://github.com/JcoderPaul/Hibernate_Lessons/blob/master/Hibernate_part_5/src/main/java/oldboy/dao/UserDaoWithCriteriaAPI.java
        */
        if (filter.firstname() != null) {
            predicates.add(cb.like(user.get("firstname"), filter.firstname()));
        }
        if (filter.lastname() != null) {
            predicates.add(cb.like(user.get("lastname"), filter.lastname()));
        }
        if (filter.birthDate() != null) {
            predicates.add(cb.lessThan(user.get("birthDate"), filter.birthDate()));
        }
        criteria.where(predicates.toArray(value -> new Predicate[value]));

        return entityManager.createQuery(criteria).getResultList();
    }
}
