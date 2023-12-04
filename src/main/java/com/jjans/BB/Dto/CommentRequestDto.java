package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private Long id;
    private String comment;


    public Comment toEntity(){
        Comment comments = Comment.builder()
                .id(id)
                .contents(comment)
                .build();
        return comments;
    }

}

