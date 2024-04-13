package spring.oldboy.database.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import spring.oldboy.database.pool.ConnectionPool;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
public class UserRepository {
    /* Перенос данной аннотации в конструктор прописан в lombok.config */
    @Qualifier("pool2")
    private final ConnectionPool connectionPool;

}
