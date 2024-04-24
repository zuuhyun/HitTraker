package me.zuuhyun.youtubeproject.dto;

import lombok.Getter;
import me.zuuhyun.youtubeproject.domain.Video;

@Getter
public class VideoResponse {
    private final String title;
    private final String content;
    private final Long length;
    public VideoResponse(Video video) {
        this.title = video.getTitle();
        this.content = video.getContent();
        this.length = video.getLength();
    }
}
