package com.jjans.BB.Service.Impl;

import com.jjans.BB.Config.Utill.SecurityUtil;
import com.jjans.BB.Dto.CommentRequestDto;
import com.jjans.BB.Dto.CommentResponseDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Entity.Comment;
import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Repository.CommentRepository;
import com.jjans.BB.Repository.FeedRepository;
import com.jjans.BB.Repository.UsersRepository;
import com.jjans.BB.Service.CommentService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final FeedRepository feedRepository;
    private final UsersRepository usersRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(FeedRepository feedRepository, UsersRepository usersRepository, CommentRepository commentRepository) {
        this.feedRepository = feedRepository;
        this.usersRepository = usersRepository;
        this.commentRepository = commentRepository;
    }
    @Override
    public CommentResponseDto saveComments(Long feedId, CommentRequestDto commentDto) {
        String userEmail = SecurityUtil.getCurrentUserEmail();

        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new UsernameNotFoundException("No authentication information."));

        Comment comment = commentDto.toEntity();
        comment.setUser(user);
        comment.setArticle(feed);
        Comment savedComment = commentRepository.save(comment);

        return new CommentResponseDto(savedComment);
    }

    @Override
    public CommentResponseDto updateComments(Long feedId, Long commentId,CommentRequestDto commentDto) {

        String userEmail = SecurityUtil.getCurrentUserEmail();

        Comment existingComment = Optional.ofNullable(commentRepository.findByArticleIdAndId(feedId, commentId))
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (!existingComment.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("불법적인 접근: 해당 유저가 아닙니다.");
        }
        existingComment.setContents(commentDto.getComment());

        Comment updatedComment = commentRepository.save(existingComment);

        return new CommentResponseDto(updatedComment);
    }

    @Override
    public void deleteComment(Long feedId, Long commentId) {
        String userEmail = SecurityUtil.getCurrentUserEmail();

        Comment commentToDelete = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!commentToDelete.getUser().getEmail().equals(userEmail)) {
            throw new IllegalArgumentException("불법적인 접근: 해당 유저가 아닙니다.");
        }

        if (!commentToDelete.getArticle().getId().equals(feedId)) {
            throw new IllegalArgumentException("불법적인 접근: 댓글이 지정된 피드에 속해 있지 않습니다.");
        }

        commentRepository.delete(commentToDelete);

    }
}
