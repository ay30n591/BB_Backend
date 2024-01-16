package com.jjans.BB.Repository;

import com.jjans.BB.Entity.UsersDocument;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UsersSearchRepository extends ElasticsearchRepository<UsersDocument,Long> {
    List<UsersDocument> findByNickName(String nickname);
//    List<UsersDocument> findByNickName(String nickname, Pageable pageable);
}
