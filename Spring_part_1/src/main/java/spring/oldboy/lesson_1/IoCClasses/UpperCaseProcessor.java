package spring.oldboy.lesson_1.IoCClasses;
/*
Класс подписанный на интерфейс TextProcessor и реализующий
его единственный метод сообразно назначению - перевод в
верхний регистр полученной строки.
*/
public class UpperCaseProcessor implements TextProcessor {
    @Override
    public String process(String input) {
        return input.toUpperCase();
    }
}
