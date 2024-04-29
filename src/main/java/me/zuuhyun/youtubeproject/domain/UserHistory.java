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
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "play_id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Column(name = "viewing_time", nullable = false)
    private Long viewingTime;

    @CreatedDate
    @Column(name = "video_timestamp")
    private LocalDateTime videoTimestamp;

    @Builder
    public UserHistory(Long userId, Long videoId, Long viewingTime, LocalDateTime videoTimestamp) {
        this.userId = userId;
        this.videoId = videoId;
        this.viewingTime = viewingTime;
        this.videoTimestamp = videoTimestamp;
    }

    public void updateViewingTime(Long viewingTime) {
        this.viewingTime = viewingTime;
    }
}
