package com.shop.aop.aspect;

import com.shop.pojo.test.Hello;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestServiceAspect  {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceAspect.class);
    @Before("execution(* com.shop.controller.HelloTestController.hello (java.lang.String)) && args(sampleName)")
    public void beforeSampleCreation(String sampleName) {
        LOGGER.info("A request was issued for a sample name: " + sampleName);
    }

    @Around("execution(* com.shop.controller.HelloTestController.hello (java.lang.String)) && args(sampleName)")
    public Object aroundSampleCreation(ProceedingJoinPoint proceedingJoinPoint, String sampleName) throws Throwable {
        LOGGER.info("A request was issued for a sample name: " + sampleName);
        sampleName = sampleName + "!";
        Hello sample = (Hello) proceedingJoinPoint.proceed(new Object[] {sampleName});
        sample.setName(sample.getName().toUpperCase());
        return sample;
    }

}