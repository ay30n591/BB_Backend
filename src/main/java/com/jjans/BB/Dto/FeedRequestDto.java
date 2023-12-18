package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.HashTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    private List<String> hashTags;

    public Feed toEntity() {

        Feed feed = new Feed();
        feed.setContent(content);
        feed.setMusicArtist(musicArtist);
        feed.setReleaseDate(releaseDate);
        feed.setMusicTitle(musicTitle);
        feed.setAlbumName(albumName);
        feed.setVideoId(videoId);
        feed.setAlbumUrl(albumSrc);
        Set<HashTag> hashTagSet = hashTags.stream()
                .map(tagName -> {
                    HashTag hashTag = new HashTag();
                    hashTag.setTagName(tagName);
                    return hashTag;
                })
                .collect(Collectors.toSet());
        feed.setHashTags(hashTagSet);


        return feed;
    }
//    가수, 노래제목, 발매연도, 앨범이름, 사진, 글, 해시태그, 작성자,
}