package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zuuhyun.youtubeproject.domain.BalanceAccount;
import me.zuuhyun.youtubeproject.domain.User;
import me.zuuhyun.youtubeproject.domain.Video;
import me.zuuhyun.youtubeproject.domain.VideoAd;
import me.zuuhyun.youtubeproject.repository.*;
import org.springframework.stereotype.Service;
import me.zuuhyun.youtubeproject.util.Period;
import me.zuuhyun.youtubeproject.util.Utils;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoSettlementService {
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final VideoAdRepository videoAdRepository;
    private final VideoStatisticsRepository videoStatisticsRepository;
    private final BalanceAccountRepository balanceAccountRepository;

    @Transactional
    public HashMap<String,Double> getSettlementInfo(long id, Period period){
        Date endDate = java.sql.Date.valueOf(LocalDate.now().minusDays(1));
        Date startDate = Utils.getStartDate(endDate, period);
        BalanceAccount balanceAccount;
        double videoSettlement, adSettlement;

        try {
            balanceAccount = balanceAccountRepository.findByVideoIdAndCreatedAt(id, LocalDate.now()).
                    orElseThrow(() -> new IllegalArgumentException("not found: " + id));
            videoSettlement = getVideoSettlement(balanceAccount, period);
            adSettlement = getAdSettlement(balanceAccount, period);
        } catch (Exception e1) {
            try {
                videoSettlement = calculateVideoSettlement(findTotalViewsByVideoIdAndDateRange(id, startDate, endDate));
                adSettlement = getTotalAdViewsSettlement(id, startDate, endDate);
            } catch (Exception e2) {
                videoSettlement = 0;
                adSettlement = 0;
            }
            saveDayBalanceAccount(id);
        }

        HashMap<String,Double> settlementInfo = new HashMap<>();
        settlementInfo.put("videoSettlement",videoSettlement);
        settlementInfo.put("adSettlement",adSettlement);
        settlementInfo.put("totalSettlement",videoSettlement + adSettlement);

        return settlementInfo;
    }

    public double getVideoSettlement(BalanceAccount balanceAccount, Period period){
        return switch (period) {
            case DAY -> balanceAccount.getVideoSettlementDay();
            case WEEK -> balanceAccount.getVideoSettlementWeek();
            case MONTH -> balanceAccount.getVideoSettlementMonth();
        };
    }

    public double getAdSettlement(BalanceAccount balanceAccount, Period period){
        return switch (period) {
            case DAY -> balanceAccount.getAdSettlementDay();
            case WEEK -> balanceAccount.getAdSettlementWeek();
            case MONTH -> balanceAccount.getAdSettlementMonth();
        };
    }

    public double getTotalAdViewsSettlement(long id, Date startDate, Date endDate){
        /*video_ad 테이블에서 video_id와 ad_timestamp 기준으로 ad_id를 가져오고 누적 개수를 구한다.*/
        List<VideoAd> videoAdLists = findTotalAdIdAndTimestampByVideoIdAndDateRange(id, startDate, endDate);
        HashMap<Long,Long> map = new HashMap<>();
        for (VideoAd videoAd : videoAdLists) {
            long key = videoAd.getAdId();
            map.merge(key, 1L, Long::sum);
        }
        double totalAdViewsPrice = 0;
        for (Map.Entry<Long, Long> entry : map.entrySet()){
            totalAdViewsPrice += calculateAdSettlement(entry.getValue());
        }
        return totalAdViewsPrice;
    }

    public long findTotalViewsByVideoIdAndDateRange(long videoId, Date startDate, Date endDate) {
        try {
            return videoStatisticsRepository.findTotalViewsByVideoIdAndDateRange(videoId, startDate, endDate);
        } catch (Exception e){
            return 0;
        }
    }

    public List<VideoAd> findTotalAdIdAndTimestampByVideoIdAndDateRange(Long id, Date startDate, Date endDate) {
        try {
            return videoAdRepository.findTotalAdIdAndTimestampByVideoIdAndDateRange(id, startDate, endDate);
        } catch (Exception e){
            return null;
        }
    }

    /*영상정산*/
    public double calculateVideoSettlement(long totalViews) {
        double price;
        if (totalViews >= 100000 && totalViews < 500000) {
            price = 1.1;
        } else if (totalViews >= 500000 && totalViews < 1000000 ) {
            price = 1.3;
        } else if (totalViews >= 1000000) {
            price = 1.5;
        } else {
            price = 1.0;
        }
        return totalViews * price;
    }

    /*광고영상정산*/
    public double calculateAdSettlement(long totalViews) {
        double price;
        if (totalViews >= 100000 && totalViews < 500000) {
            price = 12;
        } else if (totalViews >= 500000 && totalViews < 1000000 ) {
            price = 15;
        } else if (totalViews >= 1000000) {
            price = 20;
        } else {
            price = 10;
        }
        return totalViews * price;
    }

    /* 조회한 날의 전날 기준으로 일간 주간 월간 정산 테이블 생성*/
    @Transactional
    public void saveDayBalanceAccount(long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow();
        Date startDate, endDate;
        double dayVideoSettlement = 0, weekVideoSettlement = 0, monthVideoSettlement = 0;
        double dayAdettlement = 0, weekAdsettlement = 0, monthAdSettlement = 0;


        String author = video.getAuthor();
        User user = userRepository.findByEmail(author).orElseThrow();
        endDate = java.sql.Date.valueOf(LocalDate.now().minusDays(1));

        for (Period period : Period.values()) {
            startDate = Utils.getStartDate(endDate, period);
            switch (period) {
                case DAY:
                    dayVideoSettlement = calculateVideoSettlement(findTotalViewsByVideoIdAndDateRange(videoId, startDate, endDate));
                    dayAdettlement = getTotalAdViewsSettlement(videoId, startDate, endDate);
                    break;
                case WEEK:
                    weekVideoSettlement = calculateVideoSettlement(findTotalViewsByVideoIdAndDateRange(videoId, startDate, endDate));
                    weekAdsettlement = getTotalAdViewsSettlement(videoId, startDate, endDate);
                    break;
               case MONTH:
                   monthVideoSettlement = calculateVideoSettlement(findTotalViewsByVideoIdAndDateRange(videoId, startDate, endDate));
                   monthAdSettlement = getTotalAdViewsSettlement(videoId, startDate, endDate);
                   break;
            }
        }
        log.info("save balance_account videoId: {}", videoId);
        balanceAccountRepository.save(BalanceAccount.builder()
                .userId(user.getId())
                .videoId(videoId)
                .createdAt(LocalDate.now())
                .videoSettlementDay(dayVideoSettlement)
                .videoSettlementWeek(weekVideoSettlement)
                .videoSettlementMonth(monthVideoSettlement)
                .adSettlementDay(dayAdettlement)
                .adSettlementWeek(weekAdsettlement)
                .adSettlementMonth(monthAdSettlement)
                .totalSettlementDay(dayVideoSettlement+dayAdettlement)
                .totalSettlementWeek(weekVideoSettlement+weekAdsettlement)
                .totalSettlementMonth(monthVideoSettlement+monthAdSettlement)
                .build());
    }
}
