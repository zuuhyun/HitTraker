package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface VideoStatisticsRepository extends JpaRepository<VideoStatistics, Long> {
    /* 주어진 기간내에 누적된 vedio_id, today_total_views */
    @Query("SELECT v FROM VideoStatistics v WHERE DATE(v.date) BETWEEN :startDate AND :endDate ORDER BY v.date")
    List<VideoStatistics> findVideoStatisticsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /* video_id 기준으로 주어진 기간안의 today_total_views */
    @Query("SELECT SUM(vs.todayTotalViews) FROM VideoStatistics vs WHERE vs.videoId = :videoId AND DATE(vs.date) BETWEEN :startDate AND :endDate")
    Long findTotalViewsByVideoIdAndDateRange(@Param("videoId") Long videoId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}