//package com.example.demo.config;
//
//import com.example.demo.util.Inserter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Component;
//
//import java.util.TimeZone;
//
//@Component
//public class ContextRefreshListener implements ApplicationListener<ContextRefreshedEvent> {
//
//    private final Inserter inserter;
//
//    @Autowired
//    public ContextRefreshListener(Inserter inserter) {
//        this.inserter = inserter;
//    }
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//        inserter.insertInitialData();
//        System.out.println("   >>>   tables filled");
//    }
//}
