package spring.oldboy.repository;

import spring.oldboy.bean_post_processor.DropBeanBeforeInit;
import spring.oldboy.bean_post_processor.InjectBean;
import spring.oldboy.entity.Firm;
import spring.oldboy.pool.StarterConnectionPool;

import javax.annotation.PostConstruct;
import java.util.Optional;

@DropBeanBeforeInit
public class FirmRepository implements CrudRepository<Integer, Firm>{

    @InjectBean
    private StarterConnectionPool starterConnectionPool;

    @PostConstruct
    private void init() {
        System.out.println("Init company repository");
    }

    @Override
    public Optional<Firm> findById(Integer id) {
        System.out.println("findById method... from CompanyRepository");
        /*
        Объект Optional можно создать с помощью статического метода *.of().
        Данный метод должен вернуть Company объект, который мы передаем в
        метод of(), и он должен быть not null, иначе, получим исключение:
        NullPointerException.

        Т.е. тут мы эмулируем обращение к БД и гарантированно возвращаем
        объект Company с ID = 1. Своего рода заглушка.
        */
        return Optional.of(new Firm(id));
    }

    @Override
    public void delete(Firm entity) {
        System.out.println("delete method...from CompanyRepository");
    }
}
