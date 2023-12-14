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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "ARTICLE_TYPE")

public class Article extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(length = 1000, nullable = false)
    private String content;

    private int feedLike;

    private String musicArtist;
    private String releaseDate;
    private String musicTitle;
    private String albumName;
    @ElementCollection
    private List<String> hashTagList;

    // 사진
    @Column(nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;
}
