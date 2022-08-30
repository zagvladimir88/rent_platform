package com.zagvladimir.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class CustomAspect {
    private static final Logger log = Logger.getLogger(CustomAspect.class);

    @Pointcut("execution(* com.zagvladimir.service.*.*(..))")
    public void aroundServicePointcut() {
    }

    @Around("aroundServicePointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stwatch = new StopWatch(getClass().getSimpleName());
        System.out.println(joinPoint.getArgs().length);
        log.info("Method " + joinPoint.getSignature().getName() + " in " + joinPoint.getSignature().getDeclaringTypeName() + " start");

        stwatch.start(joinPoint.getSignature().getName());
        Object proceed = joinPoint.proceed();

        stwatch.stop();
        log.info("Method " + joinPoint.getSignature().getName() + " finished\n" +
                stwatch.prettyPrint());
        return proceed;
    }
}