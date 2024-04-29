package me.zuuhyun.youtubeproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_ad_id", updatable = false)
    private Long id;

    @Column(name = "video_id", updatable = false)
    private Long videoId;

    @Column(name = "ad_id", updatable = false)
    private Long adId;

    @CreatedDate
    @Column(name = "ad_timestamp")
    private LocalDateTime createdAt;

    @Builder
    public VideoAd(Long videoId, Long adId, LocalDateTime createdAt) {
        this.videoId = videoId;
        this.adId = adId;
        this.createdAt = createdAt;
    }
}
