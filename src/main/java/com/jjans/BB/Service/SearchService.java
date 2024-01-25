package com.jjans.BB.Service;

import com.jjans.BB.Document.FeedDocument;
import com.jjans.BB.Document.PlaylistDocument;
import com.jjans.BB.Document.TotalDocument;
import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Repository.SearchFeedRepository;
import com.jjans.BB.Repository.SearchPlaylistRepository;
import com.jjans.BB.Repository.SearchTotalRepository;
import com.jjans.BB.Repository.SearchUsersRepository;
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

    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final SearchUsersRepository searchUsersRepository;
    private final SearchFeedRepository searchFeedRepository;
    private final SearchPlaylistRepository searchPlaylistRepository;
    private final SearchTotalRepository searchTotalRepository;

    public List<UsersDocument> findByNickName(String nickname) {

        // 어떤 QueryBuilders -> 제공하는 함수 중에 -> lsc -> ls - X l
        NativeSearchQuery usersearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.wildcardQuery("nick_name", "*" + nickname + "*"))
                .build();

        SearchHits<UsersDocument> usersearchHits = elasticsearchRestTemplate.search(usersearchQuery, UsersDocument.class);

        return usersearchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<FeedDocument> findByFeedKeyword(String keyword) {
        // 검색어를 소문자로 변환
        String lowercaseKeyword = keyword.toLowerCase();

        // 해당 검색어를 tagName, musicArtist, musicTitle에 대해 regexp query로 검색 (대소문자 무시)
        NativeSearchQuery feedSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.wildcardQuery("tag_name.keyword", "*" + lowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("music_artist.keyword", "*" + lowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("music_title.keyword", "*" + lowercaseKeyword + "*")))
                .build();

        SearchHits<FeedDocument> feedSearchHits = elasticsearchRestTemplate.search(feedSearchQuery, FeedDocument.class);

        List<FeedDocument> result = feedSearchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            // 검색 결과가 없을 경우 예외 처리 또는 기본값 반환 등을 수행
            log.warn("검색 결과가 비어있습니다.");
        }

        return result;
    }

    public List<PlaylistDocument> findByPlistKeyword(String keyword) {
        String plistlowercaseKeyword = keyword.toLowerCase();

        NativeSearchQuery plistSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.wildcardQuery("tag_name.keyword", "*" + plistlowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("music_artist.keyword", "*" + plistlowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("music_title.keyword", "*" + plistlowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("title.keyword", "*" + plistlowercaseKeyword + "*")))
                .build();

        SearchHits<PlaylistDocument> plistSearchHits = elasticsearchRestTemplate.search(plistSearchQuery, PlaylistDocument.class);

        List<PlaylistDocument> plistresult = plistSearchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        if (plistresult.isEmpty()) {
            log.warn("검색 결과가 비어있습니다.");
        }
        return plistresult;
    }
    public List<TotalDocument> findByTotalKeyword(String keyword) {
        String totallowercaseKeyword = keyword.toLowerCase();

        NativeSearchQuery totalSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .should(QueryBuilders.wildcardQuery("nick_name.keyword", "*" + totallowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("tag_name.keyword", "*" + totallowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("f_music_artist.keyword", "*" + totallowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("f_music_title.keyword", "*" + totallowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("title.keyword", "*" + totallowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("p_music_artist.keyword", "*" + totallowercaseKeyword + "*"))
                        .should(QueryBuilders.wildcardQuery("p_music_title.keyword", "*" + totallowercaseKeyword + "*")))
                .build();

        SearchHits<TotalDocument> totalSearchHits = elasticsearchRestTemplate.search(totalSearchQuery, TotalDocument.class);

        List<TotalDocument> totalresult = totalSearchHits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        if (totalresult.isEmpty()) {
            log.warn("검색 결과가 비어있습니다.");
        }
        return totalresult;
    }
}