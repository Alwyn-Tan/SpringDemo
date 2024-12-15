package org.alwyn.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

public class TestBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {

    // ************** InstantiationAwareBeanPostProcessor Implementations ******  //

    @Override
    public Object postProcessBeforeInstantiation(Class<?>clazz, String beanName) throws BeansException {
        if(beanName.equals("lifeCycleTest")){
            System.out.println("<<<<<<<<< Post Processing before Instantiation >>>>>>>>>");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(bean.equals("lifeCycleTest")){
            System.out.println("<<<<<<<<< Post Processing after Instantiation >>>>>>>>>");
            return false;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if(bean.equals("lifeCycleTest")){
            System.out.println("<<<<<<<<< Post Processing after Instantiation >>>>>>>>>");
        }
        return pvs;
    }

    //************ DestructionAwareBeanPostProcessor Implementation ************//

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if(bean.equals("lifeCycleTest")){
            System.out.println("<<<<<<<<< Post Processing before Destruction >>>>>>>>>");
        }
    }

    // ********** BeanPostProcessor Implementation ********//

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean.equals("lifeCycleTest")){
            System.out.println("<<<<<<<<< Post Processing before Initialization >>>>>>>>>");
        }
        return bean;
    }


        @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean.equals("lifeCycleTest")){
            System.out.println("<<<<<<<<< Post Processing before Destruction >>>>>>>>>");
        }
        return bean;
    }
}
