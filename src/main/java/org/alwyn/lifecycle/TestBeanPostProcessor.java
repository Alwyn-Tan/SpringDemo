package org.alwyn.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(TestBeanPostProcessor.class);
    // ************** InstantiationAwareBeanPostProcessor Implementations ******  //

    @Override
    public Object postProcessBeforeInstantiation(Class<?>clazz, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            log.debug("<<<<<<<<< Post Processing before Instantiation >>>>>>>>>");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            log.debug("<<<<<<<<< Post Processing after Instantiation - false for skipping post-processing properties >>>>>>>>>");
            return true;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            log.debug("<<<<<<<<< Post Processing Properties - Dependency Injection for @Autowire, @Value, @Resource etc. >>>>>>>>>");
        }
        return pvs;
    }

    // ********** BeanPostProcessor Implementation ********//

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            log.debug("<<<<<<<<< Post Processing before Initialization - The returned object will replace original bean, for @PostConstruct, @Configuration >>>>>>>>>");
        }
        return bean;
    }


        @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            log.debug("<<<<<<<<< Post Processing after Initialization - for Proxy Enhance >>>>>>>>>");
        }
        return null;
    }

    //************ DestructionAwareBeanPostProcessor Implementation ************//

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleBean")){
            log.debug("<<<<<<<<< Post Processing before Destruction - for @PreDestroy >>>>>>>>>");
        }
    }
}
