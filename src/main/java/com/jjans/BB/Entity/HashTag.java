package com.jjans.BB.Entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tagName;
    // 기본 생성자
    public HashTag(String tagName) {
        this.tagName = tagName;
    }

}
