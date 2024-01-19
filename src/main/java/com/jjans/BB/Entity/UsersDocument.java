package com.jjans.BB.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jjans.BB.Dto.UserRequestDto;
import com.jjans.BB.Enum.AuthProvider;
import com.jjans.BB.Enum.Role;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
//        (access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "bb_users")
//        , useServerConfiguration = true, createIndex = false)
//@Mapping(mappingPath = "elastic/users-mapping.json") //타입 매핑
//@Setting(settingPath = "elastic/users-setting.json") //분석기 매핑
public class UsersDocument {

    @Id
    private long id;

    private String email;

    private String password = "";

    private String user_name;

    @Column(nullable = true)
//    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private String nick_name;

    private String img_src;

    private String gender;

    private String birth;

    private String oauth2Id;


}