package com.jjans.BB.Document;


import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(indexName = "bb_users")
//        , useServerConfiguration = true, createIndex = false)
//@Mapping(mappingPath = "elastic/users-mapping.json") //타입 매핑
//@Setting(settingPath = "elastic/users-setting.json") //분석기 매핑
public class UsersDocument {

    @Id
    private String id;

    private String email;

    private  String user_name;

//    @Field(type = FieldType.Text, analyzer = "nori_analyzer")
    private  String nick_name;

    private String img_src;

}
