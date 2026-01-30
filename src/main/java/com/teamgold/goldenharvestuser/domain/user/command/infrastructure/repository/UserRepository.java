package com.teamgold.goldenharvestuser.domain.user.command.infrastructure.repository;

import com.teamgold.goldenharvestuser.domain.user.command.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);    //  중복체크
    Optional<User> findByEmail(String email);

    String email(String email);
}
