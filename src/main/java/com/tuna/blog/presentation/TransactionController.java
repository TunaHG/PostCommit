package com.tuna.blog.presentation;

import com.tuna.blog.application.FrontService;
import com.tuna.blog.application.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/name")
    public ResponseEntity<String> getMemberByName(
        @RequestParam(name = "name") String name
    ) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                this.transactionService.getMemberByName(name)
            );
    }

    @GetMapping("/update")
    public ResponseEntity<String> updateMember() {
        this.transactionService.updateMember();

        return ResponseEntity.status(HttpStatus.OK)
            .body("Member updated");
    }

    @GetMapping("/create")
    public ResponseEntity<String> createMember() {
        this.transactionService.createMember();

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Member created");
    }
}
