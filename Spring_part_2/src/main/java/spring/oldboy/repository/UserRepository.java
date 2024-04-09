package spring.oldboy.repository;

/* Нам нужен пустой конструктор для создания bean-a из текущего класса */

import spring.oldboy.bean_post_processor.InjectBean;
import spring.oldboy.pool.StarterConnectionPool;

public class UserRepository {

    @InjectBean
    private StarterConnectionPool starterConnectionPool;

}