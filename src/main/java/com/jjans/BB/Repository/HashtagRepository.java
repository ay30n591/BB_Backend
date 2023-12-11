package com.jjans.BB.Repository;

import com.jjans.BB.Entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<HashTag, Long>{
    Optional<HashTag> findByTagName(String tagName);
}

