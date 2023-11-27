package com.jjans.BB.Service;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Entity.Users;

import java.util.List;

public interface FeedService {
    List<FeedResponseDto> getAllFeeds();
    FeedResponseDto saveFeed(FeedRequestDto feedDto);
    FeedResponseDto updateFeed(Long feedId, FeedRequestDto updatedFeedDto);
    List<FeedResponseDto> getUserFeeds(String email);
    List<FeedResponseDto> getMyFeeds();

    void deleteFeed(Long feedId);

}
