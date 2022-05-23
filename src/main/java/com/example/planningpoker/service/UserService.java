package com.example.planningpoker.service;

import com.example.planningpoker.domain.Notification;
import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    Notification createUser(String name);

    Notification deleteUser(String name);

    User getUser(String name);

    boolean userExists(String name);
}
