package com.bootcamp.bootcampmanager.studentTaskStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StudentTaskStatus, Long> {
}
