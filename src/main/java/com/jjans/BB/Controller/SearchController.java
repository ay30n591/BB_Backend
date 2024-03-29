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
import io.swagger.v3.oas.annotations.Operation;
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
@Slf4j
public class SearchController {
    private final SearchService searchService;
    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/users/nickname")
    @Operation(summary = "유저 닉네임 검색", description = "유저 닉네임 검색")
    public ResponseEntity<List<UsersDocument>> searchByNickname(@RequestParam String nickname) {
        log.info("닉네임으로 검색 중: {}", nickname);
        List<UsersDocument> searchInfos = searchService.findByNickName(nickname);
        log.info("검색 결과: {}", searchInfos);
        return ResponseEntity.ok(searchInfos);
    }

    @GetMapping("/feed/Keyword")
    @Operation(summary = "피드 키워드 검색", description = "피드 키워드 검색")
    public ResponseEntity<List<FeedDocument>> searchByKeyword(@RequestParam String keyword) {
        log.info("Keyword 검색 중: {}", keyword);
        List<FeedDocument> result = searchService.findByFeedKeyword(keyword);
        log.info("Keyword 검색 결과: {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/playlist/Keyword")
    @Operation(summary = "플리 키워드 검색", description = "플리 키워드 검색")
    public ResponseEntity<List<PlaylistDocument>> searchByPlistKeyword(@RequestParam String keyword) {
        log.info("PKeyword 검색 중: {}", keyword);
        List<PlaylistDocument> plistresult = searchService.findByPlistKeyword(keyword);
        log.info("PKeyword 검색 결과: {}", plistresult);
        return new ResponseEntity<>(plistresult, HttpStatus.OK);
    }
    @GetMapping("/total/Keyword")
    @Operation(summary = "전체 키워드 검색", description = "전체 키워드 검색")
    public ResponseEntity<List<TotalDocument>> searchByTotalKeyword(@RequestParam String keyword) {
        log.info("TKeyword 검색 중: {}", keyword);
        List<TotalDocument> totalresult = searchService.findByTotalKeyword(keyword);
        log.info("TKeyword 검색 결과: {}", totalresult);
        return new ResponseEntity<>(totalresult, HttpStatus.OK);
    }
}

