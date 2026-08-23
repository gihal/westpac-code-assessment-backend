package com.westpac.account.component;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OffensiveNicknamePolicyTest {
    private final OffensiveNicknamePolicy policy =
            new OffensiveNicknamePolicy();

    @Test
    void shouldReturnFalseWhenNicknameIsNull() {
        assertThat(policy.isOffensive(null)).isFalse();
    }

    @Test
    void shouldReturnFalseWhenNicknameIsBlank() {
        assertThat(policy.isOffensive("   ")).isFalse();
    }

    @Test
    void shouldDetectOffensiveNicknameCaseInsensitively() {
        assertThat(policy.isOffensive("My OFFENSIVE Account")).isTrue();
    }

    @Test
    void shouldAllowValidNickname() {
        assertThat(policy.isOffensive("Holiday Fund")).isFalse();
    }
}