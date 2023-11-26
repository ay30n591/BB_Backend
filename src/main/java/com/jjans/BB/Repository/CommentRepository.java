package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Comment;
import com.jjans.BB.Entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface CommentRepository extends JpaRepository<Comment,Long> {
}

