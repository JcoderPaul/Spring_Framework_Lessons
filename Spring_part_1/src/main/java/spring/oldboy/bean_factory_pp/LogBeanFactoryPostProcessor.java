package spring.oldboy.bean_factory_pp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

public class LogBeanFactoryPostProcessor implements BeanFactoryPostProcessor, PriorityOrdered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws
                                                                                 BeansException {
        System.out.println("1.postProcessBeanFactory - LogBeanFactoryPostProcessor");
    }
    /* Влияем на порядок инициализации (запуска) наших экземпляров BeanFactoryPostProcessor bean-ов */
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
