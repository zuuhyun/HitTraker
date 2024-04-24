package me.zuuhyun.youtubeproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zuuhyun.youtubeproject.domain.Video;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddVideoRequest {
    private String title;
    private String content;
    private Long length;
    public Video toEntity() {
        return Video.builder()
                .title(title)
                .content(content)
                .length(length)
                .build();
    }
}
