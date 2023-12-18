package com.jjans.BB.Entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("FEED_TYPE")
public class Feed extends Article{

    private String musicArtist;
    private String releaseDate;
    private String musicTitle;
    private String albumName;

    private String videoId;

}


