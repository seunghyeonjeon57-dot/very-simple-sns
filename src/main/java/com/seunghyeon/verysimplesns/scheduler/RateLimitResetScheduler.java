package com.seunghyeon.verysimplesns.scheduler;

import com.seunghyeon.verysimplesns.global.RateLimitStore;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RateLimitResetScheduler {
    private final RateLimitStore store;
    @Scheduled(cron = "0 * * * * *")
    public void reset(){
        store.reset();

    }
}
