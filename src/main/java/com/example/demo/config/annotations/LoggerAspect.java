//package com.example.demo.config.annotations;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LoggerAspect {
//    @Around("@annotation(com.example.demo.config.annotations.LogThisModule)")
//    public Object executionTime(ProceedingJoinPoint jp) throws Throwable {
//        long start = System.currentTimeMillis();
//        Object proceed = jp.proceed();
//        long exTime = System.currentTimeMillis() - start;
//        System.out.println("          >>>>>>>>>>>>>>>>   " + exTime + " ms");
//        return proceed;
//    }
//}
