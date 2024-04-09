package spring.oldboy.repository;

import spring.oldboy.bean_post_processor.Auditing;
import spring.oldboy.bean_post_processor.InjectBean;
import spring.oldboy.bean_post_processor.MyOwnTransaction;
import spring.oldboy.entity.Company;
import spring.oldboy.pool.StarterConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Optional;

@MyOwnTransaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    /*
    Мы сделаем конструктор без параметров, поле для внедрения пометим
    нашей аннотацией и обработаем ее нашим же самописным пост-процессором,
    который в случае нахождения аннотации @InjectBean обработает ее и
    найдет нужный StarterConnectionPool bean в контейнере и внедрит в наше
    поле.

    Более подробно о рефлексии и самописных аннотациях см.:
    https://github.com/JcoderPaul/JavaExtended-35-38
    */

    @InjectBean
    private StarterConnectionPool starterConnectionPool;

    @PostConstruct
    private void init() {
        System.out.println("Init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method... from CompanyRepository");
        /*
        Объект Optional можно создать с помощью статического метода *.of().
        Данный метод должен вернуть Company объект, который мы передаем в
        метод of(), и он должен быть not null, иначе, получим исключение:
        NullPointerException.

        Т.е. тут мы эмулируем обращение к БД и гарантированно возвращаем
        объект Company с ID = 1. Своего рода заглушка.
        */
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...from CompanyRepository");
    }
}