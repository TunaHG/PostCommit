package com.tuna.blog.infrastructure;

import com.tuna.blog.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
public class KafkaProduceService {

    @TransactionalEventListener
    public void produce(Member member) {
        // Do kafka message produce
        log.info("Kafka message produced");
        log.info("Member: {}", member.toString());
    }

    @TransactionalEventListener
    public void produce2(Member member) {
        // Do kafka message produce
        log.info("Kafka message produced 2");
        log.info("Member 2: {}", member.toString());
    }

    public void syncProduce(Member member) {
        TransactionSynchronizationManager.registerSynchronization(
            new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    // Do kafka message produce
                    log.info("Kafka message produced (Sync)");
                    log.info("Member: {}", member.toString());
                }
            }
        );
    }

    @PostCommit
    public void syncAopProduce(Member member) {
        // Do kafka message produce
        log.info("Kafka message produced (Sync AOP)");
        log.info("Member: {}", member.toString());
    }
}
