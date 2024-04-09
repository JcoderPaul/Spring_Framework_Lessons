package spring.oldboy.bean_factory_pp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

@Component
public class VerifyPropertyBeanFactoryPostProcessor implements BeanFactoryPostProcessor,
                                                                        PriorityOrdered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws
                                                                                  BeansException {
        System.out.println("2.postProcessBeanFactory - VerifyPropertyBeanFactoryPostProcessor");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
