package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Comment;
import com.jjans.BB.Entity.Feed;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class FeedResponseDto {

    private Long id;
    private String contents;
    private int feedLike;
    private String imageFileUrl;
    private Long userId;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.contents = feed.getContent();
        this.feedLike = feed.getFeedLike();
        this.imageFileUrl = feed.getFeedImageUrl();  // 수정된 부분
        this.userId = feed.getUser().getId();
        this.userName = feed.getUser().getUserName();
        this.createdAt = feed.getCreateDate();
        this.modifiedAt = feed.getModifiedDate();

        List<Comment> comments = feed.getComments();
        if (comments != null) {
            this.comments = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        } else {
            this.comments = Collections.emptyList();
        }    }
}
