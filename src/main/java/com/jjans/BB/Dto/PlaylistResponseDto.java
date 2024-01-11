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
public class PlaylistResponseDto {

    private Long id;
    private String contents;
    private String title;
    private int plLike;
    private boolean likeCheck;
    private String imageFileUrl;
    private Long userId;
    private String userName;

    private List<MusicInfo> musicInfoList;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;
    private List<String> tagName;

    public PlaylistResponseDto(Playlist playlist) {
        this.id = playlist.getId();
        this.title = playlist.getTitle();
        this.contents = playlist.getContent();
        this.plLike = playlist.getLikes().size();
        this.imageFileUrl = playlist.getImageUrl();
        this.userId = playlist.getUser().getId();
        this.userName = playlist.getUser().getUserName();
        this.musicInfoList = playlist.getMusicInfoList(); // Updated field name
        this.createdAt = playlist.getCreateDate();
        this.modifiedAt = playlist.getModifiedDate();

        List<String> tagNames = playlist.getHashTags().stream()
                .map(HashTag::getTagName)
                .collect(Collectors.toList());
        // List<String>을 쉼표로 구분된 하나의 문자열로 결합
        this.tagName = tagNames;

        List<Comment> comments = playlist.getComments();
        if (comments != null) {
            this.comments = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
        } else {
            this.comments = Collections.emptyList();
        }
    }
}
