package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Setter
@Getter
public class VideoSettlementResponse {
    private String period;
    private Long videoId;
    private Date date;
    private Double videoSettlement;
    private Double adSettlement;
    private Double totalSettlement;
}
