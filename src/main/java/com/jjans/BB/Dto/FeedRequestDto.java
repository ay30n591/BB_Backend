package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.HashTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedRequestDto {

    private String content;
    private String videoId;
    private String musicArtist;
    private String releaseDate;
    private String musicTitle;
    private String albumName;
    private String albumSrc;
    @NotBlank(message = "Tag name is required")
    private String tagName;

    public Feed toEntity() {

        Feed feed = new Feed();
        feed.setContent(content);
        feed.setMusicArtist(musicArtist);
        feed.setReleaseDate(releaseDate);
        feed.setMusicTitle(musicTitle);
        feed.setAlbumName(albumName);
        feed.setVideoId(videoId);
        // tagName에 해당하는 HashTag 엔티티 생성
        HashTag hashTag = HashTag.builder().tagName(tagName).build();

        // Feed에 HashTag 추가
        feed.getHashTags().add(hashTag);

        return feed;
    }

//    가수, 노래제목, 발매연도, 앨범이름, 사진, 글, 해시태그, 작성자,
}

