package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findByUserNickName(String nickname);
    Feed findByIdAndUserNickName(Long id, String nickname);

    Optional<List<HashTag>> findByTagName(String tagName);

}
