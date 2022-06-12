package com.example.planningpoker.service;

import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean registerUser(String name, String password);

    boolean deleteUser(String name);

    User getUser(String name);

    boolean userExists(String name);
}
