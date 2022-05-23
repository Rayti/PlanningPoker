package com.example.planningpoker.service;


import com.example.planningpoker.domain.Notification;
import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private List<User> userCache = new ArrayList<>();

    @Override
    public Notification createUser(String name) {
        return null;
    }

    @Override
    public Notification deleteUser(String name) {
        return null;
    }

    @Override
    public User getUser(String name) {
        return null;
    }

    @Override
    public boolean userExists(String name) {
        return false;
    }
}
