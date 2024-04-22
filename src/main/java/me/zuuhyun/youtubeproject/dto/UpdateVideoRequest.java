package me.zuuhyun.youtubeproject.dto;
//UpdateArticleRequest

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateVideoRequest {
    private String title;
    private String content;
    private Long length;
}
