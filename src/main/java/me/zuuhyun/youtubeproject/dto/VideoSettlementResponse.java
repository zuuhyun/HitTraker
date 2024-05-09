package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VideoSettlementResponse {
    private String period;
    private Long videoId;
    private String date;
    private Double videoSettlement;
    private Double adSettlement;
    private Double totalSettlement;
}
