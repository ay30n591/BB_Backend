package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findByUserNickName(String nickname, Pageable pageable);
    Playlist findByIdAndUserNickName(Long id, String nickname);
    Page<Playlist> findAllByOrderByCreateDateDesc(Pageable pageable);

    @Query("SELECT p FROM Playlist p ORDER BY p.likes.size DESC")
    Page<Playlist> findAllOrderByLikeCountDesc(Pageable pageable);


}
