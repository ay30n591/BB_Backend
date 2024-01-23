package com.jjans.BB.Controller;

import com.jjans.BB.Document.FeedDocument;
//import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Document.PlaylistDocument;
import com.jjans.BB.Document.TotalDocument;
import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Dto.Response;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Dto.UserResponseDto;
import com.jjans.BB.Service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/search")
//@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;
//    private final Response response;

    //닉네임 검색
    @GetMapping("/users/nickname")
    public ResponseEntity<List<UsersDocument>> searchByNickname(@RequestParam String nickname) {
        log.info("닉네임으로 검색 중: {}", nickname);

        List<UsersDocument> searchInfos = searchService.findByNickName(nickname);
        log.info("검색 결과: {}", searchInfos);

        return ResponseEntity.ok(searchInfos);
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<UserResponseDto>> searchByName(UserRequestDto.SearchCondition searchCondition, Pageable pageable){
//        return ResponseEntity.ok(searchService.searchByCondition(searchCondition,pageable));
//    }

//    tag 검색
//    @GetMapping("/feed/tagName")
//    public ResponseEntity<List<FeedDocument>> searchByTagName(@RequestParam String tagName) {
//        log.info("tag로 검색 중: {}", tagName);
//
//        List<FeedDocument> feedtagsearchInfos = searchService.findByTagName(tagName);
//        log.info("tag 검색 결과: {}", feedtagsearchInfos);
//
//        return ResponseEntity.ok(feedtagsearchInfos);
//    }
//    @GetMapping("/feed/musicTitle")
//    public ResponseEntity<List<FeedDocument>> searchByMusicTitle(@RequestParam String musicTitle) {
//        log.info("tag로 검색 중: {}", musicTitle);
//
//        List<FeedDocument> feedmusicsearchInfos = searchService.findByMusicTitle(musicTitle);
//        log.info("tag 검색 결과: {}", feedmusicsearchInfos);
//
//        return ResponseEntity.ok(feedmusicsearchInfos);
//    }
//    @GetMapping("/feed/musicArtist")
//    public ResponseEntity<List<FeedDocument>> searchByMusicArtist(@RequestParam String musicArtist) {
//        log.info("Artist로 검색 중: {}", musicArtist);
//
//        List<FeedDocument> feedartistsearchInfos = searchService.findByMusicArtist(musicArtist);
//        log.info("Artist로 검색 결과: {}", feedartistsearchInfos);
//
//        return ResponseEntity.ok(feedartistsearchInfos);
//    }

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/feed/Keyword")
    public ResponseEntity<List<FeedDocument>> searchByKeyword(@RequestParam String keyword) {
        log.info("Keyword 검색 중: {}", keyword);
        List<FeedDocument> result = searchService.findByFeedKeyword(keyword);
        log.info("Keyword 검색 결과: {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/playlist/Keyword")
    public ResponseEntity<List<PlaylistDocument>> searchByPlistKeyword(@RequestParam String keyword) {
        log.info("PKeyword 검색 중: {}", keyword);
        List<PlaylistDocument> plistresult = searchService.findByPlistKeyword(keyword);
        log.info("PKeyword 검색 결과: {}", plistresult);
        return new ResponseEntity<>(plistresult, HttpStatus.OK);
    }
    @GetMapping("/total/Keyword")
    public ResponseEntity<List<TotalDocument>> searchByTotalKeyword(@RequestParam String keyword) {
        log.info("TKeyword 검색 중: {}", keyword);
        List<TotalDocument> totalresult = searchService.findByTotalKeyword(keyword);
        log.info("TKeyword 검색 결과: {}", totalresult);
        return new ResponseEntity<>(totalresult, HttpStatus.OK);
    }
}

