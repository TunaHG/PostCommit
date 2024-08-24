package com.tuna.blog.application;

import com.tuna.blog.infrastructure.KafkaProduceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FrontService {
    private final TransactionService transactionService;
    private final KafkaProduceService kafkaProduceService;

    public void frontCall() {
        // transactionService.businessLogic();
        // kafkaProduceService.produce();
    }
}
