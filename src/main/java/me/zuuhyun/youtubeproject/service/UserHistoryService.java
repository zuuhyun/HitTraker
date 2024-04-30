package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.UserHistory;
import me.zuuhyun.youtubeproject.repository.UserHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;

    @Transactional
    public UserHistory getUserHistory(long userId, long videoId) {
        return userHistoryRepository.findFirstByUserIdAndVideoIdOrderByVideoTimestampDesc(userId, videoId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userId + videoId));
    }

    @Transactional
    public void updateViewTime(long userId, long videoId, long viewTime, long videoLength) {
        UserHistory userHistory = userHistoryRepository.findFirstByUserIdAndVideoIdOrderByVideoTimestampDesc(userId, videoId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userId + videoId));
        long newViewTime = userHistory.getViewingTime() + viewTime;
        if (newViewTime > videoLength) {
            newViewTime = videoLength;
        }
        userHistory.updateViewingTime(newViewTime);
    }

    @Transactional
    public UserHistory saveUserHistory(long userId, long videoId) {
        return userHistoryRepository.save(UserHistory.builder()
                .userId(userId)
                .videoId(videoId)
                .viewingTime(0L)
                .videoTimestamp(LocalDateTime.now())
                .build());
    }

    @Transactional
    public UserHistory getLatestUserHistory(long userId, long videoId) {
        return userHistoryRepository.findFirstByUserIdAndVideoIdOrderByVideoTimestampDesc(userId, videoId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userId + videoId));
    }
}
