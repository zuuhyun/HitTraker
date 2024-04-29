package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.VideoAd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoAdRepository extends JpaRepository<VideoAd, Long> {
}
