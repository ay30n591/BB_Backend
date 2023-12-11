package com.jjans.BB.Service;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Dto.HashTagDto;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Repository.FeedRepository;
import com.jjans.BB.Repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface FeedService {
    List<FeedResponseDto> getAllFeeds();
    FeedResponseDto saveFeed(FeedRequestDto feedDto, MultipartFile imageFile);
    FeedResponseDto updateFeed(Long feedId, FeedRequestDto updatedFeedDto);
    List<FeedResponseDto> getUserAllFeeds(String nickname);
    FeedResponseDto getUserFeed(Long feed_id,String nickname);

    List<FeedResponseDto> getMyFeeds();

    void deleteFeed(Long feedId);

    List<HashTagDto> getAllHashTags();
    HashTagDto getHashTagByName(String tagName);

    HashTagDto createHashTag(HashTagDto hashTagDto);

    HashTagDto updateHashTag(Long id, HashTagDto updatedHashTagDto);

    void deleteHashTag(Long id);

    Feed saveHashtag(Feed feed);

    Set<HashTag> extractHashTags(String content);
}
