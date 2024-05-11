package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    Optional<UserHistory> findFirstByUserIdAndVideoIdOrderByVideoTimestampDesc(long userid, long videoId);
    Optional<List<UserHistory>> findAllByVideoTimestampBetween(LocalDateTime start, LocalDateTime end);
}
