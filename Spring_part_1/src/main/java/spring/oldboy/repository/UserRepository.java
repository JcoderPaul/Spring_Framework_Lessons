package spring.oldboy.repository;

/*
Как и в UserRepository в данном классе, функционально
расширяющий объект поступает извне. Т.е. данный класс
не знает, как соединиться с базой, однако используя
возможности ConnectionPool делает это без проблем.
*/

import spring.oldboy.pool.StarterConnectionPool;

public class UserRepository {

    private final StarterConnectionPool starterConnectionPool;

    public UserRepository(StarterConnectionPool starterConnectionPool) {
        this.starterConnectionPool = starterConnectionPool;
    }
}