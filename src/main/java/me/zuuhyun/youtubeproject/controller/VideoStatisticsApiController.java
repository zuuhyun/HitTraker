package me.zuuhyun.youtubeproject.controller;

import lombok.RequiredArgsConstructor;
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
    @GetMapping("/api/videostat/viewcount/day")
    public ResponseEntity<List<VideoStatisticsReponse>> getTop5ViewedVideosDay() {
        Date startDate = java.sql.Date.valueOf(LocalDate.now());
        return ResponseEntity.ok()
                .body(getTop5ViewedVideos(startDate));
    }

    /* 일주일 조회수 TOP5 */
    @GetMapping("/api/videostat/viewcount/week")
    public ResponseEntity<List<VideoStatisticsReponse>> getTop5ViewedVideosWeek(){
        Date startDate = java.sql.Date.valueOf(LocalDate.now().minusWeeks(1));

        return ResponseEntity.ok()
                .body(getTop5ViewedVideos(startDate));
    }

    /* 한달 조회수 TOP5 */
    @GetMapping("/api/videostat/viewcount/month")
    public ResponseEntity<List<VideoStatisticsReponse>> getTop5ViewedVideosMonth(){
        Date startDate = java.sql.Date.valueOf(LocalDate.now().minusMonths(1));

        return ResponseEntity.ok()
                .body(getTop5ViewedVideos(startDate));
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
