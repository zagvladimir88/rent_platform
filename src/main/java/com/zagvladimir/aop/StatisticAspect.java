package com.zagvladimir.aop;


import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class StatisticAspect {

    private Map<Class<?>, Integer> counter = new HashMap<>();

    @Pointcut("execution(* com.zagvladimir.service.*.*(..))")
    private void logCountsNumberOfMethodsCall() {}

    @AfterReturning("logCountsNumberOfMethodsCall()")
    public void count(JoinPoint jp) {
        Class<?> clazz = jp.getTarget().getClass();
        counter.merge(clazz, 1, Integer::sum);
    }
    public Map<Class<?>, Integer> getCounter() {
        return Collections.unmodifiableMap(counter);
    }

    @AfterReturning("execution(* com.zagvladimir.service.*.*(..))")
    public void outputLoggingCounter() {
        System.out.println("Loggers statistics. Number of calls: ");
        for (Map.Entry<Class<?>, Integer> entry : counter.entrySet()) {
            System.out.println("    " + entry.getKey().getSimpleName() + ": " + entry.getValue());
        }
    }
}
