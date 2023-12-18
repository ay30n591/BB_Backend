package com.jjans.BB.Repository;

import com.jjans.BB.Entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    HashTag findByTagName(String tagName);
}