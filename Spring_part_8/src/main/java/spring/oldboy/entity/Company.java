package spring.oldboy.entity;

/*
При появлении record, нам уже не нужно использовать Lombok
т.к. класс record уже несет в себе массу невидимого рутинного
кода.
*/
public record Company(Integer id) {
}
