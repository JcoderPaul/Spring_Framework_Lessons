package spring.oldboy.bean_post_processor;

/* Пример самописного пост-процессора с основным ...AfterInitialization методом */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyOwnTransactionBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Class<?>> transactionBeans = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
                                                                    throws BeansException {
        if (bean.getClass().isAnnotationPresent(MyOwnTransaction.class)) {
            transactionBeans.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
                                                                    throws BeansException {
        Class<?> beanClass = transactionBeans.get(beanName);
        if (beanClass != null) {
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
