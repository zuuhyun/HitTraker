package me.zuuhyun.youtubeproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(name = "length", nullable = false)
    private Long length;

    @Builder
    public Video(String title, String content, Long length) {
        this.title = title;
        this.content = content;
        this.length = length;
    }

    public void update(String title, String content, Long length) {
        this.title = title;
        this.content = content;
        this.length = length;
    }
}
