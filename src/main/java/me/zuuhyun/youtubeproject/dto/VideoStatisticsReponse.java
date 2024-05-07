package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import lombok.Setter;
import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import java.time.LocalDateTime;

@Setter
@Getter
public class VideoStatisticsReponse {
    private Long videoId;
    private Long todayTotalViews;
    private LocalDateTime date;

}
