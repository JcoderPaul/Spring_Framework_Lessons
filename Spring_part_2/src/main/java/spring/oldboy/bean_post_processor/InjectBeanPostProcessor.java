package spring.oldboy.bean_post_processor;

/* Пример самописного пост-процессора с основным ...BeforeInitialization методом */

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

/*
Чтобы наш класс стал пост-процессором он должен имплементировать интерфейс BeanPostProcessor,
и естественно инициализируем его (пока) через resources/application.xml. Далее нам нужно
получить контекст, это можно сделать через интерфейс ApplicationContextAware, а точнее через
пост-процессор, который обрабатывает все Aware интерфейсы.
*/

public class InjectBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    /*
    Необходимая для работы нашего пост-процессора зависимость,
    которую мы получим см. ниже., необходимо сохранить, что мы
    и делаем, т.е. внедряем через сеттер *.setApplicationContext().
    */
    private ApplicationContext applicationContext;

    /* Переопределяем метод */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
                                                                    throws BeansException {
        /* При помощи Reflection API получаем все поля (Fields) у нашего bean-a */
        Arrays.stream(bean.getClass().getDeclaredFields())
                /* Далее мы находим только те поля которые используют нашу самописную аннотацию */
                .filter(field -> field.isAnnotationPresent(InjectBean.class))
                /*
                И если мы нашли хотя бы одно поле (а тем более поля),
                то мы должны проинициализировать их (перебрать в цикле).
                */
                .forEach(field -> {
                    /*
                    Однако, наш самописный пост-процессор должен знать о существовании контекста и иметь
                    возможность достать bean-ы требующиеся для внедрения в аннотированные, опять же нашей
                    аннотацией @InjectBean поля.

                    И вот тут нам поможет интерфейс ApplicationContextAware и его переопределенный метод
                    *.setApplicationContext(). Как только мы получили доступ к контексту, мы попробуем
                    получить тип аннотированного @InjectBean поля (т.е. определить type bean-a для
                    внедрения - beanToInject).

                    Поскольку в нашем application.xml как минимум 3-и StarterConnectionPool bean-a, зададим
                    конкретное имя - "pool2", чтобы не путать Spring.
                    */
                    Object beanToInject = applicationContext.getBean("pool2",field.getType());
                    /* Если вдруг интересующее нас поле private, нам понадобится доступ к нему */
                    ReflectionUtils.makeAccessible(field);
                    /*
                    После получения доступа к полю мы можем внедрить зависимости через сеттер,
                    куда передаем:
                        - field - поле, в которое будем внедрять зависимость;
                        - bean - куда будет внедряться зависимость;
                        - beanToInject - bean, который будет внедряться в аннотированное,
                                         нашей самописной @InjectBean, поле;
                    */
                    ReflectionUtils.setField(field, bean, beanToInject);
                    /*
                    Чтобы не обрабатывать возможные исключения, которые в основном не Runtime,
                    используем ReflectionUtils и его методы для доступа к полю и внедрению
                    нужного bean-a (*.makeAccessible() и *.setField()).

                    Для понимания происходящего можно просто заглянуть внутрь метода:

                    ****************************************************************************
                            public static void setField(Field field,
                                                        @Nullable Object target,
                                                        @Nullable Object value) {
                                try {
                                    field.set(target, value);
                                }
                                catch (IllegalAccessException ex) {
                                    handleReflectionException(ex);
                                }
                            }
                    ****************************************************************************

                    И увидеть, что тут так же поле задается через сеттер и сразу ловится
                    исключение - чуток разгрузили наш код.
                    */
                });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    /*
    И тут начинается магия Spring-a (пока, наш пост-процессор прописан в application.xml).

    Когда ApplicationContext будет инициализировать наш InjectBeanPostProcessor,
    он воспользуется другим BeanPostProcessor-ом, который занимается всеми
    AwarePostProcessor-ами, и внедрит нужную нам зависимость.

    Мы получим доступ к текущему ApplicationContext-у.
    */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
                                                                    throws BeansException {
        this.applicationContext = applicationContext;
    }


}
