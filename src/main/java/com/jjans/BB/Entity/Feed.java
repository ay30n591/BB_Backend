package com.jjans.BB.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    private int feedImage;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    public void update(String content, int feedImage) {

        this.content = content;
        this.feedImage = feedImage;

    }

}


