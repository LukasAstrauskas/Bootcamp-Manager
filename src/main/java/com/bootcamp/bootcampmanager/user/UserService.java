package com.bootcamp.bootcampmanager.user;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void saveUser(User user);

    User getUserById(long id);

    void deleteUserById(long id);

    void updateUserPassword(DataContainer dataContainer);

    void updateUser(User user);

    User getUserByEmail(String email);

    Boolean existsByEmail(String email);

    Long getUserIDByEmail(String email);
}