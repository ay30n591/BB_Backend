package com.jjans.BB.Repository;

import com.jjans.BB.Document.TotalDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchTotalRepository extends ElasticsearchRepository<TotalDocument,String> {
}
