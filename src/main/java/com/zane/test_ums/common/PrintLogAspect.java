package com.zane.test_ums.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Zanezeng
 */
@Slf4j
//@Aspect
//@Component
public class PrintLogAspect {

    /**
     * 定义切入点：切入点为controller包下所有类的所有方法
     */
    @Pointcut("execution(* com.zane.test_ums.controller.*.*(..))")
    public void controllerLog() {
        // 定义切入点
    }

    @Before("controllerLog()")
    public void logParam(JoinPoint joinPoint) {
        log.info("Start to execute method: {}, method parameter: {}",
                joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "controllerLog()", returning = "retVal")
    public void logResult(JoinPoint joinPoint, Object retVal) {
        log.info("Finish to execute method: {}, method return: {}",
                joinPoint.getSignature().toShortString(), retVal.toString());
    }

    @AfterThrowing(pointcut = "controllerLog()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.info("Throw exception method: {}, exception message: {}",
                joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}
