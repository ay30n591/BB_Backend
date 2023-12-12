//package com.jjans.BB.Controller;
//
//
//import com.jjans.BB.Entity.MusicInfo;
//import com.jjans.BB.Service.MusicInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/music-info")
//public class MusicInfoController {
//
//    private final MusicInfoService musicInfoService;
//
//    @Autowired
//    public MusicInfoController(MusicInfoService musicInfoService) {
//        this.musicInfoService = musicInfoService;
//    }
//
//    @GetMapping("/search-by-music-filename")
//    public List<MusicInfo> searchByMusicFileName(@RequestParam String musicFileName) {
//        return musicInfoService.findByMusicFileName(musicFileName);
//    }
//
//    @GetMapping("/search-by-artist")
//    public List<MusicInfo> searchByArtist(@RequestParam String artist) {
//        return musicInfoService.findByArtist(artist);
//    }
//
//    @PostMapping("/save")
//    public ResponseEntity<List<MusicInfo>> saveMusicInfo(@RequestBody List<MusicInfo> musicInfoList) {
//        List<MusicInfo> savedMusicInfoList = musicInfoService.saveAll(musicInfoList);
//        return new ResponseEntity<>(savedMusicInfoList, HttpStatus.CREATED);
//    }
//}
//
