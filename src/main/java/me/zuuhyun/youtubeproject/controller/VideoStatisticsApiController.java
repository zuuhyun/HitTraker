package me.zuuhyun.youtubeproject.controller;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import me.zuuhyun.youtubeproject.dto.VideoStatisticsReponse;
import me.zuuhyun.youtubeproject.service.VideoStatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class VideoStatisticsApiController {
    private final VideoStatisticsService videoStatisticsService;

    /* 1일 조회수 TOP5 */
    @GetMapping("/api/totalrank/viewcount/day")
    public ResponseEntity<List<VideoStatisticsReponse>> getTop5ViewedVideosDay() {
        LocalDate localDate = LocalDate.now();
        Date today = Date.valueOf(localDate);
        List<VideoStatistics> mostViewedVideos = videoStatisticsService.getTodayTotalViews(today);

        return ResponseEntity.ok()
                .body(convertToResponse(mostViewedVideos));
    }

    @GetMapping("/api/totalrank/viewcount/week")
    //public ResponseEntity<List<VideoStatisticsReponse>> getTop5ViewedVideosWeek()
      public void getTop5ViewedVideosWeek(){
        LocalDate localDate = LocalDate.now();
        Date endDate = Date.valueOf(localDate);
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        Date startDate = Date.valueOf(oneWeekAgo);

        //List<VideoStatistics> mostViewedVideos = videoStatisticsService.getWeekTotalViews(startDate, endDate);
        videoStatisticsService.getWeekTotalViews(startDate, endDate);
    }

    public List<VideoStatisticsReponse> convertToResponse(List<VideoStatistics> statisticsList) {
        List<VideoStatisticsReponse> responses = new ArrayList<>();
        for (VideoStatistics statistics : statisticsList) {
            VideoStatisticsReponse response = new VideoStatisticsReponse();
            response.setVideoId(statistics.getVideoId());
            response.setDate(statistics.getDate());
            response.setTodayTotalViews(statistics.getTodayTotalViews());
            responses.add(response);
        }
        return responses;
    }

    /* 1일, 1주일, 1달 동안 재생 시간이 긴 동영상 TOP5
     *  현재 날짜 기준으로 (createdAt) total_playtime_day, total_playtime_week, total_playtime_month 가져오기
     */
}
