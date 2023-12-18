package com.jjans.BB.Entity;


import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;


import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicInfo {

    private String musicArtist;
    private String releaseDate;
    private String musicTitle;
    private String albumName;
    private String videoId;
    private String albumUrl;
}
