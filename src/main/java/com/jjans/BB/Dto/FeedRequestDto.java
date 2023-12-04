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
@Builder
public class FeedRequestDto {
    private Long id;
    private String content;
    private String imageFileUrl;

    public Feed toEntity() {
        Feed feed = Feed.builder()
                .id(id)
                .content(content)
                .feedImageUrl(imageFileUrl)
                .build();
        return feed;
    }

}