package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface FeedRepository extends JpaRepository<Feed,Long> {
    Page<Feed> findAll(Pageable pageable);
    Page<Feed> findByUserNickName(String nickname,Pageable pageable);

    Feed findByIdAndUserNickName(Long id, String nickname);

    Page<Feed> findByHashTags_TagName(String tagName,Pageable pageable);

    Page<Feed> findByBookMarks_User(Users user,Pageable pageable);

    Page<Feed> findByUserIn(Set<Users> users, Pageable pageable);

}
