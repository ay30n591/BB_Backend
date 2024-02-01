package com.jjans.BB.Dto;

import com.jjans.BB.Entity.Feed;
import com.jjans.BB.Entity.HashTag;
import com.jjans.BB.Entity.MusicInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedRequestDto {

    private String content;
    private String videoId;
    private MusicInfo musicInfo; // Change to MusicInfo
    private String albumSrc;
    private List<HashTag> hashTags; // Change to List<HashTag>

    public Feed toEntity() {
        Feed feed = new Feed();
        feed.setContent(content);
        feed.setMusicInfo(musicInfo);
        Set<HashTag> hashTagSet = hashTags.stream()
                .map(tag -> {
                    HashTag hashTag = new HashTag();
                    hashTag.setTagName(tag.getTagName());
                    return hashTag;
                })
                .collect(Collectors.toSet());
        feed.setHashTags(hashTagSet);

        return feed;
    }
//    가수, 노래제목, 발매연도, 앨범이름, 사진, 글, 해시태그, 작성자,
}