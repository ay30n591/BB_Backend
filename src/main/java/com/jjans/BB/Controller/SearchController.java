package com.jjans.BB.Controller;

import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Dto.Response;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Dto.UserResponseDto;
import com.jjans.BB.Service.SearchService;
import com.jjans.BB.Service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;
    private final Response response;

    //닉네임 검색
    @GetMapping("/users/nickname")
    public ResponseEntity<List<com.jjans.BB.Document.UsersDocument>> searchByNickname(@RequestParam String nickname) {
        log.info("닉네임으로 검색 중: {}", nickname);

        List<UsersDocument> searchInfos = searchService.findByNickName(nickname);
        log.debug("검색 결과: {}", searchInfos);

        return ResponseEntity.ok(searchInfos);
    }
//
//    @GetMapping("/users/search/nickname")
//    public ResponseEntity<List<UserResponseDto.searchInfo>> searchByNickname(@RequestParam String nickname) {
//        log.info("닉네임으로 검색 중: {}", nickname);
//        List<UserResponseDto.searchInfo> searchInfos = usersService.findByNickName(nickname);
//        log.debug("검색 결과: {}", searchInfos);
//        return ResponseEntity.ok(searchInfos);
//    }
//
//
//    @GetMapping("/users/search")
//    public ResponseEntity<List<UserResponseDto>> searchByName(UserRequestDto.SearchCondition searchCondition, Pageable pageable){
//        return ResponseEntity.ok(usersService.searchByCondition(searchCondition,pageable));
//    }
//
//    @GetMapping("/api/users/search/nickname")
//    public ResponseEntity<String> checkUserExistsInElasticsearch(@RequestParam String nickname) {
//        // Elasticsearch에 데이터가 존재하는지 확인
//        List<UserResponseDto.searchInfo> searchInfos = usersService.findByNickName(nickname);
//
//        if (searchInfos.isEmpty()) {
//            return ResponseEntity.ok("해당 닉네임의 사용자는 Elasticsearch에 존재하지 않습니다.");
//        } else {
//            return ResponseEntity.ok("해당 닉네임의 사용자가 Elasticsearch에 존재합니다.");
//        }
//    }

}

