package com.jjans.BB.Service;

import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    @Autowired
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final SearchRepository SearchRepository;

    public List<UsersDocument> findByNickName(String nickname) {

        // 어떤 QueryBuilders -> 제공하는 함수 중에 -> lsc -> ls - X l
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhrasePrefixQuery("nick_name", nickname))
                .build();

        SearchHits<UsersDocument> searchHits = elasticsearchRestTemplate.search(searchQuery, UsersDocument.class);

        return searchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
