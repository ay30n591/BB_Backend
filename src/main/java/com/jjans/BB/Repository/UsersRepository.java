package com.jjans.BB.Repository;

import com.jjans.BB.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    // 중복id 체크
    Optional<Users> findByEmail(String email);
    Optional<Users> findByNickName(String nickname);

    boolean existsByEmail(String email);
    List<Users> findAll();


}
