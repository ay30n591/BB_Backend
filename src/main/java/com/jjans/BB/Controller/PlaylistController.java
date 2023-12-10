package com.jjans.BB.Controller;


import com.jjans.BB.Dto.FeedRequestDto;
import com.jjans.BB.Dto.FeedResponseDto;
import com.jjans.BB.Dto.PlaylistRequestDto;
import com.jjans.BB.Dto.PlaylistResponseDto;
import com.jjans.BB.Service.PlaylistService;
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
@RequestMapping("/api/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    @Operation(summary = "Get all playlists", description = "모든 플리 가져오기[id 순서. 개인화x]")
    public ResponseEntity<List<PlaylistResponseDto>> getAllPls() {
        List<PlaylistResponseDto> pls = playlistService.getAllPls();
        return new ResponseEntity<>(pls, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user/{plId}")
    @Operation(summary = "Get my playlist", description = "내 플리 가져오기")
    public ResponseEntity<PlaylistResponseDto> getMyPl(@PathVariable Long plId) {
        PlaylistResponseDto myPl = playlistService.getMyPl(plId);
        return ResponseEntity.ok(myPl);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/user/{plId}")
    @Operation(summary = "Get my playlist", description = "내 플리 가져오기")
    public ResponseEntity<List<PlaylistResponseDto>> getMyPls() {
        List<PlaylistResponseDto> myPls = playlistService.getMyPls();
        return ResponseEntity.ok(myPls);
    }


    @GetMapping("/user/{nickname}")
    @Operation(summary = "Get User's all playlists", description = "유저 플리 전체 가져오기")
    public ResponseEntity<List<PlaylistResponseDto>> getUserAllFeeds(@PathVariable String nickname) {
        List<PlaylistResponseDto> userPls = playlistService.getUserAllPls(nickname);
        return ResponseEntity.ok(userPls);
    }

    @GetMapping("/user/{nickname}/{pl_id}")
    @Operation(summary = "Get User's playlist", description = "유저 플리 하나 가져오기")
    public ResponseEntity<PlaylistResponseDto> getUserFeed(@PathVariable Long pl_id,
                                                       @PathVariable String nickname) {
        PlaylistResponseDto userPl = playlistService.getUserPl(pl_id, nickname);
        return ResponseEntity.ok(userPl);
    }


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "Post User's playlist", description = "플리 작성 가져오기")
    public ResponseEntity<PlaylistResponseDto> saveFeed(@RequestPart(name = "playlistRequestDto", required = true) @Valid PlaylistRequestDto playlistRequestDto
            , @RequestPart(name = "imageFile", required = false) @Valid MultipartFile imageFile){

        PlaylistResponseDto savePl = playlistService.savePl(playlistRequestDto,imageFile);
        return new ResponseEntity<>(savePl, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Put playlist", description = "플리 수정")
    @PutMapping("/{plId}")
    public ResponseEntity<PlaylistResponseDto> updateFeed(@PathVariable Long plId, @RequestBody PlaylistRequestDto updatedPlDto) {
        PlaylistResponseDto updatedPl = playlistService.updatePl(plId, updatedPlDto);
        return new ResponseEntity<>(updatedPl, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{plId}")
    @Operation(summary = "Delete playlist", description = "플리 삭제")

    public ResponseEntity<String> deleteFeed(@PathVariable Long plId) {
        try {
            playlistService.deleteFeed(plId);
            return ResponseEntity.ok("playList deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting playlist");
        }
    }

}
