package com.tuna.blog.infrastructure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@AllArgsConstructor
@Configuration
public class PostCommitAspect {
    private final PostCommitAdapter postCommitAdapter;

    @Pointcut("@annotation(com.tuna.blog.infrastructure.PostCommit)")
    private void postCommitPointCut() {

    }

    @Around("postCommitPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        postCommitAdapter.execute(new ProceedingJoinPointAfterCommitRunnable(proceedingJoinPoint));
        return null;
    }

    @AllArgsConstructor
    private static final class ProceedingJoinPointAfterCommitRunnable implements Runnable {
        private final ProceedingJoinPoint proceedingJoinPoint;

        @Override
        public void run() {
            try {
                proceedingJoinPoint.proceed();
                log.info("Post Commit Success");
            } catch (Throwable e) {
                log.error("Post Commit Aspect Error {} {}", e.getMessage(), e);
            }
        }
    }
}
