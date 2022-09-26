package com.zagvladimir.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingServiceAspect {


    @Pointcut("execution(* com.zagvladimir.service.*.*(..))")
    public void aroundServicePointcut() {
    }

    @Around("aroundServicePointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Method {} in {} start" ,joinPoint.getSignature().getName(), joinPoint.getSignature().getDeclaringTypeName());
        Object proceed = joinPoint.proceed();
        log.info("Method {} finished", joinPoint.getSignature().getName());
        return proceed;
    }
}