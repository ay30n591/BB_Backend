package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Comment;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private Long id;
    private String comment;
    private Users user;
    private Feed feed;

    public Comment toEntity(){
        Comment comments = Comment.builder()
                .id(id)
                .contents(comment)
                .user(user)
                .feed(feed)
                .build();
        return comments;
    }
}

