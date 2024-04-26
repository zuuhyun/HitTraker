package me.zuuhyun.youtubeproject.service;

import lombok.RequiredArgsConstructor;
import me.zuuhyun.youtubeproject.domain.UserHistory;
import me.zuuhyun.youtubeproject.repository.UserHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;

    @Transactional
    public UserHistory getUserHistory(long userId, long videoId) {
        return userHistoryRepository.findByUserIdAndVideoId(userId, videoId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userId + videoId));
    }

    @Transactional
    public UserHistory updateViewTime(long userId, long videoId, long viewTime) {
        UserHistory userHistory = userHistoryRepository.findByUserIdAndVideoId(userId, videoId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userId + videoId));
        userHistory.updateViewingTime(viewTime);

        return userHistory;
    }

    @Transactional
    public UserHistory saveUserHistory(long userId, long videoId) {
        return userHistoryRepository.save(UserHistory.builder()
                .userId(userId)
                .videoId(videoId)
                .viewingTime(0L)
                .videoTimestamp(0L)
                .build());
    }
}
