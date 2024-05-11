package me.zuuhyun.youtubeproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BalanceAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_accounts_id", updatable = false)
    private Long id;

    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Column(name = "video_id", updatable = false)
    private Long videoId;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "video_settlement_day")
    private Double videoSettlementDay;

    @Column(name = "video_settlement_week")
    private Double  videoSettlementWeek;

    @Column(name = "video_settlement_month")
    private Double videoSettlementMonth;

    @Column(name = "ad_settlement_day")
    private Double adSettlementDay;

    @Column(name = "ad_settlement_week")
    private Double adSettlementWeek;

    @Column(name = "ad_settlement_month")
    private Double adSettlementMonth;

    @Column(name = "total_settlement_day")
    private Double totalSettlementDay;

    @Column(name = "total_settlement_week")
    private Double totalSettlementWeek;

    @Column(name = "total_settlement_month")
    private Double totalSettlementMonth;

    @Builder
    public BalanceAccount(Long userId, Long videoId, LocalDate createdAt, Double videoSettlementDay, Double videoSettlementWeek, Double videoSettlementMonth, Double adSettlementDay, Double adSettlementWeek, Double adSettlementMonth, Double totalSettlementDay, Double totalSettlementWeek, Double totalSettlementMonth) {
        this.userId = userId;
        this.videoId = videoId;
        this.createdAt = createdAt;
        this.videoSettlementDay = videoSettlementDay;
        this.videoSettlementWeek = videoSettlementWeek;
        this.videoSettlementMonth = videoSettlementMonth;
        this.adSettlementDay = adSettlementDay;
        this.adSettlementWeek = adSettlementWeek;
        this.adSettlementMonth = adSettlementMonth;
        this.totalSettlementDay = totalSettlementDay;
        this.totalSettlementWeek = totalSettlementWeek;
        this.totalSettlementMonth = totalSettlementMonth;
    }
}
