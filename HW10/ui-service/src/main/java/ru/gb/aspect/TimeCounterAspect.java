package ru.gb.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeCounterAspect {

    @Pointcut("within(@ru.gb.aspect.TimeCounter *)")
    public void beansAnnotatedWith() {
    }

    @Pointcut("@annotation(ru.gb.aspect.TimeCounter)")
    public void methodsAnnotatedWith() {
    }

    @Around("beansAnnotatedWith() || methodsAnnotatedWith()")
    public Object timeCounter (ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Long startTime = System.currentTimeMillis();
            Object returnValue = joinPoint.proceed();
            Long endTime = System.currentTimeMillis();
            log.info("className: {}, - methodName: {}, #({} seconds)", joinPoint.getTarget().getClass(), joinPoint.getSignature(), ((endTime - startTime)/1000));
            return returnValue;
        } catch (Throwable e) {
            log.info("Error = {}", joinPoint.getTarget().getClass());
            throw e;
        }
    }
}
