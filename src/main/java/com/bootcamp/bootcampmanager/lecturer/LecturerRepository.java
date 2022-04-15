package com.bootcamp.bootcampmanager.lecturer;

import com.bootcamp.bootcampmanager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    

}
