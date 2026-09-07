package com.mink.projecttrip.user.repository;

import com.mink.projecttrip.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByNickName(String nickName);
    public boolean existsByEmail(String email);
    public User findByEmailAndPassword(String email, String password);

    List<User> findByNickNameContaining(String nickName);

}
