package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.TimeZone;

@SpringBootApplication
public class DemoApplication {
//todo flyway
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
