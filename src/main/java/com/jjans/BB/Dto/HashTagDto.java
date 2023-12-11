package com.jjans.BB.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
@Getter
@Data
@Builder
public class HashTagDto {

    private Long id;

    @NotBlank(message = "Tag name is required")
    private String tagName;

    public HashTagDto(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "HashTagDto{" +
                "id=" + id +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
