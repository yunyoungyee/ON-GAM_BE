package com.example.spring_assignment1.repository;

import com.example.spring_assignment1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    //기능 추가 및 확장 시, UserCustomRepository(선언) -> UserCustomRepositoryImpl(정의) 생성.
    // interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository(인터페이스 다중 상속)으로 확장 가능
    Optional<User> findByEmail(String email);

    @Query("SELECT u.nickname FROM User u WHERE u.id = :id")
    Optional<String> findNicknameById(@Param("id") Long id);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
