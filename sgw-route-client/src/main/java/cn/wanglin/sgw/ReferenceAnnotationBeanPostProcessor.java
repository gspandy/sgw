package cn.wanglin.sgw;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;

/**
 *
 */
public class ReferenceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
        implements InstantiationAwareBeanPostProcessor, PriorityOrdered, ApplicationContextAware, BeanClassLoaderAware, DisposableBean {
    private ApplicationContext applicationContext;
    private ClassLoader        classLoader;

    private ConcurrentMap<Class, ChannelServiceBean<?>> channelServiceBeansCache = new ConcurrentHashMap<>();


    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public PropertyValues postProcessPropertyValues(
            PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeanCreationException {

        InjectionMetadata metadata = buildChannelServiceMetadata(bean.getClass());
        try {
            metadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of @Reference dependencies failed", ex);
        }
        return pvs;
    }


    private InjectionMetadata buildChannelServiceMetadata(Class<?> beanType) {
        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<>();

        elements.addAll(findFieldChannelServiceMetadata(beanType));
        return new InjectionMetadata(beanType, elements);
    }

    private Collection<? extends InjectionMetadata.InjectedElement> findFieldChannelServiceMetadata(Class<?> beanType) {
        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<>();

        ReflectionUtils.doWithFields(beanType, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {

                Reference reference = getAnnotation(field, Reference.class);

                if (reference != null) {
                    elements.add(new ChannelServiceFieldElement(field));
                }

            }
        });

        return elements;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    class ChannelServiceFieldElement extends InjectionMetadata.InjectedElement {
        Field field;

        protected ChannelServiceFieldElement(Field field) {
            super(field, null);
            this.field = field;
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
            Class<?> referenceClass = field.getType();
            Object   referenceBean  = buildChannelServiceBean(referenceClass);
            ReflectionUtils.makeAccessible(field);
            field.set(bean, referenceBean);
        }
    }

    private Object buildChannelServiceBean(Class<?> referenceClass) throws Exception {
        ChannelServiceBean channelServiceBean = channelServiceBeansCache.get(referenceClass);

        if (channelServiceBean == null) {
            channelServiceBean = new ChannelServiceBean(referenceClass,classLoader);
            channelServiceBeansCache.putIfAbsent(referenceClass, channelServiceBean);
        }
        return channelServiceBean.getObject();
    }
}
