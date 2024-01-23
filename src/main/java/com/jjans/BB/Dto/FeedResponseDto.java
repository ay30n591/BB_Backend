package com.jjans.BB.Dto;

import com.jjans.BB.Entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FeedResponseDto{

    private Long id;
    private String content;
    private int feedLike;
    private String imageFileUrl;
    private Long userId;
    private boolean likeCheck;
    private List<MusicInfo> musicInfoList;
    private String nickName;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;
    private List<String> tagName;

    public FeedResponseDto(Feed feed) {
        this.id = feed.getId();
        this.content = feed.getContent();
        this.feedLike = feed.getLikes().size(); // feed의 좋아요 수를 유저 수로 변경
        this.imageFileUrl = feed.getImageUrl();  // 수정된 부분
        this.userId = feed.getUser().getId();
        this.nickName = feed.getUser().getNickName();
        this.createdAt = feed.getCreateDate();
        this.modifiedAt = feed.getModifiedDate();
        this.musicInfoList = Collections.singletonList(feed.getMusicInfo());
        // Set<HashTag>에서 각 HashTag의 tagName을 추출하여 List<String>으로 변환
        List<String> tagNames = feed.getHashTags().stream()
                .map(HashTag::getTagName)
                .collect(Collectors.toList());
        // List<String>을 쉼표로 구분된 하나의 문자열로 결합
        this.tagName = tagNames;

        List<Comment> comments = feed.getComments();
        if (comments != null) {
            this.comments = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        } else {
            this.comments = Collections.emptyList();
        }    }
}
