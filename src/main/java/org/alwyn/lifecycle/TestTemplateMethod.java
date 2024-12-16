package org.alwyn.lifecycle;

import java.util.ArrayList;
import java.util.List;

public class TestTemplateMethod {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public void inject(Object bean) {
                System.out.println("Autowire...");
            }
        });
        beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public void inject(Object bean) {
                System.out.println("Resource...");
            }
        });
        beanFactory.getBean();
    }

    static class MyBeanFactory{
        public Object getBean(){
            Object bean = new Object();
            System.out.println("Construction");
            System.out.println("Dependency Injection"); //Autowire
            for (BeanPostProcessor processor : processors){
                processor.inject(bean);
            }
            System.out.println("Initialization");
            return bean;
        }

        private List<BeanPostProcessor> processors = new ArrayList<>();
        private void addBeanPostProcessor(BeanPostProcessor processor){
            processors.add(processor);
        }
    }

    interface BeanPostProcessor{
        void inject(Object bean);
    }
}
