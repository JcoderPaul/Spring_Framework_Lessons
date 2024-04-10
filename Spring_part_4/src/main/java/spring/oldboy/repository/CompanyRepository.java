package spring.oldboy.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import spring.oldboy.bean_post_processor.Auditing;
import spring.oldboy.bean_post_processor.MyOwnTransaction;
import spring.oldboy.entity.Company;
import spring.oldboy.pool.StarterConnectionPool;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/*
Как уже писалось в ReadMe.md аннотации: @Component, @Controller, @Repository,
@Service однотипны и разделены логически. Наш класс CompanyRepository, как ни
странно относится к слою репозитариев, вот мы его и помечаем соответствующей
аннотацией.
*/
@Repository("companyRep")
@MyOwnTransaction
@Auditing
public class CompanyRepository implements CrudRepository<Integer, Company> {

    /*
    Название поля 'pool1' полностью совпадает с параметром аннотации
    @Component("pool1") самого StarterConnectionPool-а, это позволит
    без лишнего аннотирования работать нашим связным bean-ам. И снова
    у нас неизменяемый объект (singleton bean)
    */
    @Qualifier("pool1")
    private final StarterConnectionPool pool1;
    private final List<StarterConnectionPool> pools;
    private final Integer poolSize;

    public CompanyRepository(StarterConnectionPool pool1,
                             List<StarterConnectionPool> pools,
                             @Value("${db.pool.size}") Integer poolSize) {
        this.pool1 = pool1;
        this.pools = pools;
        this.poolSize = poolSize;
    }

    @PostConstruct
    private void init() {
        System.out.println("init company repository");
    }

    @Override
    public Optional<Company> findById(Integer id) {
        System.out.println("findById method...");
        return Optional.of(new Company(id));
    }

    @Override
    public void delete(Company entity) {
        System.out.println("delete method...");
    }
}