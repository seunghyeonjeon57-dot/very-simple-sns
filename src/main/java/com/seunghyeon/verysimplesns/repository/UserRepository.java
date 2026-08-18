package com.seunghyeon.verysimplesns.repository;

import com.seunghyeon.verysimplesns.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
     Optional<User> findByEmail(String email);
     Optional<User> findByUsername(String userName);
     Optional<User> findByNickname(String nickName);
     boolean existsByEmail(String email);
     boolean existsByUsername(String userName);
     boolean existsByNickname(String nickName);


}
