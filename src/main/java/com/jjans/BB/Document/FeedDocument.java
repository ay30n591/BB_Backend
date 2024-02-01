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

    private String img_src;

    private String video_id;

    private String music_title;

    private String release_date;

    private String album_url;

    private String music_artist;

    private String user_id;

    private  String nick_name;

    private String user_img_src;
}
