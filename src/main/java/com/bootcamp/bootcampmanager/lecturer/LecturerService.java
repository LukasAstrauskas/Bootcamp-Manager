package com.bootcamp.bootcampmanager.lecturer;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.student.Student;
import com.bootcamp.bootcampmanager.task.Task;

import java.util.List;

public interface LecturerService {

    List<Lecturer> getAllLecturers();

    void saveLecturer(Lecturer lecturer);

    Lecturer getLecturerById(long id);

    void deleteLecturerById(long id);

    List<Task> getLecturersTasks(long id);

    Lecturer getLecturerByEmail(String email);

    List<Bootcamp> getLecturersBootcampsByEmail(String name);

    List<Lecturer> getLecturersNotInCamp(Long campID);

    void unlinkLecFromCamp(long lecID, long campID);

    void linkLecturerToBootcamp(long campID, long lecID);

    List<Student> getAllLecturersStudents(Lecturer lec);
}
