package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Feed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


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

    public Feed toEntity() {

        Feed feed = new Feed();
        feed.setContent(content);
        feed.setMusicArtist(musicArtist);
        feed.setReleaseDate(releaseDate);
        feed.setMusicTitle(musicTitle);
        feed.setAlbumName(albumName);
        feed.setVideoId(videoId);

        return feed;
    }
//    가수, 노래제목, 발매연도, 앨범이름, 사진, 글, 해시태그, 작성자,
}