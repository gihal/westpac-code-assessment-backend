package com.westpac.account.component;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OffensiveNicknamePolicy {

    private static final Set<String> OFFENSIVE_TERMS = Set.of(
            "offensive",
            "abusive",
            "inappropriate"
    );

    /**
     * Checks if a nickname is offensive.
     * @param nickName
     * @return true if the nickname is offensive, false otherwise.
     */
    public boolean isOffensive(String nickName) {
        if (nickName == null || nickName.isBlank()) {
            return false;
        }

        String normalized = nickName.trim().toLowerCase();

        return OFFENSIVE_TERMS.stream()
                .anyMatch(normalized::contains);
    }
}