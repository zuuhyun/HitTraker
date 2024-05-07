package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import me.zuuhyun.youtubeproject.dto.VideoStatisticsReponse;
import me.zuuhyun.youtubeproject.repository.VideoRepository;
import me.zuuhyun.youtubeproject.repository.VideoStatisticsRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

import static java.sql.Types.NULL;

@RequiredArgsConstructor
@Service
public class VideoStatisticsService {
    private final VideoStatisticsRepository videoStatisticsRepository;
    private final VideoRepository videoRepository;

    public List<VideoStatistics> getTodayTotalViews(Date date){
        return videoStatisticsRepository.findByDateOrderByTodayTotalViewsDesc(date);
    }

    /* 1. 날짜 기준에 맞는 데이터 개수를 가져와야 함
     *  2. 데이터 for문을 돌리면서 video_id 갯수를 세면서 +1 을 해주고
     *  return을 해주자 ....
     * */
    //public List<VideoStatistics> getWeekTotalViews(Date startDate, Date endDate)
      public void getWeekTotalViews(Date startDate, Date endDate){

        List<VideoStatistics> videoStatistics = videoStatisticsRepository.findVideoStatisticsByDateRange(startDate, endDate);

        HashMap<Long,Long> map = new HashMap<>();
        List<VideoStatisticsReponse> responses = new ArrayList<>();
        for (VideoStatistics statistics : videoStatistics) {
              VideoStatisticsReponse response = new VideoStatisticsReponse();
              long key = statistics.getVideoId();
              long value = statistics.getTodayTotalViews();
              if (map.get(key) == null) {
                  map.put(key,value);
              } else {
                  map.put(key,value+map.get(key));
              }
        }

        /*for (Map.Entry<Long, Long> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }*/

        List<Map.Entry<Long, Long>> sortedEntries = new ArrayList<>(map.entrySet());

        // 값에 따라 내림차순으로 정렬
        Collections.sort(sortedEntries, (entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        LinkedHashMap<Long, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Long, Long> entry : sortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        /*
        for (Map.Entry<Long, Long> entry : sortedMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
         */
      }
}
