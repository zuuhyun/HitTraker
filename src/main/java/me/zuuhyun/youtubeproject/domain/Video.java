package me.zuuhyun.youtubeproject.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


//Article.java
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id", updatable = false)
    private Long video_id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "length", nullable = false)
    private Long length;


    @Builder
    public Video(String title, Long length, String content) {
        this.title = title;
        this.length = length;
        this.content = content;
    }

    public void update(String title, Long length, String content) {
        this.title = title;
        this.length = length;
        this.content = content;
    }
}
