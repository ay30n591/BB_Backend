package com.jjans.BB.Dto;


import com.jjans.BB.Entity.Comment;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Playlist;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PlaylistResponseDto {

    private Long id;
    private String contents;
    private int feedLike;
    private String imageFileUrl;
    private Long userId;
    private String userName;
    private List<String> vedioIds;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;


    public PlaylistResponseDto(Playlist playlist) {
        this.id = playlist.getId();
        this.contents = playlist.getContent();
        this.feedLike = playlist.getFeedLike();
        this.imageFileUrl = playlist.getImageUrl();  // 수정된 부분
        this.userId = playlist.getUser().getId();
        this.userName = playlist.getUser().getUserName();
        this.vedioIds = playlist.getVideoIds();
        this.createdAt = playlist.getCreateDate();
        this.modifiedAt = playlist.getModifiedDate();

        List<Comment> comments = playlist.getComments();
        if (comments != null) {
            this.comments = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        } else {
            this.comments = Collections.emptyList();
        }    }

}
