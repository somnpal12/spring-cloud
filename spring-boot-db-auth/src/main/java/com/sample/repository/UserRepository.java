package com.sample.repository;

import com.sample.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface UserRepository extends JpaRepository<User, Integer> {
}
