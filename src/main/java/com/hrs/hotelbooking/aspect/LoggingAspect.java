package com.hrs.hotelbooking.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.hrs.hotelbooking.controller.*.*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        
        // Get request details
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        // Log request
        logger.info("Request: {} {} - Method: {}.{} - Args: {}", 
            request.getMethod(),
            request.getRequestURI(),
            className,
            methodName,
            Arrays.toString(joinPoint.getArgs())
        );

        try {
            // Execute the method
            Object result = joinPoint.proceed();
            
            // Calculate execution time
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            // Log response
            logger.info("Response: {} {} - Method: {}.{} - Execution Time: {}ms - Response: {}", 
                request.getMethod(),
                request.getRequestURI(),
                className,
                methodName,
                executionTime,
                result
            );
            
            return result;
        } catch (Exception e) {
            // Log error
            logger.error("Error in {} {} - Method: {}.{} - Error: {}", 
                request.getMethod(),
                request.getRequestURI(),
                className,
                methodName,
                e.getMessage(),
                e
            );
            throw e;
        }
    }
} 