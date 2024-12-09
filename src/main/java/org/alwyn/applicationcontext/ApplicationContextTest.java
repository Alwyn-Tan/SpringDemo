package org.alwyn.applicationcontext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ApplicationContextTest {
    public static void main(String[] args) {
        //testClassPathXmlApplicationContext();
        //testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebServerApplicationContext();
    }


    private static void testClassPathXmlApplicationContext(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        for(String name : context.getBeanDefinitionNames()){
            System.out.println(name);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    private static void testAnnotationConfigApplicationContext(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for(String name : context.getBeanDefinitionNames()){
            System.out.println(name);
        }
    }

    private static void testAnnotationConfigServletWebServerApplicationContext(){
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(ServletConfig.class);
        for(String name : context.getBeanDefinitionNames()){
            System.out.println(name);
        }
    }

    @Configuration
     static class Config{
        @Bean
        public Bean1 bean1(){
            return new Bean1();
        }

        @Bean
        public Bean2 bean2(){
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1());
            return bean2;
        }
    }

    static class Bean1{}

    static class Bean2{
        private Bean1 bean1;

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }

        public Bean1 getBean1() {
            return bean1;
        }
    }

    @Configuration
    static class ServletConfig{
        @Bean
        public ServletWebServerFactory servletWebServerFactory(){
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServlet(){
            return new DispatcherServlet();
        }

        @Bean
        public DispatcherServletRegistrationBean registrationBean(DispatcherServlet dispatcherServlet){
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        @Bean("/hello")
        public Controller controller(){
            return new Controller() {
                @Override
                public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
                    response.getWriter().println("hello");
                    return null;
                }
            };
        }
    }
}

