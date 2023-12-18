package com.jjans.BB.Controller;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    private final FeedService feedService;

    @Autowired
    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }


    @GetMapping
    @Operation(summary = "Get all feeds", description = "모든 피드 가져오기[id 순서. 개인화x]")
    public ResponseEntity<List<FeedResponseDto>> getAllFeeds() {
        List<FeedResponseDto> feeds = feedService.getAllFeeds();
        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my")
    @Operation(summary = "Get my feeds", description = "내 피드 전체 가져오기")
    public ResponseEntity<List<FeedResponseDto>> getMyFeeds() {
        List<FeedResponseDto> userFeeds = feedService.getMyFeeds();
        return ResponseEntity.ok(userFeeds);
    }


    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my/{feed_id}")
    @Operation(summary = "Get my feeds", description = "내 피드  가져오기")
    public ResponseEntity<FeedResponseDto> getMyFeed(@PathVariable Long feed_id) {
        FeedResponseDto myFeed = feedService.getMyFeed(feed_id);
        return ResponseEntity.ok(myFeed);
    }

    @GetMapping("/user/{nickname}")
    @Operation(summary = "Get User's all feeds", description = "유저 피드 전체 가져오기")
    public ResponseEntity<List<FeedResponseDto>> getUserAllFeeds(@PathVariable String nickname) {
        List<FeedResponseDto> userFeeds = feedService.getUserAllFeeds(nickname);
        return ResponseEntity.ok(userFeeds);
    }

    @GetMapping("/user/{nickname}/{feed_id}")
    @Operation(summary = "Get User's feed", description = "유저 피드 하나 가져오기")
    public ResponseEntity<FeedResponseDto> getUserFeed(@PathVariable Long feed_id,
                                                             @PathVariable String nickname) {
        FeedResponseDto userFeed = feedService.getUserFeed(feed_id, nickname);
        return ResponseEntity.ok(userFeed);
    }

    @GetMapping("/byTag/{tagName}")
    public ResponseEntity<List<FeedResponseDto>> getFeedsByTagName(@PathVariable String tagName) {
        List<FeedResponseDto> feedDtos = feedService.findFeedsByTagName(tagName);
        return new ResponseEntity<>(feedDtos, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
    @Operation(summary = "Post User's feed", description = "피드 작성")
    public ResponseEntity<FeedResponseDto> saveFeed(@RequestPart(name = "feedRequestDto", required = true) @Valid FeedRequestDto feedRequestDto
        , @RequestPart(name = "imageFile", required = false) @Valid MultipartFile imageFile){

        FeedResponseDto savedFeed = feedService.saveFeed(feedRequestDto,imageFile);
        return new ResponseEntity<>(savedFeed, HttpStatus.CREATED);
    }



    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Put feed", description = "피드 수정")
    @PutMapping("/{feedId}")
    public ResponseEntity<FeedResponseDto> updateFeed(@PathVariable Long feedId, @RequestBody FeedRequestDto updatedFeedDto) {
        FeedResponseDto updatedFeed = feedService.updateFeed(feedId, updatedFeedDto);
        return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
    }


    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{feedId}")
    @Operation(summary = "Delete feed", description = "피드 삭제")

    public ResponseEntity<String> deleteFeed(@PathVariable Long feedId) {
        try {
            feedService.deleteFeed(feedId);
            return ResponseEntity.ok("Feed deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting feed");
        }
    }

}
