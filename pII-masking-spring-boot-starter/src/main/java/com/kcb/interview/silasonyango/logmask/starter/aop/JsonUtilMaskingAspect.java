package com.kcb.interview.silasonyango.logmask.starter.aop;

import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect that intercepts calls to JsonUtil.toJsonString(..) and returns a masked JSON
 * representation instead of the raw JSON. This keeps all existing logging code intact
 * while ensuring sensitive fields are not written to logs.
 */
@Aspect
public class JsonUtilMaskingAspect {

    private final PIIMaskingService maskingService;

    public JsonUtilMaskingAspect(PIIMaskingService maskingService) {
        this.maskingService = maskingService;
    }

    @Around("execution(* com.kcb.interview.silasonyango.books.util.JsonUtil.toJsonString(..))")
    public Object maskJsonLogging(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        if (args.length == 1 && args[0] != null) {
            // Use masking service to produce masked JSON string for the argument
            return maskingService.maskForLogging(args[0]);
        }

        // Fallback to original behavior if something unexpected happens
        return pjp.proceed();
    }
}

