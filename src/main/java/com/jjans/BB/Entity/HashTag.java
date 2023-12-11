package com.jjans.BB.Entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class HashTag {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String tagName;
        // 기본 생성자
        public HashTag() {
        }

        // 필요한 매개변수를 받는 생성자
        public HashTag(String tagName) {
                this.tagName = tagName;
        }
}
