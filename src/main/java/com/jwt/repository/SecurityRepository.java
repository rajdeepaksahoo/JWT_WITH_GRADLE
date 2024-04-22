package com.jwt.repository;

import com.jwt.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<UserModel,Long> {
    @Query("SELECT u FROM UserModel u WHERE u.userName = :userName")
    public Optional<UserModel> findByUserName(String userName);
}
