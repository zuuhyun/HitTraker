package me.zuuhyun.youtubeproject.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zuuhyun.youtubeproject.service.VideoSettlementService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class VideoStatRecord {
    private final VideoSettlementService videoSettlementService;

    // 매일 2시에 자동으로 시작하는 스케쥴러
    @Scheduled(cron = "0 0 2 * * *")
    public void autoUpdateVideoStatistics() throws Exception {
        try{
            log.info("Start auto update VideoStatistics");
            //video_statistics 테이블 만드는 함수 호출
            //user_history테이블에서 해결할것.
            videoSettlementService.saveAllVideoStatistics();

        } catch (Exception e){
            log.error("error auto update VideoStatistics");
            log.error(e.getMessage());
        }

    }
    @Scheduled(cron = "0 0 2 * * *")
    public void autoUpdateBalanceAccounts() throws Exception {
        try{
            log.info("Start auto update BalanceAccounts");
            //balance_account 테이블 만드는 함수 호출

        }catch (Exception e){
            log.error("error auto update BalanceAccounts");
            log.error(e.getMessage());
        }

    }

}
