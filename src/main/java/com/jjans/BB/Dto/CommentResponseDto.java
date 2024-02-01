package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CommentResponseDto {
    private Long id;
    private Long feedId;
    private String comment;
    private String nickName;
    private String userImgSrc;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getContents();
        this.nickName = comment.getUser().getNickName();
        this.userImgSrc = comment.getUser().getUserImgSrc();
        this.feedId = comment.getArticle().getId();
        this.createdAt = comment.getCreateDate();
        this.modifiedAt = comment.getModifiedDate();
    }
}
