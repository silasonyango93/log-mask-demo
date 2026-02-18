package com.kcb.interview.silasonyango.logmask.starter.aop;

import com.kcb.interview.silasonyango.logmask.starter.core.PIIMaskingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

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
      return maskingService.maskForLogging(args[0]);
    }

    return pjp.proceed();
  }
}

