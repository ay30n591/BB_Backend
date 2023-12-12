package com.jjans.BB.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;


import javax.persistence.Id;

@Data
@Document(indexName = "music")
@NoArgsConstructor
public class MusicInfo{

    @Id
    private String id;
    private String artist;
    private String musicFileName;
    private String musicFileUrl;


}
