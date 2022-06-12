package com.example.planningpoker.service;

import com.example.planningpoker.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean registerUser(String name, String password);

    boolean deleteUser(String name);

    User getRegisteredUser(String name);

    boolean userExists(String name);

    String logIn(String userName, String password);


    boolean loggedUserExists(String userName);

    boolean logOut(String userName);

    boolean checkSessionId(String sessionId, String userName);
}
