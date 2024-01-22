package com.jjans.BB.Document;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "bb_plist")
public class PlaylistDocument {

    @Id
    private String id;

    private String tag_name;

    private String title;

    private String playlist_id;

    private String album_name;

    private String music_artist;

    private String music_title;

    private String release_date;

    private String video_id;

    private String article_type;

    private String content;

    private String user_id;

    private String email;

    private  String user_name;

    //    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private  String nick_name;

    private String img_src;

}