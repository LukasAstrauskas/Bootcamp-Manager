package com.bootcamp.bootcampmanager.lecturer;

import com.bootcamp.bootcampmanager.bootcamp.Bootcamp;
import com.bootcamp.bootcampmanager.bootcamp.BootcampService;
import com.bootcamp.bootcampmanager.student.Student;
import com.bootcamp.bootcampmanager.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LecturerServiceImp implements LecturerService {

    private final LecturerRepository lecturerRepository;

    private final BootcampService bootcampService;

    @Autowired
    public LecturerServiceImp(LecturerRepository lecturerRepository,
                              BootcampService bootcampService) {
        this.lecturerRepository = lecturerRepository;
        this.bootcampService = bootcampService;
    }

    @Override
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    @Override
    public void saveLecturer(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
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

    @Override
    public Lecturer getLecturerByEmail(String email) {
        return lecturerRepository.getByEmail(email);
    }

    @Override
    public List<Bootcamp> getLecturersBootcampsByEmail(String email) {
        Lecturer lecturer = getLecturerByEmail(email);
        return lecturer.getJoinedBootcamp();
    }

    public List<Lecturer> getLecturersNotInCamp(Long campID) {
        List<Lecturer> campLecturers = bootcampService.getBootcampById(campID).getCampLecturers();
        List<Lecturer> lecturers = lecturerRepository.findAll();
        lecturers.removeAll(campLecturers);
        return lecturers;
    }

    @Override
    public void unlinkLecFromCamp(long lecID, long campID) {
        Lecturer lec = lecturerRepository.getById(lecID);
        Bootcamp camp = bootcampService.getBootcampById(campID);
        List<Bootcamp> camps = lec.getJoinedBootcamp();
        camps.remove(camp);
        lec.setJoinedBootcamp(camps);
        lecturerRepository.save(lec);
    }

    @Override
    public void linkLecturerToBootcamp(long campID, long lecID) {
        Lecturer lec = lecturerRepository.getById(lecID);
        List<Bootcamp> bootcamps = lec.getJoinedBootcamp();
        bootcamps.add(bootcampService.getBootcampById(campID));
        lec.setJoinedBootcamp(bootcamps);
        lecturerRepository.save(lec);
    }

    @Override
    public List<Student> getAllLecturersStudents(Lecturer lec) {
        List<Student> studentsList = new ArrayList<>();
        lec.getJoinedBootcamp().forEach(bootcamp ->
                studentsList.addAll(bootcamp.getStudents())
        );
        return studentsList;
    }
}
