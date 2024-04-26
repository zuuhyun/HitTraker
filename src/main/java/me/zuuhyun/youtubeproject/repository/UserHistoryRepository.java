package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    Optional<UserHistory> findByUserIdAndVideoId(long userId, long videoId);
}
