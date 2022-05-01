package com.bootcamp.bootcampmanager.studentTaskStatus;

import java.util.List;

public interface StatusService {

    void saveStudTaskStatus(StudentTaskStatus sTS);

    StudentTaskStatus getStudTaskStatusByID(long id);

    List<StudentTaskStatus> getAllStudTaskStats();

    void deleteStudTaskStatusByID(long id);

    void changeStatusByID(long id);
}
