package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.HashTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedRequestDto {
    private Long id;
    private String content;
    private String imageFileUrl;
    private String musicFileUrl;
    private String musicFileName;
    private Set<String> hashTags;


    public Feed toEntity() {
        Feed feed = Feed.builder()
                .id(id)
                .content(content)
                .feedImageUrl(imageFileUrl)
                .musicFileName(musicFileName)
                .musicFileUrl(musicFileUrl)
                .build();
        if (hashTags != null && !hashTags.isEmpty()) {
            Set<HashTag> hashTagEntities = hashTags.stream()
                    .map(tagName -> new HashTag(tagName))
                    .collect(Collectors.toSet());
            feed.setHashTags(hashTagEntities);
        }
        return feed;
    }

}