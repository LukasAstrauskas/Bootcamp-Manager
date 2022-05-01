package com.bootcamp.bootcampmanager.bootcamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BootcampServiceImpl implements BootcampService {

    private final BootcampRepository bootcampRepository;

    @Autowired
    public BootcampServiceImpl(BootcampRepository bootcampRepository) {
        this.bootcampRepository = bootcampRepository;
    }


    @Override
    public List<Bootcamp> getAllBootcamps() {
        return bootcampRepository.findAll();
    }

    @Override
    public void saveBootcamp(Bootcamp bootcamp) {
         bootcampRepository.save(bootcamp);

    }

    @Override
    public Bootcamp getBootcampById(long id) {
        return bootcampRepository.getById(id);
    }

    @Override
    public void deleteBootcampById(long id) {
        bootcampRepository.deleteById(id);
    }
}
