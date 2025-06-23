package com.example.bankcards.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Аспект для логирования выполнения сервисных и контроллерных Spring для сервисов и контроллеров.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.example.bankcards.controller..*) || within(com.example.bankcards.service..*)")
    public void applicationPackagePointcut() {
        // поинткаты для пакетов приложения
    }

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Entering {} with arguments {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exiting {} with result {}", joinPoint.getSignature(), result);
            }
            return result;
        } catch (Throwable ex) {
            log.error("Exception in {} with arguments {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()), ex);
            throw ex;
        }
    }
}