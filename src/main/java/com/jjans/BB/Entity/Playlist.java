package com.jjans.BB.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("PLAYLIST_TYPE")

public class Playlist extends Article {

    //title
    private String title;

    @ElementCollection
    @CollectionTable(name = "playlist_music_info", joinColumns = @JoinColumn(name = "playlist_id"))
    private List<MusicInfo> musicInfoList;
}


