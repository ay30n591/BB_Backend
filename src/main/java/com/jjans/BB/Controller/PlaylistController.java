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
    @Operation(summary = "모든 플리 가져오기[최신순]", description = "모든 플리 가져오기[id 순서. 개인화x]")
    public ResponseEntity<List<PlaylistResponseDto>> getAllPls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<PlaylistResponseDto> pls = playlistService.getAllPls(page, size);
        return new ResponseEntity<>(pls, HttpStatus.OK);
    }
    @GetMapping("/orderedByLikeCount")
    @Operation(summary = "모든 플리 가져오기 [좋아요순]", description = "모든 플리 가져오기[id 순서. 개인화x]")
    public ResponseEntity<List<PlaylistResponseDto>> getPlaylistsOrderedByLikeCount(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

            List<PlaylistResponseDto> playlists = playlistService.getArticlesOrderByLikeCount(page, size);
            return new ResponseEntity<>(playlists, HttpStatus.OK);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my/{plId}")
    @Operation(summary = "내 플리 하나 가져오기", description = "내 플리 하나 가져오기")
    public ResponseEntity<PlaylistResponseDto> getMyPl(@PathVariable Long plId) {
        PlaylistResponseDto myPl = playlistService.getMyPl(plId);
        return ResponseEntity.ok(myPl);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/my")
    @Operation(summary = "내 전체 플리 가져오기", description = "내 전체 플리 가져오기")
    public ResponseEntity<List<PlaylistResponseDto>> getMyPls(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        List<PlaylistResponseDto> myPls = playlistService.getMyPls(page,size);
        return ResponseEntity.ok(myPls);
    }


    @GetMapping("/user/{nickname}")
    @Operation(summary = "유저 플리 전체 가져오기", description = "유저 플리 전체 가져오기")
    public ResponseEntity<List<PlaylistResponseDto>> getUserAllPls(@PathVariable String nickname,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        List<PlaylistResponseDto> userPls = playlistService.getUserAllPls(nickname,page, size);
        return ResponseEntity.ok(userPls);
    }

    @GetMapping("/user/{nickname}/{plId}")
    @Operation(summary = "유저 플리 하나 가져오기", description = "유저 플리 하나 가져오기")
    public ResponseEntity<PlaylistResponseDto> getUserPl(@PathVariable Long plId,
                                                       @PathVariable String nickname) {
        PlaylistResponseDto userPl = playlistService.getUserPl(plId, nickname);
        return ResponseEntity.ok(userPl);
    }


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "플리 작성", description = "플리 작성")
    public ResponseEntity<PlaylistResponseDto> savePl(@RequestPart(name = "playlistRequestDto", required = true) @Valid PlaylistRequestDto playlistRequestDto
            , @RequestPart(name = "imageFile", required = false) @Valid MultipartFile imageFile){

        PlaylistResponseDto savePl = playlistService.savePl(playlistRequestDto,imageFile);
        return new ResponseEntity<>(savePl, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "플리 수정", description = "플리 수정")
    @PutMapping("/{plId}")
    public ResponseEntity<PlaylistResponseDto> updatePl(@PathVariable Long plId, @RequestBody PlaylistRequestDto updatedPlDto) {
        PlaylistResponseDto updatedPl = playlistService.updatePl(plId, updatedPlDto);
        return new ResponseEntity<>(updatedPl, HttpStatus.OK);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{plId}")
    @Operation(summary = "플리 삭제", description = "플리 삭제")
    public ResponseEntity<String> deletePl(@PathVariable Long plId) {
        try {
            playlistService.deleteFeed(plId);
            return ResponseEntity.ok("playList deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting playlist");
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{plId}/like")
    @Operation(summary = "플리 좋아요", description = "플리 좋아요 클릭")
    public ResponseEntity<String> likePl(@PathVariable Long plId) {
        try {
            playlistService.likePl(plId);
            return ResponseEntity.ok("Feed liked successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error liking pl");
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{plId}/unlike")
    @Operation(summary = "플리 좋아요 취소", description = "플리 좋아요 취소")
    public ResponseEntity<String> unlikePl(@PathVariable Long plId) {
        try {

            playlistService.unlikePl(plId);
            return ResponseEntity.ok("Feed unliked successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error liking pl");
        }
    }



}
