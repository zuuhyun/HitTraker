package me.zuuhyun.youtubeproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoStatistics{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_statistics_id", updatable = false)
    private Long id;

    @Column(name = "video_id", nullable = false)
    private Long videoId;

    @Column(name = "today_total_view", nullable = false)
    private Long todayTotalView;

    @Column(name = "date")
    private LocalDateTime date;

    @Builder
    public VideoStatistics(Long videoId, Long todayTotalView, LocalDateTime date) {
        this.videoId = videoId;
        this.todayTotalView = todayTotalView;
        this.date = date;
    }
}
