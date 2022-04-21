package com.bootcamp.bootcampmanager.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StudentTaskStatus, Long> {
}
