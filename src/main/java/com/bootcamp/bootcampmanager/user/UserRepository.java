package com.bootcamp.bootcampmanager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String userName);

    Boolean existsByEmail(String email);

    User getByEmail(String email);

}
