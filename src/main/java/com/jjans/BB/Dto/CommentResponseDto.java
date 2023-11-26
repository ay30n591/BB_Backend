package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Comment;
import lombok.Getter;


@Getter
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String nickName;
    private Long feedId;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getContents();
        this.nickName = comment.getUser().getNickName();
        this.feedId = comment.getFeed().getId();

    }
}
