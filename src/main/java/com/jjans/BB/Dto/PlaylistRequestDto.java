package com.jjans.BB.Dto;

import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Entity.MusicInfo;
import com.jjans.BB.Entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PlaylistRequestDto {

    private String title;
    private String content;
    private String imageFileUrl;
    private int feedLike;
    private List<MusicInfo> musicInfoList;
    private List<HashTag> hashTags; // Change to List<HashTag>


    public Playlist toEntity() {

        Playlist playlist = new Playlist();
        playlist.setTitle(title);
        playlist.setContent(content);
        playlist.setFeedLike(feedLike);
        playlist.setMusicInfoList(musicInfoList);
        Set<HashTag> hashTagSet = hashTags.stream()
                .map(tag -> {
                    HashTag hashTag = new HashTag();
                    hashTag.setTagName(tag.getTagName());
                    return hashTag;
                })
                .collect(Collectors.toSet());
        playlist.setHashTags(hashTagSet);
        return playlist;
    }

}