//package com.jjans.BB.Service.Impl;
//
//import com.jjans.BB.Entity.MusicInfo;
//import com.jjans.BB.Repository.MusicInfoRepository;
//import com.jjans.BB.Service.MusicInfoService;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//@Service
//public class MusicInfoServiceImpl implements MusicInfoService {
//
//
//    private final MusicInfoRepository musicInfoRepository;
//    private final ElasticsearchRestTemplate elasticsearchTemplate;
//    @Autowired
//    public MusicInfoServiceImpl(MusicInfoRepository musicInfoRepository, ElasticsearchRestTemplate elasticsearchTemplate) {
//        this.musicInfoRepository = musicInfoRepository;
//        this.elasticsearchTemplate = elasticsearchTemplate;
//    }
//
//    @Override
//    public List<MusicInfo> findByMusicFileName(String musicFileName) {
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchPhrasePrefixQuery("musicFileName", musicFileName))
//                .build();
//
//        SearchHits<MusicInfo> searchHits = elasticsearchTemplate.search(searchQuery, MusicInfo.class);
//        return searchHits.getSearchHits().stream()
//                .map(SearchHit::getContent)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<MusicInfo> findByArtist(String artist) {
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchPhrasePrefixQuery("artist", artist))
//                .withQuery(QueryBuilders.wildcardQuery("artist", "*" + artist + "*"))
//                .build();
//
//        SearchHits<MusicInfo> searchHits = elasticsearchTemplate.search(searchQuery, MusicInfo.class);
//        return searchHits.getSearchHits().stream()
//                .map(SearchHit::getContent)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<MusicInfo> saveAll(List<MusicInfo> musicInfos) {
//        return (List<MusicInfo>) musicInfoRepository.saveAll(musicInfos);
//    }
//}
