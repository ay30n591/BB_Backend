package com.jjans.BB.Controller;

import com.jjans.BB.Entity.UserFollower;
import com.jjans.BB.Service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Follow a user", description = "Follow a user with the specified email.")
    @PostMapping("/follow")
    public ResponseEntity<?> followUser(@RequestParam String followerEmail) {
        followService.followUser(followerEmail);
        return ResponseEntity.ok("Successfully followed the user.");
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Unfollow a user", description = "Unfollow a user with the specified email.")
    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollowUser(@RequestParam String followerEmail) {
        followService.unfollowUser(followerEmail);
        return ResponseEntity.ok("Successfully unfollowed the user.");
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get followers", description = "Get the list of users following the current user.")
    @GetMapping("/followInfo")
    public ResponseEntity<?>  getFollowers() {
        return ResponseEntity.ok(followService.getFollowInfo());
    }




}
