package com.example.demo;

import com.example.demo.util.Inserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.TimeZone;

@SpringBootApplication
public class DemoApplication implements ApplicationListener<ContextRefreshedEvent> {

    private final Inserter inserter;

    @Autowired
    public DemoApplication(Inserter inserter) {
        this.inserter = inserter;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        inserter.insertInitialData();
    }
}
