package com.kcb.interview.silasonyango.logmask.starter.aop;

import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple AOP aspect that logs method arguments and return values using {@link PIIMaskingService}.
 * It does not change the actual objects or the real logging configuration, but demonstrates
 * how an application can get masked values in logs.
 */
@Aspect
public class LoggingMaskingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingMaskingAspect.class);

    private final PIIMaskingService maskingService;

    public LoggingMaskingAspect(PIIMaskingService maskingService) {
        this.maskingService = maskingService;
    }

    @Before("within(@org.springframework.web.bind.annotation.RestController *)")
    public void logRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        for (Object arg : args) {
            String masked = maskingService.maskForLogging(arg);
            log.info("Request arg (masked): {}", masked);
        }
    }

    @AfterReturning(pointcut = "within(@org.springframework.web.bind.annotation.RestController *)", returning = "result")
    public void logResponse(Object result) {
        if (result == null) {
            return;
        }
        String masked = maskingService.maskForLogging(result);
        log.info("Response (masked): {}", masked);
    }
}

