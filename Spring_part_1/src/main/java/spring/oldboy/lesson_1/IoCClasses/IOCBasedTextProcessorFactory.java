package spring.oldboy.lesson_1.IoCClasses;
/* Фабрика 'текст-процессоров' */
import java.util.HashMap;
import java.util.Map;

public class IOCBasedTextProcessorFactory implements TextProcessorFactory {
    /* 'Склад' наших 'текст-процессоров', аналог контекста */
    private final Map<String, TextProcessor> processors;
    /* Фабрика по производству 'текст-процессоров' и отправки их на 'склад' */
    public IOCBasedTextProcessorFactory() {
        processors = new HashMap<>();
        /*
        'Загрузка на склад' по типу 'текст-процессора':
        key - type, value - function of text processor
        */
        processors.put("lower", new LowerCaseProcessor());
        processors.put("upper", new UpperCaseProcessor());
    }
    /* 'Кладовщик' выдающий 'текст-процессоры' под нужны потребителя */
    @Override
    public TextProcessor getProcessor(String type) {
        return processors.get(type);
    }
}
