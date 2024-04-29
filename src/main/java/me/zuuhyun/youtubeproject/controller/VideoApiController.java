package me.zuuhyun.youtubeproject.controller;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.UserHistory;
import me.zuuhyun.youtubeproject.domain.Video;
import me.zuuhyun.youtubeproject.dto.AddVideoRequest;
import me.zuuhyun.youtubeproject.dto.UpdateVideoRequest;
import me.zuuhyun.youtubeproject.dto.VideoResponse;
import me.zuuhyun.youtubeproject.service.VideoService;
import me.zuuhyun.youtubeproject.service.UserHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class VideoApiController {
    private final VideoService videoService;
    private final UserHistoryService userHistoryService;

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
    public ResponseEntity<UserHistory> countVideoView(@PathVariable long id, @RequestBody UserHistory request) {
        //조회수증가
        videoService.updateCountVideoView(id);
        UserHistory userHistory;
        //기존 재생 기록 있는지 조회
        try{
            userHistory = userHistoryService.getUserHistory(request.getUserId(), id);
        }catch (IllegalArgumentException e){
            userHistory = userHistoryService.saveUserHistory(request.getUserId(), id);
        }
        return ResponseEntity.ok()
                .body(userHistory);
    }

    @PostMapping("/api/videos/stop/{id}")
    public ResponseEntity<Video> countAdView(@PathVariable long id, @RequestBody UserHistory request) {
        userHistoryService.updateViewTime(request.getUserId(), id, request.getViewingTime());
        long viewing_time = userHistoryService.getUserHistory(request.getUserId(), id).getViewingTime();
        Video updatedVideo = videoService.updateCountAdView(id, viewing_time/300);
        return ResponseEntity.ok()
                .body(updatedVideo);
    }
}
