package com.example.security1.repository;

import com.example.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author : cadqe13@gmail.com
 * date : 2022-04-12
 * description :
 */

public interface UserRepository extends JpaRepository<User, Long> {
}
