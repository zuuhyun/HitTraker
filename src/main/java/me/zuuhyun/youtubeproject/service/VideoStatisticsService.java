package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.UserHistory;
import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import me.zuuhyun.youtubeproject.repository.UserHistoryRepository;
import me.zuuhyun.youtubeproject.repository.VideoRepository;
import me.zuuhyun.youtubeproject.repository.VideoStatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class VideoStatisticsService {
    private final UserHistoryRepository userHistoryRepository;
    private final VideoStatisticsRepository videoStatisticsRepository;

    /* 1. 날짜 기준에 맞는 데이터를 가져옴
     * 2. 데이터 for문을 돌리고 video_id 개수를 세면서 기존 시청기록을 누적
     * 3. 내림차순으로 정렬 후 상위 5개 return
     * */
    public List<Map.Entry<Long, Long>> getTop5ViewsVideos(Date startDate, Date endDate){
        /*값에 따라 내림차순으로 정렬*/
        List<Map.Entry<Long, Long>> sortedEntries = new ArrayList<>(getRangeTotalViews(startDate, endDate).entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        LinkedHashMap<Long, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Long, Long> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap.entrySet().stream()
                .limit(5)
                .toList();
      }

    public HashMap<Long,Long> getRangeTotalViews(Date startDate, Date endDate) {
        List<VideoStatistics> videoStatistics = videoStatisticsRepository.findVideoStatisticsByDateRange(startDate, endDate);
        HashMap<Long,Long> totalViews = new HashMap<>();

        for (VideoStatistics statistics : videoStatistics) {
            long key = statistics.getVideoId();
            long value = statistics.getTodayTotalViews();
            totalViews.merge(key, value, Long::sum);
        }
        return totalViews;
    }

    /*videoStatistics 테이블 생성*/
    @Transactional
    public void saveDayVideoStatistics() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<UserHistory> userHistories = userHistoryRepository.findAllByVideoTimestampBetween(yesterday.atStartOfDay(), yesterday.atTime(LocalTime.MAX))
                .orElseThrow(() -> new IllegalArgumentException("not found: " + yesterday));

        HashMap<Long,Long> todayTotalViews = new HashMap<>();
        for (UserHistory history : userHistories) {
            long key = history.getVideoId();
            if (todayTotalViews.get(key) == null) {
                todayTotalViews.put(key, 1L);
            } else {
                todayTotalViews.put(key, todayTotalViews.get(key) + 1);
            }
        }
        for (Map.Entry<Long, Long> entry : todayTotalViews.entrySet()) {
            videoStatisticsRepository.save(VideoStatistics.builder()
                    .videoId(entry.getKey())
                    .date(LocalDateTime.now())
                    .todayTotalViews(entry.getValue())
                    .build());
        }
    }
}
