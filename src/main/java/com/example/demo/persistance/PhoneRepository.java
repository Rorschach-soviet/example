package com.example.demo.persistance;

import com.example.demo.persistance.entity.Phones;
import com.example.demo.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phones,Long> {

    public List<Phones> findByUserId(Integer userId);
}
