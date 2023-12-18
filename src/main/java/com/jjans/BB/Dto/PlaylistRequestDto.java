package com.jjans.BB.Dto;

import com.jjans.BB.Entity.MusicInfo;
import com.jjans.BB.Entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PlaylistRequestDto {

    private String title;
    private String content;
    private String imageFileUrl;
    private int feedLike;
    private List<MusicInfo> musicInfoList;


    public Playlist toEntity() {

        Playlist playlist = new Playlist();
        playlist.setTitle(title);
        playlist.setContent(content);
        playlist.setFeedLike(feedLike);
        playlist.setMusicInfoList(musicInfoList);

        return playlist;
    }

}