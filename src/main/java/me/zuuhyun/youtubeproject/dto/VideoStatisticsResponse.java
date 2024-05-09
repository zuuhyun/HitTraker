package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Setter
@Getter
public class VideoStatisticsResponse {
    private Long videoId;
    private Long todayTotalViews;
    private Date date;
}
