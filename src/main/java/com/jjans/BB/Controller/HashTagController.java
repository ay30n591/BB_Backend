package com.jjans.BB.Controller;

import com.jjans.BB.Dto.HashTagDto;
import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/hashtags")
public class HashTagController {

    private final FeedService hashTagService;

    @Autowired
    public HashTagController(FeedService hashTagService) {
        this.hashTagService = hashTagService;
    }

    @GetMapping
    //모든 해시태그 조회
    public ResponseEntity<List<HashTagDto>> getAllHashTags() {
        List<HashTagDto> hashTags = hashTagService.getAllHashTags();
        return ResponseEntity.ok(hashTags);
    }

    @GetMapping("/{tagName}")
    // 태그명에 해당하는 해시태그 조회
    public ResponseEntity<HashTagDto> getHashTagByName(@PathVariable String tagName) {
        HashTagDto hashTag = hashTagService.getHashTagByName(tagName);
        return ResponseEntity.ok(hashTag);
    }

    @PostMapping
    // 새로운 해시태그 생성
    public ResponseEntity<HashTagDto> saveHashtag(@RequestBody @Valid HashTagDto hashTagDto) {
        HashTagDto createdHashTag = hashTagService.createHashTag(hashTagDto);
        return new ResponseEntity<>(createdHashTag, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    //주어진 id에 해당하는 해시태그 업데이트
//    public ResponseEntity<HashTagDto> updateHashTag(@PathVariable Long id, @RequestBody @Valid HashTagDto updatedHashTagDto) {
//        HashTagDto updatedHashTag = hashTagService.updateHashTag(id, updatedHashTagDto);
//        return ResponseEntity.ok(updatedHashTag);
//    }

    @DeleteMapping("/{id}")
    // 주어진 id에 해당하는 해시태그 삭제
    public ResponseEntity<String> deleteHashTag(@PathVariable Long id) {
        try {
            hashTagService.deleteHashTag(id);
            return ResponseEntity.ok("HashTag deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting HashTag");
        }
    }
}
