package com.jjans.BB.Service;

import com.jjans.BB.Dto.CommentRequestDto;
import com.jjans.BB.Dto.CommentResponseDto;


public interface CommentService{
    CommentResponseDto saveComments(Long feedId,CommentRequestDto comment);
    CommentResponseDto updateComments(Long feedId,Long commentId, CommentRequestDto comment);

    void deleteComment(Long feedId, Long commentId);

}
