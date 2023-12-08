package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedRequestDto {
    private String content;
    private String imageFileUrl;
    private int feedLike;
    private String videoId;


    public Feed toEntity() {

        Feed feed = new Feed();
        feed.setContent(content);
        feed.setFeedLike(feedLike);
        feed.setVideoId(videoId);

        return feed;
    }

}