package com.jjans.BB.Repository;

import com.jjans.BB.Document.FeedDocument;
import com.jjans.BB.Document.UsersDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchUsersRepository extends ElasticsearchRepository<UsersDocument,String> {

}
// Long 형태로 -> String

// 엘라스틱 서치는 기본적으로 findby -> 안써
// elasticTemplate -> s
// elasticsearch 의 데이터를 가져올 수 있어