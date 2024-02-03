package com.jjans.BB.Service;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedService {
    List<FeedResponseDto> getAllFeeds(int page, int size);
    FeedResponseDto saveFeed(FeedRequestDto feedDto, MultipartFile imageFile); //
    FeedResponseDto updateFeed(Long feedId, FeedRequestDto updatedFeedDto, MultipartFile imageFile);
    List<FeedResponseDto> getUserAllFeeds(String nickname,int page, int size);
    FeedResponseDto getUserFeed(Long feed_id,String nickname);
    List<FeedResponseDto> getMyFeeds(int page, int size);
    FeedResponseDto getMyFeed(Long feed_id);
    void deleteFeed(Long feedId);

    List<FeedResponseDto> findFeedsByTagName(String tagName,int page, int size);

    // 좋아요
    void likeFeed(Long feedId);
    void unlikeFeed(Long feedId);

    // 북마크
    void bookmarkFeed(Long feedId);
    void unbookmarkFeed(Long feedId);
    List<FeedResponseDto> getBookmarkedFeeds(int page, int size);
    // 팔로워 피드
    List<FeedResponseDto> getFeedsOfFollowing(int page, int size);

}
