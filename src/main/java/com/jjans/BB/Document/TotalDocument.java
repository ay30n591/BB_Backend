package com.jjans.BB.Document;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "bb_total")
public class TotalDocument {
    @Id
    private String id;

    private String feed_id;

    private String plist_id;

    private String title;

    private String tag_name;

    private String P_album_name;

    private String f_album_name;

    private String article_id;

    private String image_url;

    private String content;

    private String video_id;
    private String p_video_id;

    private String music_title;
    private String p_music_title;

    private String album_url;

    private String p_album_url;

    private String music_artist;
    private String p_music_artist;

    private String user_id;

    private  String nick_name;

    private String userimg;

    private String oauth2id;
}