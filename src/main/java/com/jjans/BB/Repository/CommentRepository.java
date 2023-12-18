package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findByArticleIdAndId(Long feedId, Long commentId);

}

