package com.bootcamp.bootcampmanager.password;

import com.bootcamp.bootcampmanager.user.User;
import org.springframework.stereotype.Service;

@Service
public class PasswordGeneratorServiceImpl implements PasswordGeneratorService {

    public String generateRandomPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        return passwordGenerator.generate(8);
    }
    public String generateRandomPassword(String email) {
        return email.split("@")[0];
    }
}
