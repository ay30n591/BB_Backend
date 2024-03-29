package com.jjans.BB.Entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleLike> likes = new HashSet<>();

    // 사진
    @Column(nullable = true)
    private String imgSrc;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "articleHashtags",
            joinColumns = @JoinColumn(name = "articleId"),
            inverseJoinColumns = @JoinColumn(name = "hashtagId"))
    private Set<HashTag> hashTags = new HashSet<>();


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookMark> bookMarks = new ArrayList<>();

    public void addArticleLike(ArticleLike articleLike) {
        this.likes.add(articleLike);
    }

    public List<Users> getLikedUsers() {
        return likes.stream().map(ArticleLike::getUser).collect(Collectors.toList());
    }

    public void removeArticleLike(ArticleLike articleLike) {
        this.likes.remove(articleLike);
        articleLike.setArticle(null);
    }

    public void addBookmark(BookMark bookMark) {
        this.bookMarks.add(bookMark);
    }

    public void removeBookmark(BookMark bookMark) {
        this.bookMarks.remove(bookMark);
        bookMark.setArticle(null);
    }
}
