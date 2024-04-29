package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zuuhyun.youtubeproject.domain.Video;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class VideoViewResponse {
    private Long id;
    private String title;
    private String content;
    private long length;
    private String author;
    private LocalDateTime createdAt;

    public VideoViewResponse(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.content = video.getContent();
        this.length = video.getLength();
        this.createdAt = video.getCreatedAt();
        this.author = video.getAuthor();
    }
}
