package me.zuuhyun.youtubeproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "duration", nullable = false)
    private Long length;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "video_total_views", nullable = false)
    private Long videoTotalView;

    @Builder
    public Video(String title, String author, String content, Long length, LocalDateTime createdAt, Long videoTotalView) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.length = length;
        this.createdAt = createdAt;
        this.videoTotalView = videoTotalView;
    }

    public void update(String title, String content, Long length, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.length = length;
        this.updatedAt = createdAt;
    }

    public void countTotalView() {
        this.videoTotalView = getVideoTotalView() + 1;
    }
}
