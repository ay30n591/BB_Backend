package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Comment;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedRequestDto {
    private Long id;
    private String content;
    private int feedImage;

    public Feed toEntity(){
        Feed feed = Feed.builder()
                .id(id)
                .content(content)
                .feedImage(feedImage)
                .build();
        return feed;
    }
}