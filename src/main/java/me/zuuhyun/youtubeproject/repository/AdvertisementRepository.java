package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    int countAllBy();
}
