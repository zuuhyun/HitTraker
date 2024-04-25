package me.zuuhyun.youtubeproject.controller;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.Video;
import me.zuuhyun.youtubeproject.dto.VideoListViewResponse;
import me.zuuhyun.youtubeproject.dto.VideoViewResponse;
import me.zuuhyun.youtubeproject.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class VideoViewController {
    private final VideoService videoService;
    @GetMapping("/videos")
    public String getVideos(Model model) {
        List<VideoListViewResponse> videos = videoService.findAll().stream()
                .map(VideoListViewResponse::new)
                .toList();
        model.addAttribute("videos", videos);

        return "videoList";
    }

    @GetMapping("/videos/{id}")
    public String getVideo(@PathVariable Long id, Model model) {
        Video video = videoService.findById(id);
        model.addAttribute("video", new VideoViewResponse(video));
        return "video";
    }

    @GetMapping("/new-video")
    public String newVideo(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("video", new VideoViewResponse());
        } else {
            Video video = videoService.findById(id);
            model.addAttribute("video", new VideoViewResponse(video));
        }

        return "newVideo";
    }
}
