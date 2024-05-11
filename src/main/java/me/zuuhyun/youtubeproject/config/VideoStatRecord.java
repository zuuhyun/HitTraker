package me.zuuhyun.youtubeproject.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zuuhyun.youtubeproject.service.VideoSettlementService;
import me.zuuhyun.youtubeproject.service.VideoStatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class VideoStatRecord {
    private final VideoStatisticsService videoStatisticsService;
    private final VideoSettlementService videoSettlementService;

    // 매일 2시에 자동으로 시작하는 스케쥴러
    @Scheduled(cron = "0 0 2 * * *")
    public void autoUpdateVideoStatisticsAndBalanceAccounts() throws Exception {
        try {
            log.info("Start auto update VideoStatistics");
            videoStatisticsService.saveDayVideoStatistics();
            log.info("End auto update VideoStatistics");
            log.info("Start auto update saveDayBalanceAccount");
            videoSettlementService.saveDayBalanceAccount();
            log.info("End auto update saveDayBalanceAccount");
        } catch (Exception e) {
            log.error("Error auto update");
            log.error(e.getMessage());
        }
    }
}
