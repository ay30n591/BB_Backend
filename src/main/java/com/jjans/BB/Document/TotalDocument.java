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

    private String article_type;

    private String img_src;

    private String user_id;

    private String tag_name;

    private String P_album_name;
    private String f_album_name;

    private String p_album_url;
    private String f_album_url;

    private String p_music_artist;
    private String f_music_artist;

    private String p_music_title;
    private String f_music_title;

    private String user_img_src;

    private String nick_name;
}