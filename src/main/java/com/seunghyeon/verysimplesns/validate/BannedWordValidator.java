package com.seunghyeon.verysimplesns.validate;

import java.util.Set;

public class BannedWordValidator {
    public static final Set<String> BANNED_WORDS = Set.of(
            "admin","관리자","운영자","system","시발","병신"
    );

    private BannedWordValidator(){}

    public static boolean isBanned(String nickname){
        return BANNED_WORDS.stream()
                .anyMatch(words ->nickname.contains(words));
    }
}
