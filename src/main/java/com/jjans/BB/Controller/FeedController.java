package com.jjans.BB.Controller;


import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Entity.Users;
import com.jjans.BB.Service.FeedService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    //     @GetMapping("/{userid}/")
    @GetMapping("/user/{email}")
    public ResponseEntity<List<FeedResponseDto>> getUserFeeds(@PathVariable String email) {
        List<FeedResponseDto> userFeeds = feedService.getUserFeeds(email);
        return ResponseEntity.ok(userFeeds);
    }
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<FeedResponseDto> saveFeed(@RequestBody @Valid FeedRequestDto feedRequestDto) {
        FeedResponseDto savedFeed = feedService.saveFeed(feedRequestDto);
        return new ResponseEntity<>(savedFeed, HttpStatus.CREATED);
    }
    @PutMapping("/{feedId}")
    public ResponseEntity<FeedResponseDto> updateFeed(@PathVariable Long feedId, @RequestBody FeedRequestDto updatedFeedDto) {
        FeedResponseDto updatedFeed = feedService.updateFeed(feedId, updatedFeedDto);
        return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
    }
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
