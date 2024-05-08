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
import java.util.Map;

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

    /* 일주일 조회수 TOP5 */
    @GetMapping("/api/totalrank/viewcount/week")
    public ResponseEntity<List<VideoStatisticsReponse>> getTop5ViewedVideosWeek(){
        Date startDate = java.sql.Date.valueOf(LocalDate.now().minusWeeks(1));

        return ResponseEntity.ok()
                .body(getTop5ViewedVideos(startDate));
    }

    /* 한달 조회수 TOP5 */
    @GetMapping("/api/totalrank/viewcount/month")
    public ResponseEntity<List<VideoStatisticsReponse>> getTop5ViewedVideosMonth(){
        Date startDate = java.sql.Date.valueOf(LocalDate.now().minusMonths(1));

        return ResponseEntity.ok()
                .body(getTop5ViewedVideos(startDate));
    }

    public List<VideoStatisticsReponse> convertToResponse(List<VideoStatistics> statisticsList) {
        List<VideoStatisticsReponse> responses = new ArrayList<>();
        for (VideoStatistics statistics : statisticsList) {
            VideoStatisticsReponse response = new VideoStatisticsReponse();
            response.setVideoId(statistics.getVideoId());
            response.setDate(Date.valueOf(statistics.getDate().toLocalDate()));
            response.setTodayTotalViews(statistics.getTodayTotalViews());
            responses.add(response);
        }
        return responses;
    }

    public List<VideoStatisticsReponse> getTop5ViewedVideos(Date startDate){
        Date endDate = java.sql.Date.valueOf(LocalDate.now());
        List<Map.Entry<Long, Long>> top5Videos = videoStatisticsService.getRangeTotalViews(startDate, endDate);
        List<VideoStatisticsReponse> videoInfoList = new ArrayList<>();
        for (Map.Entry<Long, Long> entry : top5Videos) {
            VideoStatisticsReponse videoInfoDto = new VideoStatisticsReponse();
            videoInfoDto.setVideoId(entry.getKey());
            videoInfoDto.setTodayTotalViews(entry.getValue());
            videoInfoDto.setDate(endDate);
            videoInfoList.add(videoInfoDto);
        }

        return videoInfoList;
    }
}
