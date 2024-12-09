package org.alwyn.beanfactory;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    BeanFactory, as a basic Spring bean container, will not things automatically:
    <1> Invoke BeanFactoryPostProcessor
    <2> Invoke BeanPostProcessor
    <3> Pre-instantiate singletons
 */

public class BeanFactoryTest {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //generic bean definition and register manually
        BeanDefinition ConfigBeanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).getBeanDefinition();
        beanFactory.registerBeanDefinition("config", ConfigBeanDefinition);

        for (String beanDefinitionName: beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        System.out.println("After registerAnnotationConfigProcessors, some processor bean definitions are added:");
        for (String beanDefinitionName: beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        System.out.println("Post process bean factory:");
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(
                beanFactoryPostProcessor -> {
                    beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
                }
        );
        for (String beanDefinitionName: beanFactory.getBeanDefinitionNames()){
            System.out.println(beanDefinitionName);
        }

        //Test bean post processor
        //System.out.println("Before bean post processor: @Autowired annotation is not processed:");
        //System.out.println(beanFactory.getBean(Bean1.class).getBean2());
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        System.out.println("After bean post processor:");
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());

    }

    @Configuration
    static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(){
            return new Bean2();
        }
    }

    static class Bean1{
        public Bean1(){
            System.out.println("Bean1 constructed");
        }

        @Autowired
        private Bean2 bean2;

        public Bean2 getBean2(){return bean2;}
    }

    static class Bean2{
        public Bean2(){
            System.out.println("Bean2 constructed");
        }

    }

}
