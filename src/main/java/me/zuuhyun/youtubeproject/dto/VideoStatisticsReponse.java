package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Setter
@Getter
public class VideoStatisticsReponse {
    private Long videoId;
    private Long todayTotalViews;
    private LocalDate date;
}
