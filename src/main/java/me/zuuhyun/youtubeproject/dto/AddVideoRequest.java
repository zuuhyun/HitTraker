package me.zuuhyun.youtubeproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.zuuhyun.youtubeproject.domain.Video;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddVideoRequest {
    private String title;
    private String content;
    private Long length;
    private LocalDateTime createdAt;

    public Video toEntity(String author) {
        return Video.builder()
                .title(title)
                .author(author)
                .content(content)
                .length(length)
                .createdAt(createdAt)
                .build();
    }
}
