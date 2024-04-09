package spring.oldboy.repository;

import org.springframework.stereotype.Repository;
import spring.oldboy.pool.StarterConnectionPool;

@Repository
public class UserRepository {

    private final StarterConnectionPool connectionPool;

    public UserRepository(StarterConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
}
