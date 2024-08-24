package com.tuna.blog.application;

import com.tuna.blog.domain.Member;
import com.tuna.blog.infrastructure.KafkaProduceService;
import com.tuna.blog.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final KafkaProduceService kafkaProduceService;

    @Transactional
    public void businessLogic() {
        // Do some business logic
        log.info("Business logic executed");
    }

    @Transactional
    public String getMemberByName(String name) {
        Member member = memberRepository.findByName(name);
        return member.toString();
    }

    @Transactional
    public void updateMember() {
        Member member = memberRepository.findById(1L)
            .orElse(null);

        assert member != null;

        log.info("Before Member: {}", member);

        member.updateAge(100);

        log.info("After Member: {}", member);

        // applicationEventPublisher.publishEvent(member);
        // kafkaProduceService.syncProduce(member);
        kafkaProduceService.syncAopProduce(member);
    }

    @Transactional
    public void createMember() {
        List<Member> list =
            List.of(
                Member.builder()
                    .name("이름1")
                    .age(10)
                    .build(),
                Member.builder()
                    .name("이름2")
                    .age(20)
                    .build(),
                Member.builder()
                    .name("이름3")
                    .age(30)
                    .build()
            );

        memberRepository.saveAll(list);
    }
}
