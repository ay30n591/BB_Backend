package com.jjans.BB.Document;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "bb_feed")
public class FeedDocument {
    @Id
    private String id;

    private String tag_name;

    private String album_name;

    private String feed_id;

    private String article_id;

    private String image_url;

    private String content;

    private String video_id;

    private String music_title;

    private String album_url;

    private String music_artist;

    private String user_id;

    private String email;

    private  String user_name;

    //    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private  String nick_name;

    private String img_src;
}


// 깃 메인