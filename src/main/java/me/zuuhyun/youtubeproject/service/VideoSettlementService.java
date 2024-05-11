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

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoSettlementService {
    private final BalanceAccountRepository balanceAccountRepository;
    private final VideoRepository videoRepository;
    private final VideoAdRepository videoAdRepository;
    private final VideoStatisticsRepository videoStatisticsRepository;
    private final UserRepository userRepository;

    public HashMap<String,Long> getSettlement(long id, Period period) {
        LocalDate endDate =  LocalDate.now().minusDays(1);
        LocalDate startDate = Utils.getStartDate(endDate, period);
        List<BalanceAccount> balanceAccounts = balanceAccountRepository.findByVideoIdAndDateBetween(id, startDate, endDate).
                orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        return getSettlementInfo(balanceAccounts);
    }

    private HashMap<String, Long> getSettlementInfo(List<BalanceAccount> balanceAccounts) {

        long videoSettlement = 0L, adSettlement = 0L;
        for (BalanceAccount balanceAccount : balanceAccounts) {
            videoSettlement += balanceAccount.getVideoSettlement();
            adSettlement += balanceAccount.getAdSettlement();
        }

        long totalSettlement = videoSettlement + adSettlement;
        HashMap<String,Long> settlementInfo = new HashMap<>();
        settlementInfo.put("videoSettlement", videoSettlement);
        settlementInfo.put("adSettlement", adSettlement);
        settlementInfo.put("totalSettlement", totalSettlement);

        return settlementInfo;
    }

    @Transactional
    public void saveDayBalanceAccount() {
        /* Date 기준으로 video_statistics 가지고 와서 video_id를 기준으로 video 테이블 찾고, author, video_total_views
        *  video_total_views로 영상단가 * video_statistics의 today_total_views*/
        List<Video> videos = videoRepository.findAll();
        for (Video video : videos) {
            long videoId = video.getId();
            long videoTotalView = video.getVideoTotalView();
            Date date = java.sql.Date.valueOf(LocalDate.now().minusDays(1));
            String author = video.getAuthor();

            /*영상수익계산*/
            double viewPrice = calculateVideoSettlement(videoTotalView);
            double videoSettlement = findTotalViewsByVideoIdAndDateRange(videoId, date, date) * viewPrice;

            /*광고수익계산*/
            double adViewPrice = calculateAdSettlement(videoTotalView);
            double totalAdSettlement = findTotalAdIdAndTimestampByVideoIdAndDateRange(videoId, date, date).size() * adViewPrice;

            User user = userRepository.findByEmail(author).orElseThrow();
            log.info("save balance_account videoId: {}", videoId);
            balanceAccountRepository.save(BalanceAccount.builder()
                    .userId(user.getId())
                    .videoId(videoId)
                    .videoSettlement((long)videoSettlement)
                    .adSettlement((long)totalAdSettlement)
                    .totalSettlement((long)(videoSettlement+totalAdSettlement))
                    .build());
        }
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

    /*영상단가*/
    public double calculateVideoSettlement(long videoTotalViews) {
        double price;
        if (videoTotalViews >= 100000 && videoTotalViews < 500000) {
            price = 1.1;
        } else if (videoTotalViews >= 500000 && videoTotalViews < 1000000 ) {
            price = 1.3;
        } else if (videoTotalViews >= 1000000) {
            price = 1.5;
        } else {
            price = 1.0;
        }
        return price;
    }

    /*광고단가*/
    public double calculateAdSettlement(long videoTotalViews) {
        double price;
        if (videoTotalViews >= 100000 && videoTotalViews < 500000) {
            price = 12;
        } else if (videoTotalViews >= 500000 && videoTotalViews < 1000000 ) {
            price = 15;
        } else if (videoTotalViews >= 1000000) {
            price = 20;
        } else {
            price = 10;
        }
        return price;
    }
}
