package me.zuuhyun.youtubeproject.controller;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.Video;
import me.zuuhyun.youtubeproject.dto.AddVideoRequest;
import me.zuuhyun.youtubeproject.dto.UpdateVideoRequest;
import me.zuuhyun.youtubeproject.dto.VideoResponse;
import me.zuuhyun.youtubeproject.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class VideoApiController {
    private final VideoService videoService;

    @PostMapping("/api/videos")
    public ResponseEntity<Video> addVideo(@RequestBody AddVideoRequest request, Principal principal) {
        Video savedVideo = videoService.save(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedVideo);
    }

    @GetMapping("/api/videos")
    public ResponseEntity<List<VideoResponse>> findAllVideos() {
        List<VideoResponse> videos = videoService.findAll()
                .stream()
                .map(VideoResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(videos);
    }

    @GetMapping("/api/videos/{id}")
    public ResponseEntity<VideoResponse> findVideo(@PathVariable long id) {
        Video video = videoService.findById(id);
        return ResponseEntity.ok()
                .body(new VideoResponse(video));
    }

    @DeleteMapping("/api/videos/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable long id) {
        videoService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/videos/{id}")
    public ResponseEntity<Video> updateVideo(@PathVariable long id,
                                               @RequestBody UpdateVideoRequest request) {
        Video updatedVideo = videoService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedVideo);
    }

    @PostMapping("/api/videos/play/{id}")
    public ResponseEntity<Video> countView(@PathVariable long id) {
        Video updatedVideo = videoService.countView(id);
        return ResponseEntity.ok()
                .body(updatedVideo);
    }
}
