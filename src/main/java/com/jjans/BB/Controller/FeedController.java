package com.jjans.BB.Controller;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    @Operation(summary = "모든 피드 가져오기", description = "모든 피드 가져오기[id 순서. 개인화x]")
    public ResponseEntity<List<FeedResponseDto>> getAllFeeds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<FeedResponseDto> feeds = feedService.getAllFeeds(page,size);
        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/followings")
    @Operation(summary = "팔로우한 유저 피드 가져오기", description = "팔로잉한 유저들 피드 가져오기[id 순서. 개인화x]")
    public ResponseEntity<List<FeedResponseDto>> getFeedsOfFollowing(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size) {
        List<FeedResponseDto> feedsOfFollowers = feedService.getFeedsOfFollowing(page, size);
        return new ResponseEntity<>(feedsOfFollowers, HttpStatus.OK);
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my")
    @Operation(summary = "내 전체 피드 가져오기", description = "내 피드 전체 가져오기")
    public ResponseEntity<List<FeedResponseDto>> getMyFeeds(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        List<FeedResponseDto> userFeeds = feedService.getMyFeeds(page, size);
        return ResponseEntity.ok(userFeeds);
    }


    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my/{feed_id}")
    @Operation(summary = "내 피드 하나 가져오기", description = "내 피드  가져오기")
    public ResponseEntity<FeedResponseDto> getMyFeed(@PathVariable Long feed_id) {
        FeedResponseDto myFeed = feedService.getMyFeed(feed_id);
        return ResponseEntity.ok(myFeed);
    }

    @GetMapping("/user/{nickname}")
    @Operation(summary = "유저 피드 전체 가져오기", description = "유저 피드 전체 가져오기")
    public ResponseEntity<List<FeedResponseDto>> getUserAllFeeds(@PathVariable String nickname,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        List<FeedResponseDto> userFeeds = feedService.getUserAllFeeds(nickname,page,size);
        return ResponseEntity.ok(userFeeds);
    }

    @GetMapping("/user/{nickname}/{feed_id}")
    @Operation(summary = "유저 피드 하나 가져오기", description = "유저 피드 하나 가져오기")
    public ResponseEntity<FeedResponseDto> getUserFeed(@PathVariable Long feed_id,
                                                             @PathVariable String nickname) {
        FeedResponseDto userFeed = feedService.getUserFeed(feed_id, nickname);
        return ResponseEntity.ok(userFeed);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE })
    @Operation(summary = "피드 작성", description = "피드 작성")
    public ResponseEntity<FeedResponseDto> saveFeed(@RequestPart(name = "feedRequestDto", required = true) @Valid FeedRequestDto feedRequestDto
        , @RequestPart(name = "imageFile", required = false) @Valid MultipartFile imageFile){

        FeedResponseDto savedFeed = feedService.saveFeed(feedRequestDto,imageFile);
        return new ResponseEntity<>(savedFeed, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{feedId}/like")
    @Operation(summary = "피드 좋아요", description = "피드 좋아요 클릭")
    public ResponseEntity<String> likeFeed(@PathVariable Long feedId) {
        try {
            feedService.likeFeed(feedId);
            return ResponseEntity.ok("Feed liked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error liking feed");
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{feedId}/unlike")
    @Operation(summary = "피드 좋아요 취소", description = "피드 좋아요 취소")
    public ResponseEntity<String> unlikeFeed(@PathVariable Long feedId) {
            feedService.unlikeFeed(feedId);
            return ResponseEntity.ok("Feed unliked successfully");
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "피드 수정", description = "피드 수정")
    @PutMapping("/{feedId}")
    public ResponseEntity<FeedResponseDto> updateFeed(@PathVariable Long feedId, @RequestBody FeedRequestDto updatedFeedDto) {
        FeedResponseDto updatedFeed = feedService.updateFeed(feedId, updatedFeedDto);
        return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
    }


    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{feedId}")
    @Operation(summary = "피드 삭제", description = "피드 삭제")

    public ResponseEntity<String> deleteFeed(@PathVariable Long feedId) {
        try {
            feedService.deleteFeed(feedId);
            return ResponseEntity.ok("Feed deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting feed");
        }
    }
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{feedId}/bookmark")
    @Operation(summary = "피드 북마크", description = "피드 북마크")
    public ResponseEntity<String> bookmarkFeed(@PathVariable Long feedId) {
        try {
            feedService.bookmarkFeed(feedId);
            return new ResponseEntity<>("피드가 성공적으로 북마크되었습니다", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("피드 북마크에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 피드 북마크 해제하기
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{feedId}/unbookmark")
    @Operation(summary = "피드 북마크 해제", description = "피드 북마크 해제")

    public ResponseEntity<String> unbookmarkFeed(@PathVariable Long feedId) {
        try {
            feedService.unbookmarkFeed(feedId);
            return new ResponseEntity<>("피드 북마크가 성공적으로 해제되었습니다", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("피드 북마크 해제에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/bookmarked")
    @Operation(summary = "북마크한 피드 가져오기", description = "북마크한 피드 가져오기")

    public ResponseEntity<List<FeedResponseDto>> getBookmarkedFeeds( @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {

            List<FeedResponseDto> bookmarkedFeeds = feedService.getBookmarkedFeeds(page, size);
            return new ResponseEntity<>(bookmarkedFeeds, HttpStatus.OK);
    }

}
