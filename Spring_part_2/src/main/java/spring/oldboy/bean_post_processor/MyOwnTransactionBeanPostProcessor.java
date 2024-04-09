package spring.oldboy.bean_post_processor;

/* Пример самописного пост-процессора с основным ...AfterInitialization методом */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MyOwnTransactionBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> transactionBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
                                                                    throws BeansException {
        /*
        Исследуя работу DropBeanBeforeInitialisationPostProcessor.java мы выяснили, что нежелательно
        использовать Proxy на этапе пред-инициализации, и лучше перенести подобный функционал на этап
        пост-инициализации bean-a см. DOC/Context/Bean_LifeCycle_With_PostProcessors.jpg

        И снова возникает тонкий момент (для реализации текущих методов данного класса):

        - внедрение proxy, подмена одних bean-ов другими, использование оберток и т.п. не принято
          использовать в методах *.postProcessBeforeInitialization(), но это не закон, а лишь правило;
          значит его можно нарушить и на вход метода *.postProcessAfterInitialization() может легко
          прилететь не тот bean, что прилетел на вход *.postProcessBeforeInitialization(); а нам точно
          нужно знать, 'структуру первоначального чертежа' - что за bean был на стадии пред-инициализации;
        - поскольку пост-процессоры тоже bean-ы, пусть и особого порядка, они также располагаются в
          случайном порядке и значит может возникнуть ситуация когда метод *.postProcessAfterInitialization()
          одного пост-процессора вернет Proxy и он прилетит на вход такого же метода другого пост-процессора,
          и получится ситуация описанная выше;

        Поэтому, нам нужен код, который сохранит исходную мета-информацию о нашем bean-е. Мы это реализуем в
        методе *.postProcessBeforeInitialization(). Получая на вход исходный bean мы сохраним его в коллекцию,
        где его имя будет ключом, а класс данными.

        Если bean содержит аннотацию @MyOwnTransaction сохраняем его в коллекцию.
        */
        if (bean.getClass().isAnnotationPresent(MyOwnTransaction.class)) {
            transactionBeans.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
                                                                    throws BeansException {
        /* Ну, а тут мы просто извлечем нужную информацию из коллекции по ключу */
        Class<?> beanClass = transactionBeans.get(beanName);
        if (beanClass != null) {
            /*
            Мы пытаемся внести некую сквозную логику для всех bean-ов, помеченных нашей аннотацией
            @MyOwnTransaction и делаем это через Proxy. Даже если 'на этапе чертежа' bean и не имел
            возможности открыть транзакцию, проделать некую операцию и закрыть транзакцию, то на
            этапе пост-инициализации мы придали ему такую способность.

            Существует два варианта создания proxy это динамический, через реализацию интерфейсов
            и тут нам дополнительные библиотеки не понадобятся. Второй вариант создания proxy -
            через наследование (для этого понадобится подключение доп. библиотек ByteBuddy, CgLib,
            JavaAssist).

            Hibernate и Spring используют proxy через наследование.

            При этом, мы можем оборачивать одни прокси в другие и т.д. чем можем очень серьезно расширить
            функционал простого bean-a. И это возможно простым использованием уже готовых аннотаций, так
            и написанием и конфигурированием своих аннотаций и пост-процессоров.
            */
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        /* Эмулируем открытие транзакции */
                        System.out.println("Open transaction");
                        try {
                            return method.invoke(bean, args);
                        } finally {
                        /* Эмулируем закрытие транзакции */
                            System.out.println("Close transaction");
                        }
                    });
        }
        return bean;
    }
}
