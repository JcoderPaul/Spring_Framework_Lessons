package spring.oldboy.lesson_1.IoCClasses;
/*
Общий интерфейс для всех 'текст-процессоров', которым нужно реализовать
единственный метод, получить на вход строку и вернуть строку.
*/
public interface TextProcessor {
    String process(String input);
}
