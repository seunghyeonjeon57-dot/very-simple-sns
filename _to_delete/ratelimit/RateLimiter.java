package com.seunghyeon.verysimplesns.ratelimit;

import com.seunghyeon.verysimplesns.exception.SimpleSnsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 분당 API 호출 횟수를 자료구조(ConcurrentHashMap)로 기록하고,
 * 매 분 정각에 스케줄러로 초기화하는 간단한 고정 윈도우(fixed window) 방식의 rate limiter.
 *
 * Redis 없이 인메모리로 처리하므로 서버가 여러 대로 늘어나면(스케일 아웃)
 * 인스턴스별로 카운트가 따로 놀아 정합성이 깨진다는 한계가 있음 - 이 프로젝트는 단일 인스턴스 기준이라 이렇게 설계함.
 */
@Slf4j
@Component
public class RateLimiter {

    private static final int LIMIT_PER_MINUTE = 5;

    // key = "userId:action" 형태. 값은 이번 분(minute window) 동안의 누적 호출 횟수
    private final Map<String, AtomicInteger> callCounts = new ConcurrentHashMap<>();

    public void checkAndCount(UUID userId, ApiAction action) {
        String key = userId + ":" + action;
        int count = callCounts.computeIfAbsent(key, k -> new AtomicInteger(0)).incrementAndGet();

        if (count > LIMIT_PER_MINUTE) {
            throw new SimpleSnsException(
                    action + " API 호출 횟수를 초과했습니다. 잠시 후 다시 시도해주세요.",
                    HttpStatus.TOO_MANY_REQUESTS
            );
        }
    }

    // 분 단위 task - 매 분 0초에 실행되어 카운트를 초기화
    @Scheduled(cron = "0 * * * * *")
    public void resetCounts() {
        int size = callCounts.size();
        callCounts.clear();
        log.debug("API 호출 횟수 기록 초기화 완료. 초기화 전 key 개수: {}", size);
    }
}
