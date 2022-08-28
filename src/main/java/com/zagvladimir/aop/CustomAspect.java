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
private static int count;
    private static final Logger log = Logger.getLogger(CustomAspect.class);

//    @Before("aroundRepositoryPointcut()")
//    public void logBefore(JoinPoint joinPoint) {
//        log.info("Method " + joinPoint.getSignature().getName() + " start");
//    }
//
//    @AfterReturning(pointcut = "aroundRepositoryPointcut()")
//    public void doAccessCheck(JoinPoint joinPoint) {
//        log.info("Method " + joinPoint.getSignature().getName() + " finished");
//    }

    @Pointcut("execution(* com.zagvladimir.repository.jdbctemplate.JdbcTemplateUserRepository.*(..))")
    public void aroundRepositoryPointcut() {
    }


    @Around("aroundRepositoryPointcut()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stwatch = new StopWatch(getClass().getSimpleName());
        System.out.println(joinPoint.getArgs().length);
        log.info("Method " + joinPoint.getSignature().getName() + " start");

        stwatch.start(joinPoint.getSignature().getName());
        Object proceed = joinPoint.proceed();
        count++;
        stwatch.stop();
        log.info("Method " + joinPoint.getSignature().getName() + " finished\n" +
                stwatch.prettyPrint()+ " " + count);
        return proceed;
    }



//    @Around("aroundRepositoryPointcut()")
//    public Object countCallAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
//        log.info("Method " + joinPoint.getSignature().getName() + " start");
//        Object proceed = joinPoint.proceed();
//
//        log.info("Method " + joinPoint.getSignature().getName() + " finished");
//        return proceed;
//    }

}