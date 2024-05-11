package com.ftn.sbnz.repository;

import com.ftn.sbnz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
