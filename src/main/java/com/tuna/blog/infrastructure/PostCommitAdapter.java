package com.tuna.blog.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostCommitAdapter implements TransactionSynchronization {
    private static final ThreadLocal<List<Runnable>> RUNNABLE = new ThreadLocal<>();

    public void execute(Runnable runnable) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            List<Runnable> localRunnables = RUNNABLE.get();
            if (ObjectUtils.isEmpty(localRunnables)) {
                localRunnables = new ArrayList<>();
            }
            localRunnables.add(runnable);
            RUNNABLE.set(localRunnables);

            TransactionSynchronizationManager.registerSynchronization(this);
            return;
        }
        runnable.run();
    }

    @Override
    public void afterCommit() {
        List<Runnable> runnables = RUNNABLE.get();
        runnables.forEach(Runnable::run);
    }

    @Override
    public void afterCompletion(int status) {
        RUNNABLE.remove();
    }
}
