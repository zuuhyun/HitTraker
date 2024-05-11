package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.Video;
import me.zuuhyun.youtubeproject.domain.VideoAd;
import me.zuuhyun.youtubeproject.dto.AddVideoRequest;
import me.zuuhyun.youtubeproject.dto.UpdateVideoRequest;
import me.zuuhyun.youtubeproject.repository.*;
import static me.zuuhyun.youtubeproject.util.Utils.generateRandomNum;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final AdvertisementRepository advertisementRepository;
    private final VideoAdRepository videoAdRepository;

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
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeVideoAuthor(video);
        videoRepository.delete(video);
    }

    @Transactional
    public Video update(long id, UpdateVideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        authorizeVideoAuthor(video);
        video.update(request.getTitle(), request.getContent(), request.getLength(), LocalDateTime.now());

        return video;
    }

    private static void authorizeVideoAuthor(Video video) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!video.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    @Transactional
    public void updateCountVideoView(long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        video.countTotalView();
    }

    @Transactional
    public Video updateCountAdView(long id, long count) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        for (int i = 0; i < count; i++){
            long adId = generateRandomNum(1, advertisementRepository.countAllBy());
            videoAdRepository.save(VideoAd.builder()
                    .videoId(id)
                    .adId(adId)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
        return video;
    }

    public long getVideoLength(long id){
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        return video.getLength();
    }
}
