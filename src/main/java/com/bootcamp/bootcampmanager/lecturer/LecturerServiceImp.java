package com.bootcamp.bootcampmanager.lecturer;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LecturerServiceImp implements LecturerService{

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    @Override
    public void saveLecturer(Lecturer lecturer) {
        this.lecturerRepository.save(lecturer);
    }

    @Override
    public Lecturer getLecturerById(long id) {
        Optional<Lecturer> optional = lecturerRepository.findById(id);
        Lecturer lecturer;
        if (optional.isPresent()) {
            lecturer = optional.get();
        } else {
            throw new RuntimeException("Not found lecturer: " + id);
        }
        return lecturer;
    }

    @Override
    public void deleteLecturerById(long id) {
        this.lecturerRepository.deleteById(id);
    }

    @Override
    public List<Task> getLecturersTasks(long id) {
        List<Task> tasksList = new ArrayList<>();
        for (Bootcamp bootcamp : getLecturerById(id).getJoinedBootcamp()) {
            tasksList.addAll(bootcamp.getTasks());
        }
        return tasksList;
    }
}
