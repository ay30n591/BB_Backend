package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed,Long> {
    List<Feed> findByUserNickName(String nickname);
    Feed findByIdAndUserNickName(Long id, String nickname);

}
