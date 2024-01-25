package com.jjans.BB.Document;


import com.jjans.BB.Dto.UserRequestDto;
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
public class UsersDocument {

    @Id
    private String id;

    private  String nick_name;

    private String user_img_src;

}
