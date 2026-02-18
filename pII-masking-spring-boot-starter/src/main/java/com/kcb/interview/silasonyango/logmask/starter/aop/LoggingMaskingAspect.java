package com.kcb.interview.silasonyango.logmask.starter.aop;

import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;


@Aspect
public class LoggingMaskingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingMaskingAspect.class);

    private final PIIMaskingService maskingService;

    public LoggingMaskingAspect(PIIMaskingService maskingService) {
        this.maskingService = maskingService;
    }

    @Before("within(@org.springframework.stereotype.Service *)")
    public void logRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        if (ObjectUtils.isEmpty(args)) {
            return;
        }

        for (Object arg : args) {
            String masked = maskingService.maskForLogging(arg);
            log.info("Service call {} arg (masked): {}", joinPoint.getSignature(), masked);
        }
    }

    @AfterReturning(pointcut = "within(@org.springframework.stereotype.Service *)", returning = "result")
    public void logResponse(JoinPoint joinPoint, Object result) {

        if (result == null) {
            return;
        }

        String masked = maskingService.maskForLogging(result);
        log.info("Service call {} result (masked): {}", joinPoint.getSignature(), masked);
    }
}

