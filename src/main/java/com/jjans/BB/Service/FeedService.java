package com.jjans.BB.Service;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedService {
    List<FeedResponseDto> getAllFeeds();
    FeedResponseDto saveFeed(FeedRequestDto feedDto, MultipartFile imageFile); //
    FeedResponseDto updateFeed(Long feedId, FeedRequestDto updatedFeedDto);
    List<FeedResponseDto> getUserAllFeeds(String nickname);
    FeedResponseDto getUserFeed(Long feed_id,String nickname);
    List<FeedResponseDto> getMyFeeds();
    FeedResponseDto getMyFeed(Long feed_id);
    void deleteFeed(Long feedId);

    List<FeedResponseDto> findFeedsByTagName(String tagName);

}
