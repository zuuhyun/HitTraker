package me.zuuhyun.youtubeproject.repository;

import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface VideoStatisticsRepository extends JpaRepository<VideoStatistics, Long> {

    @Query("SELECT v FROM VideoStatistics v WHERE DATE(v.date) = :date ORDER BY v.todayTotalViews DESC")
    List<VideoStatistics> findByDateOrderByTodayTotalViewsDesc(Date date);

    @Query("SELECT v FROM VideoStatistics v WHERE DATE(v.date) BETWEEN :startDate AND :endDate ORDER BY v.date")
    List<VideoStatistics> findVideoStatisticsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //long countVideoStatisticsByDateRange(Date startDate, Date endDate);
}