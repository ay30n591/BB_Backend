package com.jjans.BB.Controller;

import com.jjans.BB.Document.UsersDocument;
import com.jjans.BB.Dto.Response;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Dto.UserResponseDto;
import com.jjans.BB.Service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<UsersDocument>> searchByNickname(@RequestParam String nickname) {
        log.info("닉네임으로 검색 중: {}", nickname);

        List<UsersDocument> searchInfos = searchService.findByNickName(nickname);
        log.info("검색 결과: {}", searchInfos);

        return ResponseEntity.ok(searchInfos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchByName(UserRequestDto.SearchCondition searchCondition, Pageable pageable){
        return ResponseEntity.ok(searchService.searchByCondition(searchCondition,pageable));
    }


}

