package spring.oldboy.lesson_1;
/*
Несложно заметить, что наша фабрика - это ассоциативный массив,
значит, теоретически, да и практически, там могут находиться любые
объекты.
*/
import spring.oldboy.lesson_1.IoCClasses.IOCBasedTextProcessorFactory;
import spring.oldboy.lesson_1.IoCClasses.TextProcessor;
import spring.oldboy.lesson_1.IoCClasses.TextProcessorFactory;

public class IoCDemo {

        public static void main(String[] args) {
            /* Создаем фабрику */
            final TextProcessorFactory factory = new IOCBasedTextProcessorFactory();
            /* Получаем со 'склада фабрики' нужный инструментарий */
            final TextProcessor lowerProcessor = factory.getProcessor("lower");
            final TextProcessor upperProcessor = factory.getProcessor("upper");
            /* Применяем его в работе */
            System.out.println(lowerProcessor.process("HELLO"));
            System.out.println(upperProcessor.process("hello"));
        }
}


