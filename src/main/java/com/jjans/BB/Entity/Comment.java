package com.jjans.BB.Entity;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user_id;

    @ManyToOne
    @JoinColumn(name = "feed_id") // 어떤 피드에 대한 댓글인지를 나타내는 필드
    private Feed feed_id; // 댓글이 속한 피드

}
