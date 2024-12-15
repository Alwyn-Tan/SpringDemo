package org.alwyn.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleBean {
    private static final Logger log = LoggerFactory.getLogger(LifeCycleBean.class);

    public LifeCycleBean() {
        System.out.println("Constructing...");
    }

    @Autowired
    public void autowire(@Value("${JAVA_HOME}")String home){
       System.out.println("Autowiring:"+ home);
    }

    @PostConstruct
    public void init(){System.out.println("Initializing...");}

    @PreDestroy
    public void destroy(){System.out.println("Destroying");}
}

