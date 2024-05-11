package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.BalanceAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BalanceAccountRepository extends JpaRepository<BalanceAccount, Long> {
    Optional<List<BalanceAccount>> findByVideoIdAndDateBetween(Long videoId, LocalDate startDate, LocalDate endDate);
}
