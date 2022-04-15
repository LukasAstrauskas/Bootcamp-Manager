package com.bootcamp.bootcampmanager.student;

import com.bootcamp.bootcampmanager.task.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    void saveStudent(Student student);

    Student getStudentById(long id);

    void deleteStudentById(long id);

    List<Student> getStudentsWithNoBootcamp();

    Long unlinkStudent(long id);
}