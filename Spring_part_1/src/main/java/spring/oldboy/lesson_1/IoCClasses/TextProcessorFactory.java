package spring.oldboy.lesson_1.IoCClasses;
/* Интерфейс фабрики 'текст-процессоров' */
public interface TextProcessorFactory {
    TextProcessor getProcessor(String type);
}
