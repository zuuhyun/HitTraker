package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long>  {
    int countAllBy();
}
