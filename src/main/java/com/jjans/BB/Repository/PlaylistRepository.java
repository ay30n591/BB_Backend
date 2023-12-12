package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUserNickName(String nickname);
    Playlist findByIdAndUserNickName(Long id, String nickname);

}
