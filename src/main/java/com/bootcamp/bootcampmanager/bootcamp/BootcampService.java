package com.bootcamp.bootcampmanager.bootcamp;

import java.util.List;


public interface BootcampService {

    List<Bootcamp> getAllBootcamps();

    void saveBootcamp(Bootcamp bootcamp);

    Bootcamp getBootcampById(long id);

    void deleteBootcampById(long id);

}
