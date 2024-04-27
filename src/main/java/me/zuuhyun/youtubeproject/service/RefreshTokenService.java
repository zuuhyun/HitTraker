package me.zuuhyun.youtubeproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.RefreshToken;
import me.zuuhyun.youtubeproject.repository.RefreshTokenRepository;
import me.zuuhyun.youtubeproject.config.jwt.TokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }

    @Transactional
    public void delete() {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long userId = tokenProvider.getUserId(token);

        refreshTokenRepository.deleteByUserId(userId);
    }
}
