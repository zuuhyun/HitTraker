package me.zuuhyun.youtubeproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "video_settlement")
    private Long videoSettlement;

    @Column(name = "ad_settlement")
    private Long adSettlement;

    @Column(name = "total_settlement")
    private Long totalSettlement;

    @Builder
    public BalanceAccount(Long userId, Long videoId, LocalDateTime createdAt, LocalDate date, Long videoSettlement, Long adSettlement, Long totalSettlement) {
        this.userId = userId;
        this.videoId = videoId;
        this.createdAt = createdAt;
        this.date = date;
        this.videoSettlement = videoSettlement;
        this.adSettlement = adSettlement;
        this.totalSettlement = totalSettlement;
    }
}
