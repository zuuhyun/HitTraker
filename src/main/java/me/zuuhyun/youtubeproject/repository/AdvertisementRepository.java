package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    int countAllBy();
}
