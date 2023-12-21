package com.jjans.BB.Entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity

@Table(name = "chat_rooms")
public class ChatRoom extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "user_chat_rooms",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Users> participants = new HashSet<>();

    public void addParticipant(Users user) {
        this.participants.add(user);
    }

    public void removeParticipant(Users user) {
        this.participants.remove(user);
    }
}
