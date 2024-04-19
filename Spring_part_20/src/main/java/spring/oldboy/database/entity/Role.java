package spring.oldboy.database.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN;

    /* Lesson 102: Добавляем реализацию метода GrantedAuthority */
    @Override
    public String getAuthority() {
        return name();
    }
}
