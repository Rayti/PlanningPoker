package com.example.planningpoker.service;


import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultUserService implements UserService {

    private List<User> userCache = new ArrayList<>();

    @Override
    public boolean registerUser(String name, String password) {
        if (userExists(name)) {
            return false;
        }

        //MD5Encoder.encode(password.getBytes(StandardCharsets.UTF_8))
        User user = new User(name, password);
        userCache.add(user);
        return true;
    }

    @Override
    public boolean deleteUser(String name) {
        if(!userExists(name)){
            return false;
        }
        userCache = userCache.stream().filter(user -> !user.getName().equals(name)).collect(Collectors.toList());
        return true;
    }

    @Override
    public User getUser(String name) {
        return userCache.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    public boolean userExists(String name) {
        return userCache.stream().anyMatch(user -> user.getName().equals(name));
    }
}
