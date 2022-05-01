package com.bootcamp.bootcampmanager.password;

import com.bootcamp.bootcampmanager.user.User;

public interface PasswordGeneratorService {
    String generateRandomPassword();

    String generateRandomPassword(String string);

}
