package com.jjans.BB.Enum;

import lombok.Getter;

import java.util.List;

@Getter
public enum Authority{
    ROLE_USER("사용자"),
    ROLE_ADMIN("관리자");

    private String description;
    Authority(String description) {
        this.description = description;
    }
}
