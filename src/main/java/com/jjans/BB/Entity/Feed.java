package com.jjans.BB.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Feed extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String content;

    private int feedLike;
    private int feedImage;

    @OneToMany(mappedBy = "feed_id", cascade = CascadeType.ALL) // 일대다 관계 설정 및 CascadeType.ALL로 댓글에 대한 변경을 피드에 전파
    private List<Comment> commentsList;
}


