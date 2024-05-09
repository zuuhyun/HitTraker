package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.BalanceAccount;
import me.zuuhyun.youtubeproject.domain.VideoAd;
import me.zuuhyun.youtubeproject.domain.VideoStatistics;
import me.zuuhyun.youtubeproject.repository.VideoAdRepository;
import me.zuuhyun.youtubeproject.repository.VideoRepository;
import me.zuuhyun.youtubeproject.repository.VideoStatisticsRepository;
import me.zuuhyun.youtubeproject.repository.BalanceAccountRepository;
import org.springframework.stereotype.Service;
import me.zuuhyun.youtubeproject.util.Period;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class VideoSettlementService {
    private final VideoRepository videoRepository;
    private final VideoAdRepository videoAdRepository;
    private final VideoStatisticsRepository videoStatisticsRepository;
    private final BalanceAccountRepository balanceAccountRepository;

    public HashMap<String,Double> getSettlementInfo(long id, Period period){
        Date endDate = java.sql.Date.valueOf(LocalDate.now());
        Date startDate = getStartDate(endDate, period);
        BalanceAccount balanceAccount;
        double videoSettlement, adSettlement;

        try {
            balanceAccount = balanceAccountRepository.findByVideoIdAndCreatedAt(id, LocalDateTime.now()).
                    orElseThrow(() -> new IllegalArgumentException("not found: " + id));
            videoSettlement = getVideoSettlement(balanceAccount, period);
            adSettlement = getAdSettlement(balanceAccount, period);
        } catch (Exception e1) {
            /*balanceAccount 테이블 생성해주기*/
            try {
                videoSettlement = calculateVideoSettlement(videoStatisticsRepository.findTotalViewsByVideoIdAndDateRange(id, startDate, endDate));
                adSettlement = makeAdStatisticsSettlement(id, startDate, endDate);
            } catch (Exception e2) {
                videoSettlement = 0;
                adSettlement = 0;
            }
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

    public double makeAdStatisticsSettlement(long id, Date startDate, Date endDate){
        /*video_ad 테이블에서 video_id와 ad_timestamp 기준으로 ad_id를 가져오고 누적 개수를 구한다.*/
        List<VideoAd> videoAdLists = videoAdRepository.findTotalAdIdAndTimestampByVideoIdAndDateRange(id, startDate, endDate);
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

    /*영상정산*/
    public double calculateVideoSettlement(long totalViews){
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
    public double calculateAdSettlement(long totalViews){
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

    public Date getStartDate(Date endDate, Period period){
        Date startDate;
        if (period == Period.WEEK){
            startDate = java.sql.Date.valueOf(LocalDate.now().minusWeeks(1));
        }else if (period == Period.MONTH){
            startDate = java.sql.Date.valueOf(LocalDate.now().minusMonths(1));
        }else {/*Period.DAY*/
            startDate = endDate;
        }
        return startDate;
    }
}
