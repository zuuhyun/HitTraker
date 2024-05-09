package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.VideoAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface VideoAdRepository extends JpaRepository<VideoAd, Long> {

    @Query("SELECT va FROM VideoAd va WHERE va.videoId = :videoId AND DATE(va.createdAt) BETWEEN :startDate AND :endDate")
    List<VideoAd> findTotalAdIdAndTimestampByVideoIdAndDateRange(@Param("videoId") Long videoId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
