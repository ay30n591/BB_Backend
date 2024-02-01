package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PlaylistRequestDto {

    private String content;
    private String imageFileUrl;
    private int feedLike;
    private List<String> videoIds;


    public Playlist toEntity() {

        Playlist playlist = new Playlist();
        playlist.setContent(content);
        playlist.setFeedLike(feedLike);
        playlist.setVideoIds(videoIds);

        return playlist;
    }

}