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

    @ElementCollection
    private List<String> videoIds;

}


