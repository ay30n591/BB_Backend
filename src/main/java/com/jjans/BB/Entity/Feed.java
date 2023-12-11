package com.jjans.BB.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Feed extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(length = 1000, nullable = false)
    private String content;

    private int feedLike;

    @Column(nullable = true)
    private String feedImageUrl;

    private String musicFileName;
    private String musicFileUrl;


    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "feed_hashtags",
            joinColumns = @JoinColumn(name = "feed_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    private Set<HashTag> hashTags = new HashSet<>();

}


