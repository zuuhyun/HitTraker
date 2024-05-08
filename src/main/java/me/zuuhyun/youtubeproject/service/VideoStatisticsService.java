package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import me.zuuhyun.youtubeproject.repository.VideoRepository;
import me.zuuhyun.youtubeproject.repository.VideoStatisticsRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.*;

@RequiredArgsConstructor
@Service
public class VideoStatisticsService {
    private final VideoStatisticsRepository videoStatisticsRepository;

    public List<VideoStatistics> getTodayTotalViews(Date date){
        return videoStatisticsRepository.findByDateOrderByTodayTotalViewsDesc(date);
    }

    /* 1. 날짜 기준에 맞는 데이터를 가져옴
     * 2. 데이터 for문을 돌리고 video_id 개수를 세면서 기존 시청기록을 누적
     * 3. 내림차순으로 정렬 후 상위 5개 return
     * */
    public List<Map.Entry<Long, Long>> getRangeTotalViews(Date startDate, Date endDate){
        List<VideoStatistics> videoStatistics = videoStatisticsRepository.findVideoStatisticsByDateRange(startDate, endDate);
        HashMap<Long,Long> map = new HashMap<>();

        for (VideoStatistics statistics : videoStatistics) {
            long key = statistics.getVideoId();
            long value = statistics.getTodayTotalViews();
            map.merge(key, value, Long::sum);
        }
        /*for (Map.Entry<Long, Long> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }*/

        /*값에 따라 내림차순으로 정렬*/
        List<Map.Entry<Long, Long>> sortedEntries = new ArrayList<>(map.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        LinkedHashMap<Long, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Long, Long> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap.entrySet().stream()
                .limit(5)
                .toList();
      }
}
