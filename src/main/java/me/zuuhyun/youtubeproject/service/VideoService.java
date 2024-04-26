package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;

import me.zuuhyun.youtubeproject.domain.Video;
import me.zuuhyun.youtubeproject.dto.AddVideoRequest;
import me.zuuhyun.youtubeproject.dto.UpdateVideoRequest;
import me.zuuhyun.youtubeproject.repository.VideoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;

    public Video save(AddVideoRequest request, String userName) {
        return videoRepository.save(request.toEntity(userName));
    }

    public List<Video> findAll() {
        return videoRepository.findAll();
    }

    public Video findById(long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        videoRepository.deleteById(id);
    }

    @Transactional
    public Video update(long id, UpdateVideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        video.update(request.getTitle(), request.getContent(), request.getLength(), LocalDateTime.now());

        return video;
    }

    @Transactional
    public Video countView(long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        video.countView();
        return video;
    }

}
