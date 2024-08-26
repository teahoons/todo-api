package com.example.todo_api.repository;

import com.example.todo_api.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TbUser, Long> {
    TbUser findByUsername(String username);

}
