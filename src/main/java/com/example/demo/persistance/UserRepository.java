package com.example.demo.persistance;

import com.example.demo.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByToken(String token);

    public Boolean existsUserByEmail(String email);
}
