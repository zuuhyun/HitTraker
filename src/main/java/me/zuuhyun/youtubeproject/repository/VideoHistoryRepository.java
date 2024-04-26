package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.VideoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoHistoryRepository extends JpaRepository<VideoHistory, Long> {
}
