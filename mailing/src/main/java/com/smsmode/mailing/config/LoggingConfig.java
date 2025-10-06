package com.smsmode.mailing.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

/**
 * TODO: add your documentation
 *
 * @author hamzahabchi (contact: hamza.habchi@messaging-technologies.com)
 * <p>Copyright (C) Calade Technologies, Inc - All Rights Reserved Unauthorized copying of this
 * file, via any medium is strictly prohibited Proprietary and confidential
 * <p>Created 07 Nov 2024
 */
@Configuration
@EnableAspectJAutoProxy
public class LoggingConfig {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Aspect
    private static class LoggingAspect {

        /**
         * Pointcut that matches all repositories, services and Web REST endpoints.
         */
        @Pointcut(
                "within(@org.springframework.stereotype.Repository *)"
                        + " || within(@org.springframework.stereotype.Service *)"
                        + " || within(@org.springframework.web.bind.annotation.RestController *)")
        public void springBeanPointcut() {
            // Method is empty as this is just a Pointcut, the implementations are in the advices.
        }

        /**
         * Pointcut that matches all Spring beans in the application's main packages.
         */
        @Pointcut(
                "within(com.smsmode.mailing..repository..*)"
                        + " || within(com.smsmode.mailing..service..*)"
                        + " || within(com.smsmode.mailing..controller..*)")
        public void applicationPackagePointcut() {
            // Method is empty as this is just a Pointcut, the implementations are in the advices.
        }

        /**
         * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
         *
         * @param joinPoint join point we want the logger for.
         * @return {@link Logger} associated to the given {@link JoinPoint}.
         */
        private Logger logger(JoinPoint joinPoint) {
            return LoggerFactory.getLogger(joinPoint.getTarget().getClass().getCanonicalName());
        }

        /**
         * Advice that logs when a method is entered and exited.
         *
         * @param joinPoint join point for advice.
         * @return result.
         * @throws Throwable throws {@link IllegalArgumentException}.
         */
        @Around("applicationPackagePointcut() && springBeanPointcut()")
        public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
            Logger log = logger(joinPoint);
            log.debug(
                    "Enter: {}() with argument[s] = {}",
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
            try {
                Object result = joinPoint.proceed();
                log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
                return result;
            } catch (IllegalArgumentException e) {
                log.error(
                        "Illegal argument: {} in {}()",
                        Arrays.toString(joinPoint.getArgs()),
                        joinPoint.getSignature().getName());
                throw e;
            }
        }

        /**
         * Advice that logs methods throwing exceptions.
         *
         * @param joinPoint join point for advice.
         * @param e         exception.
         */
        @AfterThrowing(
                pointcut = "applicationPackagePointcut() && springBeanPointcut()",
                throwing = "e")
        public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = '{}' and exception = '{}'",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL",
                            e.getMessage(),
                            e);
        }
    }
}
