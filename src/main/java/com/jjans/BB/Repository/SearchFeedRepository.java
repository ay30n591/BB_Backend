package com.jjans.BB.Repository;

import com.jjans.BB.Document.FeedDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

public interface SearchFeedRepository extends ElasticsearchRepository <FeedDocument, String> {

}
