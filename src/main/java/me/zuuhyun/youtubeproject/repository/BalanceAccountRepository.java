package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.BalanceAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BalanceAccountRepository extends JpaRepository<BalanceAccount, Long> {
    Optional<BalanceAccount> findByVideoIdAndCreatedAt(Long videoId, LocalDateTime createdAt);
}
