package com.bootcamp.bootcampmanager.lecturer;

import com.bootcamp.bootcampmanager.task.Task;

import java.util.List;

public interface LecturerService {

    List<Lecturer> getAllLecturers();

    void saveLecturer(Lecturer lecturer);

    Lecturer getLecturerById(long id);

    void deleteLecturerById(long id);

    List<Task> getLecturersTasks(long id);
}
