package com.seunghyeon.verysimplesns.global;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitStore {
    private static final int LIMIT=5;
    private final ConcurrentHashMap<UUID, AtomicInteger> writeCountMap= new ConcurrentHashMap<>();
    private final ConcurrentHashMap<UUID, AtomicInteger> readCountMap= new ConcurrentHashMap<>();


    public boolean tryIncrementWrite(UUID userId){
        return tryIncrement(writeCountMap,userId);
    }
    public boolean tryIncrementRead(UUID userId){
            return tryIncrement(readCountMap,userId);
    }

    private boolean tryIncrement(ConcurrentHashMap<UUID, AtomicInteger> countMap, UUID  userId){
        int count = countMap.computeIfAbsent(userId, k->new AtomicInteger(0)).incrementAndGet();
        return count<=LIMIT;
    }

    public void reset(){
        writeCountMap.clear();
        readCountMap.clear();
    }



}
