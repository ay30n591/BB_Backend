package com.jjans.BB.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nickName")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "article")
    private Article article;

    @ManyToMany(mappedBy = "bookmarkedPosts", cascade = CascadeType.ALL)
    private List<Article> feeds = new ArrayList<>();
}
