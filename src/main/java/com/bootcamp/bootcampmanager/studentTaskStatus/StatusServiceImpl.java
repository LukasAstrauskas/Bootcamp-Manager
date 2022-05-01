package com.bootcamp.bootcampmanager.studentTaskStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }


    @Override
    public void saveStudTaskStatus(StudentTaskStatus studTaskStat) {
        statusRepository.save(studTaskStat);
    }

    @Override
    public StudentTaskStatus getStudTaskStatusByID(long id) {
        return statusRepository.getById(id);
    }

    @Override
    public List<StudentTaskStatus> getAllStudTaskStats() {
        return statusRepository.findAll();
    }

    @Override
    public void deleteStudTaskStatusByID(long id) {
        statusRepository.deleteById(id);
    }

    @Override
    public void changeStatusByID(long id) {
        StudentTaskStatus studentTaskStatus = statusRepository.getById(id);
        Boolean status = studentTaskStatus.getStatus();
        studentTaskStatus.setStatus(!status);
        statusRepository.save(studentTaskStatus);
    }
}
