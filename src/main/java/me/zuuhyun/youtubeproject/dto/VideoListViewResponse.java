package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import me.zuuhyun.youtubeproject.domain.Video;

import java.time.LocalDateTime;

@Getter
public class VideoListViewResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Long length;
    private final LocalDateTime createdAt;

    public VideoListViewResponse(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.content = video.getContent();
        this.length = video.getLength();
        this.createdAt = video.getCreatedAt();
    }
}
