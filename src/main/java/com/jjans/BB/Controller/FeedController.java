package com.jjans.BB.Controller;

import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Service.FeedService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<FeedResponseDto>> getAllFeeds() {
        List<FeedResponseDto> feeds = feedService.getAllFeeds();
        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }

//    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user")
    public ResponseEntity<List<FeedResponseDto>> getMyFeeds() {
        List<FeedResponseDto> userFeeds = feedService.getMyFeeds();
        return ResponseEntity.ok(userFeeds);
    }

    @GetMapping("/user/{nickname}/{feed_id}")
    public ResponseEntity<FeedResponseDto> getUserFeed(@PathVariable Long feed_id,
                                                             @PathVariable String nickname) {
        FeedResponseDto userFeed = feedService.getUserFeed(feed_id, nickname);
        return ResponseEntity.ok(userFeed);
    }

    @GetMapping("/user/{nickname}")
    public ResponseEntity<List<FeedResponseDto>> getUserAllFeeds(@PathVariable String nickname) {
        List<FeedResponseDto> userFeeds = feedService.getUserAllFeeds(nickname);
        return ResponseEntity.ok(userFeeds);
    }


//    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = { "multipart/form-data","application/json" })
    public ResponseEntity<FeedResponseDto> saveFeed(        @RequestPart(name = "feedRequestDto", required = true) @Valid FeedRequestDto feedRequestDto,
                                                            @RequestPart (name = "imageFile", required = false) MultipartFile imageFile) {
        FeedResponseDto savedFeed = feedService.saveFeed(feedRequestDto,imageFile);
        return new ResponseEntity<>(savedFeed, HttpStatus.CREATED);
    }


//    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{feedId}")
    public ResponseEntity<FeedResponseDto> updateFeed(@PathVariable Long feedId, @RequestBody FeedRequestDto updatedFeedDto) {
        FeedResponseDto updatedFeed = feedService.updateFeed(feedId, updatedFeedDto);
        return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
    }
//    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{feedId}")
    public ResponseEntity<String> deleteFeed(@PathVariable Long feedId) {
        try {
            feedService.deleteFeed(feedId);
            return ResponseEntity.ok("Feed deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting feed");
        }
    }

}
