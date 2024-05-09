package me.zuuhyun.youtubeproject.controller;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.dto.VideoSettlementResponse;
import me.zuuhyun.youtubeproject.service.VideoSettlementService;
import me.zuuhyun.youtubeproject.util.Period;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class VideoSettlementApiController {
    private final VideoSettlementService videoSettlementService;

    /* 비디오 별 정산금액
    아이디를 받고
    1일 1주일 1달 -> 조회수 가져오기
     */
    @GetMapping("/api/videosettle/videos/{id}")
    public ResponseEntity<List<VideoSettlementResponse>> getVideoSettlement(@PathVariable long id) {
        /*비디오아이디, 기간을 service에 전달 영상 조회수의 정산 금액을 받음*/
        /*todo: NULL 처리*/
        List<VideoSettlementResponse> videoSettlementResponseList = new ArrayList<>();
        for(Period period : Period.values()) {
            double videoSettlement = videoSettlementService.getVideoSettlement(id, period);
            double adSettlement = videoSettlementService.getAdSettlement(id, period);
            double totalSettlement = videoSettlement + adSettlement;
            VideoSettlementResponse response = new VideoSettlementResponse();
            response.setPeriod(period.toString());
            response.setVideoId(id);
            response.setDate(java.sql.Date.valueOf(LocalDate.now())); // 날짜 정보 설정 필요
            response.setVideoSettlement(videoSettlement);
            response.setAdSettlement(adSettlement);
            response.setTotalSettlement(totalSettlement);
            videoSettlementResponseList.add(response);
        }
        return ResponseEntity.ok()
                .body(videoSettlementResponseList);
    }
}
